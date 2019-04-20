package com.scwvg.web;

import com.scwvg.export.EnableScwvgSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月18日16点56分
 * @Description: TODO web层
 * version 0.1
 */
@SpringBootApplication
@EnableScwvgSystem
public class ScwvgAccessWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwvgAccessWebApplication.class, args);
    }

}
