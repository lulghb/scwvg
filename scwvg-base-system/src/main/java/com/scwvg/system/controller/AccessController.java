package com.scwvg.system.controller;

import com.scwvg.system.entitys.SystemBraceEntity;
import com.scwvg.system.service.systemBrace.WvgSystemBraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/4/2：9:12
 **/
@Controller
public class AccessController {
   @Autowired
    private WvgSystemBraceService wvgSystemBraceService;
    private static String prefix = "scwvg-zuul.com/wvg";

    /**
     * @Description: 后台登陆首页
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @GetMapping("/login")
    public String accessMain() {
        return "/user/login";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    /**
     * TODO 注意：console 为关键字不能做 url地址结尾转发
     *
     * @Description:
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @GetMapping("/home/console")
    public String console(Model model) {
        List<SystemBraceEntity> braceList= wvgSystemBraceService.queryBraceAll();

        System.out.println(braceList);
        model.addAttribute("braceList",braceList);
        return "/home/wvg-console";
    }
}
