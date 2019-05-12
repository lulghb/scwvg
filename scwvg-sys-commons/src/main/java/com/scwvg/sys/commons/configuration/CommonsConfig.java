package com.scwvg.sys.commons.configuration;

import org.springframework.context.annotation.Bean;
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

}
