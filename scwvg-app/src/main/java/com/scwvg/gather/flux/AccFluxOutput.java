package com.scwvg.gather.flux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scwvg.gather.config.Global;
import com.scwvg.gather.util.BucketPath;
import com.scwvg.gather.util.Output;

public class AccFluxOutput extends Output {
    private static final Logger logger = LoggerFactory.getLogger(AccFluxOutput.class);

    private boolean isExit = false;
    private boolean bShould = false;


    @Override
    public void run() {

        BulkRequestBuilder bulkRequest = Global.esClientUtil.getBulkRequestBuilder();

        Map<String, Object> map = null;
        int num = 0;
        long time = System.currentTimeMillis();
        String index = Global.context.getString("es.index");

        Map<String, String> headers = new HashMap<>();
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() - Global.context.getLong("delay", 0L)));

        index = BucketPath.escapeString(index, headers);

        Integer batchSize = Global.context.getInteger("es.batch.size", 5000);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (!isExit || !Global.queue.isEmpty()) {
            String message = Global.queue.poll();
            if (message != null) {
                String[] split = message.split("\\$\\|\\|#", -1);
                if (split.length >= 12 && (Double.valueOf(split[6]) > 0 || Double.valueOf(split[7]) > 0 ||
                        Double.valueOf(split[8]) > 0 || Double.valueOf(split[9]) > 0)) {
                    map = new HashMap<>();
                    map.put("flux_date", sdf.format(new Date(Long.valueOf(split[0]) * 1000)));
                    map.put("dev_id", split[1]);
                    map.put("dev_ip", split[2]);

                    if ("OLT".equals(split[3])) {
                        map.put("dev_type_id", "1");
                    } else if ("ONU".equals(split[3])) {
                        map.put("dev_type_id", "2");
                    } else {
                        map.put("dev_type_id", "-1");
                    }

                    map.put("flux_port_name", split[4]);
                    map.put("flux_port", split[5]);
                    map.put("flux_inerrors", split[6]);
                    map.put("flux_outerrors", split[7]);
                    map.put("flux_inoctets", split[8]);
                    map.put("flux_outoctets", split[9]);
                    map.put("flux_width", split[10]);
                    map.put("flux_time", split[11]);

                    bulkRequest.add(Global.esClientUtil.getIndexRequestBuilder(index, index, map));
                    num++;
                }


            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (num > 0 && (bShould || num >= batchSize)) {
                BulkResponse response = bulkRequest.execute().actionGet();
                if (response.hasFailures()) {
                    // 处理错误
                    logger.info(response.buildFailureMessage());
                    logger.info("批量插入es错误");
                } else {
                    logger.info(String.format("入es:%s插入完成,耗时:%s毫秒", bulkRequest.numberOfActions(), System.currentTimeMillis() - time));
                }
                bulkRequest = Global.esClientUtil.getBulkRequestBuilder();
                time = System.currentTimeMillis();
                num = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (num > 0) {
            BulkResponse response = bulkRequest.execute().actionGet();
            if (response.hasFailures()) {
                // 处理错误
                logger.info(response.buildFailureMessage());
                logger.info("批量插入es错误");
            } else {
                logger.info(String.format("入es:%s插入完成,耗时:%s毫秒", bulkRequest.numberOfActions(), System.currentTimeMillis() - time));
            }
        }

        logger.info("slastic run completed!");
    }

    public void scheduer() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                bShould = true;
            }

        }, Global.context.getLong("es.interval", 10000L), Global.context.getLong("es.interval", 10000L));
    }

    /**
     * 退出线程
     */
    @Override
    public void exit() {
        this.isExit = true;
    }

    public static void main(String[] args) {
        String s = "开始2019-04-13 23:59:59[324192686-170.129.240.26/59092|136.192.50.33, 1.3.6.1.2.1.1.3.0=109 days, 5:39:15.26, 1.3.6.1.6.3.1.1.4.1.0=1.3.6.1.4.1.2011.2.115, 1.3.6.1.4.1.2011.2.115=0, 1.3.6.1.4.1.2011.2.115=3, 1.3.6.1.4.1.2011.2.115=2, 1.3.6.1.4.1.2011.2.115=170.129.240.26, 1.3.6.1.4.1.2011.2.115=772907019, 1.3.6.1.4.1.2011.2.115=877797, 1.3.6.1.4.1.2011.2.115=771751969, 1.3.6.1.4.1.2011.2.115=27, 1.3.6.1.4.1.2011.2.115=2019-04-13 16:00:28Z, 1.3.6.1.4.1.2011.2.115=0, 1.3.6.1.4.1.2011.2.115=12, 1.3.6.1.4.1.2011.2.115=4, 1.3.6.1.4.1.2011.2.115=37, 1.3.6.1.4.1.2011.2.115=ZXHN F650, 1.3.6.1.4.1.2011.2.115=ONT_NO_DESCRIPTION?alarm-policy_0, , moInfo=snmp_check=1|totalid=2086948|jf_name=南岗区移动网固网和兴商厦和兴商厦无线机房|zd_name=移动网固网和兴商厦|remark=1|city_name=哈尔滨|vendor_name=华为|device_name=哈尔滨_南岗_和兴大厦_OLT_1_华为_MA5680T|spec_name=ACC|devicetype_name=OLT|devicemodel_name=MA5680T|branch_name=|orgin_ip=170.129.240.26|oss_dev_name=哈尔滨_南岗_和兴大厦_OLT_1_华为_MA5680T|olt_adapter=|reverse_olt_adapter=|devcemodel_name=MA5680T|device_id=1701, oidInfo=state=0|row=5|event_name=ONU电源掉电|perceivedseverity=1|event_adapter=POSITION=RACK:1,SHELF:$GetByLine(10),SLOT:$GetByLine(11),PORT:$GetByLine(12),ONUNUM:$GetByLine(13);告警名称=GPON ONT掉电(DGi);硬件版本=$GetByLine(14);ONT描述信息(仅网管可见)=$Substr(\"15\",\"start\",\"?\"), MAC=$Substr(\"15\",\"?2\",\"?4\"), LOID=$Substr(\"15\",\"?4\",\"?end\"), ONUTYPE=$GetByLine(14), SERVICELEVEL=$Substr(\"15\",\"?\",\"?2\"), ONUIP=$Substr(\"15\",\"?1\",\"?2\"),ONUNAME=$OnuNameByIp(gatherID,\"15\",\"?1\",\"?2\"),|event_oid=772907019|recovery_oid=772972555]<AlarmStart>|^|BRANCH_NAME:|^|ORGIN_IP:170.129.240.26|^|CLEARANCETIMESTAMP:|^|OSS_DEV_NAME:哈尔滨_南岗_和兴大厦_OLT_1_华为_MA5680T|^|SEND_TIME:2019-04-13 23:59:59|^|ONLY_ID:324192686|^|ALARMTEXT:devcemodel_name#MA5680T|^|EQUIPMENTNAME:哈尔滨_南岗_和兴大厦_OLT_1_华为_MA5680T|^|ALARMORIGIN:136.192.50.33|^|ADDITIONALTEXT:POSITION=RACK:1,SHELF:0,SLOT:12,PORT:4,ONUNUM:37;告警名称=GPON ONT掉电(DGi);硬件版本=ZXHN F650;ONT描述信息(仅网管可见)=ONT_NO_DESCRIPTION, MAC=, LOID=, ONUTYPE=ZXHN F650, SERVICELEVEL=alarm-policy_0, ONUIP=,ONUNAME=,|^|PERCEIVEDSEVERITY:1|^|EVENTTIME:2019-04-13 23:59:59|^|SPECIALTY:ACC|^|NECLASS:OLT|^|CLEARANCEREPORTFLAG:0|^|ALARMNAME:ONU电源掉电|^|LOCATIONINFO:华为|^|TOTALID:2086948|^|JF_NAME:南岗区移动网固网和兴商厦和兴商厦无线机房|^|ZD_NAME:移动网固网和兴商厦|^|SERIAL:1701/772907019(772972555)/1555171199|^|REGION:哈尔滨|^|IPADDRESS:170.129.240.26|^|REPAIRACTIONADVICE:|^|EQUIPMENTNAME_ALIAS:哈尔滨_南岗_和兴大厦_OLT_1_华为_MA5680T|^|RENOVATE_FLAG:0|^|<AlarmEnd>结束";
        String[] split = s.substring(s.indexOf("<AlarmStart>") + 12, s.indexOf("<AlarmEnd>")).split("\\|\\^\\|");
        for (String string : split) {
            if (string.length() <= 0) {
                continue;
            }
            int indexOf = string.indexOf(":");
            String key = string.substring(0, indexOf);
            String value = string.substring(indexOf + 1);
            System.out.println(String.format("%s\t%s", key, value));
        }

    }

}
