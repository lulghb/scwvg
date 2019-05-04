package com.scwvg.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/3
 * @Explain：登录设置
 **/
@Controller
public class LoginPageConfig {
    @RequestMapping("/login")
    public String loginPage(){
        return "user/login";

    }
    @RequestMapping("/index")
    public String index() {
        return "/index";
    }
}
