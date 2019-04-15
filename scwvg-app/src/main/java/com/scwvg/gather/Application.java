package com.scwvg.gather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scwvg.gather.config.Context;
import com.scwvg.gather.config.Global;
import com.scwvg.gather.util.BucketPath;
import com.scwvg.gather.util.ESClientUtil;
import com.scwvg.gather.util.Output;

public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		Application application = new Application();
		
		if(application.init(args[0])) {
			Class<?> outputCls = null;
			try {
				outputCls = Class.forName(Global.context.getString("output.cls"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
			
			try {
				Output t = (Output) outputCls.newInstance();
				t.start();
				
				List<File> listFile = new ArrayList<>();
				
				String filePath = Global.context.getString("file.path");
				Map<String, String> headers = new HashMap<>();
				headers.put("timestamp", String.valueOf(System.currentTimeMillis() - Global.context.getLong("delay", 0L)));
				
				filePath = BucketPath.escapeString(filePath, headers);
				
				File f = new File(filePath);
				if(f.isDirectory()) {
					File[] files = f.listFiles();
					for (File file : files) {
						if(file.isFile()) {
							listFile.add(file);
						}
					}
				} else {
					listFile.add(f);
				}
				
				for (File file : listFile) {
					if(file.exists()) {
						FileReader reader = null;
						BufferedReader br = null;
						logger.info("开始读取: [{}]", file.getAbsolutePath());
						try {
							reader = new FileReader(file);
							br = new BufferedReader(reader);
							
							String line = br.readLine();
							while(line != null) {
								
								Global.queue.put(line);
								
								line = br.readLine();
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if(br != null) {
								try {
									br.close();
								} catch (IOException e) { e.printStackTrace(); }
							}
							if(reader != null) {
								try {
									reader.close();
								} catch (IOException e) { e.printStackTrace(); }
							}
						}
						logger.info("读取完成: [{}]", file.getAbsolutePath());
					}
				}
				
				t.exit();
				
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("init fail!");
		}
		
		logger.info("退出");
	}
	
	public boolean init(String confPath) {
		InputStream inStream = null;
		Properties properties = new Properties();
		try {
			inStream = new FileInputStream(confPath);
			properties.load(inStream);
			Global.context = new Context();
			//创建Context对象，存储配置信息
			for(Map.Entry<Object, Object> entry : properties.entrySet()) {
				Global.context.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			
			// 初始化esClient
			Global.esClientUtil = new ESClientUtil();
			Global.esClientUtil.setHosts(Global.context.getString("es.hosts"));
			Global.esClientUtil.setClusterName(Global.context.getString("es.clusterName"));
			Global.esClientUtil.setSniff(Global.context.getBoolean("es.sniff", false));
			Global.esClientUtil.init();
			
			
			//初始化队列
			Global.queue = new ArrayBlockingQueue<String>(Global.context.getInteger("queue.size", 5000));
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(inStream!=null) {
				try {
					inStream.close();
				} catch (Exception e) { }
			}
		}
		return true;
	}

}
