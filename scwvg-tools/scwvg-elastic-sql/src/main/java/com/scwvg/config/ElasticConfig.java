package com.scwvg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scwvg.elastic.sql.ESql;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/12 16:54
 * @desc: 
**/
@Configuration
@ConfigurationProperties(prefix = "esql")
public class ElasticConfig {
	
	private String clusterName;
	private String host;
	private boolean sniff;
	
	@Bean(initMethod = "init")
	public ESql eSql() {
		ESql eSql = new ESql(clusterName, host, sniff);
		return eSql;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isSniff() {
		return sniff;
	}

	public void setSniff(boolean sniff) {
		this.sniff = sniff;
	}
	

}
