package com.scwvg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/4/2：9:12
 **/
@Controller
public class AccessController {
    private static String prefix="scwvg-zuul.com/wvg";
    @RequestMapping("/login")
    public String accessMain(){
        return "login";
    }


}
