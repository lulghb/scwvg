package com.scwvg.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月18日16点56分
 * @Description: TODO 系统基础层
 * version 0.1
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.scwvg.system","com.scwvg.system.configuration","com.scwvg.system.controller"})
public class ScwvgBaseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgBaseSystemApplication.class, args);
    }
}
