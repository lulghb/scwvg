package com.scwvg.zgy.conf;


import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/5/2 14:30
 * @Description: TODO 全局配置類
 * version 0.1
 */
@Configuration
@ComponentScan({
        "com.scwvg.zgy.system",
        "com.scwvg.zgy.commons"})
public class GlobalConfig {
    /**
     * @Description:
     * @Author: chen.baihoo
     * @Date: 2019年4月24日
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(
                new ClassPathResource("yml/application.yml"),
                new ClassPathResource("yml/application_spring.yml"),
                new ClassPathResource("yml/application_mybatis.yml")
        );
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
