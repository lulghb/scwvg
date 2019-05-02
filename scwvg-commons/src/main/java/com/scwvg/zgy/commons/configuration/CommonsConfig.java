package com.scwvg.zgy.commons.configuration;

import com.scwvg.zgy.commons.thymeleaf.ScwvgDialect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月24日12点21分
 * @Description: TODO commons 配置文件
 * version 0.1
 */
@Configuration
@MapperScan({"com.scwvg.zgy.commons.thymeleaf.dataset.mapper"}) // mybatis 映射目录
public class CommonsConfig {

    /**
     * 配置 redisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return  template;
    }

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
