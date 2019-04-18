package com.scwvg.handler;

import com.scwvg.entitys.WvgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月17日22点53分
 * @Description: TODO 处理是转发或重定向到登录页面
 * version 0.1
 */
public class ScwvgAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    private static Logger logger = LoggerFactory.getLogger(ScwvgAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //3. 判断用户是否登陆
        boolean sign=true;
        if(SecurityContextHolder.getContext().getAuthentication() !=null //3.1 判断是否存在身份证明
                //3.2 判断身份证明是真实可靠，有效
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                //3.3 判断当事人是否是未登录匿名用户
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            //3.4 当事人通过证实，获取当事人信息
            WvgUser principal = (WvgUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal !=null)
                sign=true;
            else
                sign=false;
        }else{
            sign=false;
        }
        // 用户未登录
        if(!sign){
            //request.getRequestDispatcher("/login").forward(request,response);
            response.sendRedirect( "/login");
        }
    }
}
