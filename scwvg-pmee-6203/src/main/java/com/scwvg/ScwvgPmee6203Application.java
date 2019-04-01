package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScwvgPmee6203Application {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgPmee6203Application.class, args);
    }

}
