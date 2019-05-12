package com.scwvg.sys.content.configuration;

import com.scwvg.sys.content.thymeleaf.ScwvgDialect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月24日12点21分
 * @Description: TODO commons 配置文件
 * version 0.1
 */
@Configuration
@MapperScan({"com.scwvg.sys.content.mapper"})
public class ContentConfig {

    /**
     * 完成 thymeleaf 自定义标签属性方言注入
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "scwvgDialect")
    ScwvgDialect scwvgDialect(){
        return new ScwvgDialect();
    }
}
