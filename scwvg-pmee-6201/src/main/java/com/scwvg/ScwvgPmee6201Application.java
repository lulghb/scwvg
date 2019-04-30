package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScwvgPmee6201Application {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgPmee6201Application.class, args);
    }

}
