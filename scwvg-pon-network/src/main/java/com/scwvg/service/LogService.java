package com.scwvg.service;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgOperationLog;


/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 
**/
public interface LogService {
	
	public void save(ProceedingJoinPoint joinPoint, WvgOperationLog log);
	
	public Page<WvgOperationLog> queryAll(Map<String, Object> params, Page<WvgOperationLog> page);
	
	public WvgOperationLog get(Long id);

}
