package com.scwvg.gather.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.LinkedBlockingQueue;

import com.scwvg.gather.config.Global;

public class JdbcOutput extends Thread {

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);

    @Override
    public void run() {

        String sql = "INSERT INTO wvg_flux_data(flux_date, dev_id, dev_ip, dev_type_id, flux_port_name, flux_port, flux_inerrors, flux_outerrors, flux_inoctets, flux_outoctets, flux_width, flux_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://136.192.50.29:23306/scwvg?useUnicode=true&characterEncoding=UTF-8", "root", "lul@123456");
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int num = 0;

        while (!Global.isExit || !this.queue.isEmpty()) {
            String message = this.queue.poll();
            if (message != null) {
                String[] split = message.split("\\$\\|\\|#", -1);
                if (split.length >= 12) {
                    try {
                        pst.setTimestamp(1, new Timestamp(Long.valueOf(split[0]) * 1000));
                        pst.setString(2, split[1]);
                        pst.setString(3, split[2]);
                        if ("OLT".equals(split[3])) {
                            pst.setInt(4, 1);
                        } else if ("ONU".equals(split[3])) {
                            pst.setInt(4, 2);
                        } else {
                            pst.setInt(4, -1);
                        }
                        pst.setString(5, split[4]);
                        pst.setString(6, split[5]);
                        pst.setString(7, split[6]);
                        pst.setString(8, split[7]);
                        pst.setString(9, split[8]);
                        pst.setString(10, split[9]);
                        pst.setDouble(11, Double.valueOf(split[10]));
                        pst.setInt(12, Integer.valueOf(split[11]));

                        pst.addBatch();

                        num++;

                        if (num >= 500) {
                            pst.executeBatch();
                            System.out.println(num);
                            num = 0;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 关闭连接
        if (pst != null) {
            try {
                if (num >= 1) {
                    pst.executeBatch();
                    System.out.println(num);
                    num = 0;
                }
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.commit();
                System.out.println("commit");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void offer(String message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
