package com.scwvg.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.mappers.WvgLoginLogMapper;
import com.scwvg.service.LoginLogService;
import com.scwvg.utils.RequestHolder;
import com.scwvg.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:16
 * @desc: 登录日志Service
**/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class LoginLogServiceImpl implements LoginLogService {
	
	@Autowired
    private WvgLoginLogMapper wvgLoginLogMapper;

	@Override
	public void save(WvgLoginLog loginLog) {
		UserDetails userDetails = UserUtil.getLoginUser();
        if(userDetails != null) {
        	HttpServletRequest request = RequestHolder.getHttpServletRequest();
        	loginLog.setUsername(userDetails.getUsername());
            loginLog.setLoginIp(getRequestIP(request));
            loginLog.setLoginSession(request.getRequestedSessionId());
            
            wvgLoginLogMapper.save(loginLog);
        } else {
        	log.info("用户未登录");
        }
        
	}
	
	@Override
	public Page<WvgLoginLog> queryAll(Map<String, Object> params, Page<WvgLoginLog> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		return wvgLoginLogMapper.queryByPage(params);
	}
	
	@Override
	public WvgLoginLog get(Long id) {
		return wvgLoginLogMapper.get(id);
	}
	
	public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
