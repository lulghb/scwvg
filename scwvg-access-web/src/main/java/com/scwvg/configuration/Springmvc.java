package com.scwvg.configuration;

import com.scwvg.convertor.ScwvgTrimString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:06
 * @Description: TODO 配置类
 * version 0.1
 */
@Configuration
public class Springmvc implements WebMvcConfigurer {
	
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
