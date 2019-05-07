package com.scwvg.configuration;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scwvg.service.WvgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.Token;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.filter.TokenFilter;
import com.scwvg.service.LoginLogService;
import com.scwvg.service.WvgTokenService;
import com.scwvg.utils.ResponseUtil;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：登录类处理器
 **/
@Configuration
public class SecurityHandlerConfig {
    @Autowired
    private WvgTokenService wvgTokenService;
    
    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private WvgUserService  userService;
    /**
     * 登陆成功，返回Token
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                WvgLoginUser loginUser = (WvgLoginUser) authentication.getPrincipal();

                Token token = wvgTokenService.saveToken(loginUser);
                userService.upDateUsLoginTimeAndIp(loginUser);
                
                // 保存登录成功日志
                WvgLoginLog loginLog = new WvgLoginLog();
        		loginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));
        		loginLog.setStatus(0L);
                loginLogService.save(loginLog);
                
                /*查询token返回前端*/
                ResponseUtil.responseJson(response, HttpStatus.OK.value(), token);
            }
        };
    }
    /**
     * 登陆失败
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                String msg = null;
                if (exception instanceof BadCredentialsException) {
                    msg = "密码错误!";
                } else {
                    msg = exception.getMessage();
                }
                
                // 保存登录失败日志
                WvgLoginLog loginLog = new WvgLoginLog();
        		loginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));
        		loginLog.setStatus(1L);
        		loginLog.setDesc(msg);
        		loginLogService.save(loginLog);
                
                Msg msgMassge = new Msg(HttpStatus.UNAUTHORIZED.value() + "", msg);
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), msgMassge);
            }
        };

    }
    /**
     * 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                authException.printStackTrace();
                Msg msg = new Msg(HttpStatus.UNAUTHORIZED.value() + "", "请先登录！");
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), msg);
            }

        };
    }

    /**
     * 退出处理
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSussHandler() {
        return new LogoutSuccessHandler() {

            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                Msg msg = new Msg(HttpStatus.OK.value() + "", "退出成功！");

                String token = TokenFilter.getToken(request);
                wvgTokenService.deleteToken(token);

                ResponseUtil.responseJson(response, HttpStatus.OK.value(), msg);
            }
        };
    }
}
