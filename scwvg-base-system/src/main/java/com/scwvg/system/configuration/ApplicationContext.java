package com.scwvg.system.configuration;

import com.github.pagehelper.PageHelper;
import com.scwvg.system.convertor.ScwvgTrimString;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:06
 * @Description: TODO 配置类
 * version 0.1
 */
@Configuration
@MapperScan("com.scwvg.system.mapper") // mybatis 映射目录
public class ApplicationContext implements WebMvcConfigurer {

    /**
     * @Description:
     * @Author: chen.baihoo
     * @Date: 2019年4月18日
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application_system.yml"));
        //yaml.setResources(new FileSystemResource("application_system.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }


    // @param basename  国际化文件路径
    @Value("${spring.messages.basename}")
    public String basename;

    /**
     * @Description: 转换器服务类，添加工程定义的转化器
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Bean
    @Autowired
    public ConversionService conversionService() {
        // 创建转换器服务类
        DefaultConversionService service = new DefaultConversionService();
        //字符串去空转换器
        service.addConverter(new ScwvgTrimString());
        return service;
    }

    /** 
     * @Description: 用于解析消息得策略接口，支持这些小得参数和国际化
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Bean
    @Autowired
    public MessageSource messageSource() {
    	//1. 创建国际化消息资源类
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //2. 设置国际化文件路径
        messageSource.setBasename(basename);
        return messageSource;
    }
}
