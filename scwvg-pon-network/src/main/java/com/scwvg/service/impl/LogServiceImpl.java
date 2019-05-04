package com.scwvg.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.scwvgponnetwork.WvgOperationLog;
import com.scwvg.mappers.WvgOperationLogMapper;
import com.scwvg.service.LogService;
import com.scwvg.utils.RequestHolder;
import com.scwvg.utils.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:16
 * @desc: 
**/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class LogServiceImpl implements LogService {
	
	@Autowired
    private WvgOperationLogMapper wvgOperationLogMapper;

	@Override
	public void save(ProceedingJoinPoint joinPoint, WvgOperationLog operationLog) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
		
		Object[] argValues = joinPoint.getArgs();
		String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		String params = null;
		// 用户名
        String username = "";
        if(argValues != null){
        	params = "{";
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
            params += "}";
        }
        if(!"login".equals(signature.getName())){
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            if(userDetails != null) {
            	username = userDetails.getUsername();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(argValues[0]));
                username = jsonObject.get("user").toString();
            }catch (Exception e){
                e.printStackTrace();
                log.error("", e);
            }
        }
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        
        operationLog.setMethodName(methodName);
        operationLog.setUsername(username);
        operationLog.setRequestIp(getRequestIP(request));
        operationLog.setRequestUri(request.getRequestURI());
        operationLog.setRequestParams(params);
        
        wvgOperationLogMapper.save(operationLog);
        
	}
	
	@Override
	public Page<WvgOperationLog> queryAll(Map<String, Object> params, Page<WvgOperationLog> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		return wvgOperationLogMapper.queryByPage(params);
	}
	
	@Override
	public WvgOperationLog get(Long id) {
		return wvgOperationLogMapper.get(id);
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
