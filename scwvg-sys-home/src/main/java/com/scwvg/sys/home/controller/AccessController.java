package com.scwvg.sys.home.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    public String login(){
        return "/admin/login";
    }

    /**
     * @Description:  用户退出登陆
     * @Author: chen.baihoo
     * @Date: 2019年4月20日
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response){
        // 1. SecurityContextHolder 取出认证用户权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 2. 判断 是否存在，可能 session 失效
        if(auth !=null){
            // 3. 存在退出移除认证用户
            new SecurityContextLogoutHandler().logout(request , response,auth);
        }
        return "redirect:/login?logout";
    }
    /** 
     * @Description: 系统首页
     * @Author: chen.baihoo
     * @Date: 2019/4/20 
     */ 
    @GetMapping("/index")
    public String index(Model model){
        return "/index";
    }

}
