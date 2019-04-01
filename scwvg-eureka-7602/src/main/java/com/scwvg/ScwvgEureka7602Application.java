package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/4/1：9:02
 **/
@SpringBootApplication
@EnableEurekaServer
public class ScwvgEureka7602Application {
    public static void main(String[] args) {
        SpringApplication.run(ScwvgEureka7602Application.class);
    }
}
