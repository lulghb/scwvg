package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy     //zuul的启动
@EnableEurekaClient
public class ScwvgZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgZuulApplication.class, args);
    }

}
