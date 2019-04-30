package com.scwvg.system.handler;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scwvg.system.log.domain.WvgLoginLog;
import com.scwvg.system.log.service.LoginLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/21 23:23
 * @desc: 
**/
@Component("authenticationSuccessHandler")
@Slf4j
public class WvgAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private LoginLogService loginLogService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		log.info("登录成功！");
		//TODO 记录登录成功日志
		WvgLoginLog loginLog = new WvgLoginLog();
		loginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));
		loginLog.setStatus(0L);
		loginLogService.save(loginLog);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
