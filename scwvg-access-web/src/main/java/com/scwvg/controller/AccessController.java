package com.scwvg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/4/2：9:12
 **/
@Controller
public class AccessController {

    private static String prefix="scwvg-zuul.com/wvg";
    
    /** 
     * @Description:  后台登陆首页
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @GetMapping("/login")
    public String accessMain(){
        return "/login";
    }

    @GetMapping("/index")
    public String index(){
        return "/index";
    }
    
    /**
     * TODO 注意：console 为关键字不能做 url地址结尾转发
     * @Description:
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @GetMapping("/home/console")
    public String console(){
        return "/home/wvg-console";
    }
}
