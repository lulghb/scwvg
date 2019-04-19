package com.scwvg.system.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 12:21
 * @Description: TODO 定义403无权限访问的处理，重定向到/403页面
 * version 0.1
 */
public class ScwvgAccessDeniedHandler implements AccessDeniedHandler {
	private static Logger logger = LoggerFactory.getLogger(ScwvgAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 无权访问拦截
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.info("用户： '" + auth.getName() + "'试图访问保护的 URL: "
					+ request.getRequestURI());
		}
		response.sendRedirect(request.getContextPath() + "/403");
	}
}
