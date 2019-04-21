package com.scwvg.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月18日16点56分
 * @Description: TODO 系统基础层
 * version 0.1
 */
@SpringBootApplication
public class ScwvgBaseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgBaseSystemApplication.class, args);
    }
}
