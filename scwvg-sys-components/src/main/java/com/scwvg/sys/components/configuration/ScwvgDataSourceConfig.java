package com.scwvg.sys.components.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.scwvg.sys.components.exception.ScwvgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年5月2日15点30分
 * @Description: TODO 事物管理器数据库资源配置类
 * version 0.1
 */
@Configuration
@Slf4j
public class ScwvgDataSourceConfig extends WebMvcAutoConfiguration {
	
    public ScwvgDataSourceConfig() {
	    super();
    }

	
    @Autowired
    private Environment env;
		
    @Value("${spring.datasource.type}")
    private String dbType;    
	
    @Value("${spring.datasource.url}")
    private String dbUrl;    
        
    @Value("${spring.datasource.username}")    
    private String username;    
        
    @Value("${spring.datasource.password}")    
    private String password;    
        
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;    
        
    @Value("${spring.datasource.initial-size}")
    private int initialSize;    
        
    @Value("${spring.datasource.min-idle}")
    private int minIdle;    
        
    @Value("${spring.datasource.max-active}")
    private int maxActive;    
        
    @Value("${spring.datasource.max-wait}")
    private int maxWait;    
        
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;    
        
    @Value("${spring.datasource.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;    
        
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;    
        
    @Value("${spring.datasource.test-while-idle}")
    private boolean testWhileIdle;    
        
    @Value("${spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;    
        
    @Value("${spring.datasource.test-on-return}")
    private boolean testOnReturn;    
        
    @Value("${spring.datasource.pool-prepared-statements}")
    private boolean poolPreparedStatements;    
        
    @Value("${spring.datasource.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;    
        
    @Value("${spring.datasource.filters}")
    private String filters;    
        
    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;  

    /** 
     * @Description:  创建数据源,并加载进 spring 容器,springboot 对druid许多配置不自动支持，需要配置自定义配置类
     * @Param:  
     * @return:  
     * @Author: chen.baihoo
     * @Date: 2019/5/2 
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean("dataSource")
    @Primary  // 当有多个实现时以此为优先为准
    public DataSource getDataSource() throws ScwvgException {
    	DruidDataSource datasource = new DruidDataSource();
    	
    	//datasource.setDbType(dbType);   //有些版本不支持该属性
    	datasource.setUrl(dbUrl);        //连接信息配置见application.properties
    	datasource.setDriverClassName(driverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);
        
        //configuration    
        datasource.setInitialSize(initialSize);    
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);    
        datasource.setMaxWait(maxWait);    
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);    
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);    
        datasource.setValidationQuery(validationQuery);    
        datasource.setTestWhileIdle(testWhileIdle);    
        datasource.setTestOnBorrow(testOnBorrow);    
        datasource.setTestOnReturn(testOnReturn);    
        datasource.setPoolPreparedStatements(poolPreparedStatements);    
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);    
        try {    
            datasource.setFilters(filters);    
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);    
    	return datasource;    	
    }
}