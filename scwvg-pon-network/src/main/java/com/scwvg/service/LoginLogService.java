package com.scwvg.service;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;


/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 登录日志Service
**/
public interface LoginLogService {
	
	public void save(ProceedingJoinPoint joinPoint, WvgLoginLog log);
	
	public Page<WvgLoginLog> queryAll(Map<String, Object> params, Page<WvgLoginLog> page);
	
	public WvgLoginLog get(Long id);

}
