package com.scwvg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：
 **/
@Controller
public class WvgLoginController {
    @RequestMapping("/login")
    public String userLogin(){
        return "user/login";
    }
}
