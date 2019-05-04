package com.scwvg.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scwvg.annotation.Log;
import com.scwvg.entitys.scwvgponnetwork.WvgOperationLog;
import com.scwvg.service.LogService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/18 10:59
 * @desc: 操作日志切面
**/
@Aspect
@Slf4j
@Component
public class SysLogAspect {
	
	@Autowired
	private LogService logService;
	
	@Around("@annotation(sysLog)")
	@SneakyThrows
	public Object around(ProceedingJoinPoint joinPoint, Log sysLog) {

		WvgOperationLog operationLog = new WvgOperationLog();
		
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		
		Object obj = null;
		
		try {
			obj = joinPoint.proceed();
		} catch (Exception e) {
			operationLog.setExceptionInfo(getStackTrace(e));
			log.error("", e);
			throw e;
		}
		
		Long endTime = System.currentTimeMillis();
		
		operationLog.setBeginTime(new Timestamp(startTime));
		operationLog.setEndTime(new Timestamp(endTime));
		operationLog.setUseTime(endTime - startTime);
		operationLog.setUriDesc(sysLog.value());
		
		logService.save(joinPoint, operationLog);
        
		return obj;
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
	
	public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

}
