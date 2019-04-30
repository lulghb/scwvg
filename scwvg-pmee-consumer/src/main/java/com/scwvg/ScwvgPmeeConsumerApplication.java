package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient       //eureka客户端
@EnableFeignClients(basePackages = "com.scwvg")
@EnableCircuitBreaker //服务熔断hystrix的配置*/
public class ScwvgPmeeConsumerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ScwvgPmeeConsumerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ScwvgPmeeConsumerApplication.class);
    }
}
