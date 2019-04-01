package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard  //服务监控
@EnableEurekaClient
public class ScwvgDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgDashboardApplication.class, args);
    }

}
