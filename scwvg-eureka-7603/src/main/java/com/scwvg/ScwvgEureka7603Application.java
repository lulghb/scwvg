package com.scwvg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ScwvgEureka7603Application {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgEureka7603Application.class, args);
    }

}
