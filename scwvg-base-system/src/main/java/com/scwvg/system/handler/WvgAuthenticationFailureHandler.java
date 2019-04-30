package com.scwvg.system.handler;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scwvg.system.log.domain.WvgLoginLog;
import com.scwvg.system.log.service.LoginLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/22 00:14
 * @desc: 
**/
@Component("authenticationFailureHandler")
@Slf4j
public class WvgAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败！");
		//TODO 记录登录成功日志
		WvgLoginLog loginLog = new WvgLoginLog();
		loginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));
		loginLog.setStatus(1L);
		loginLog.setDesc(exception.getMessage());
		loginLogService.save(loginLog);

        response.setContentType("text/html;charset=UTF-8");
        super.onAuthenticationFailure(request, response, exception);
	}

}
