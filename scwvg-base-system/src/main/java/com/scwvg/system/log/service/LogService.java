package com.scwvg.system.log.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scwvg.system.log.domain.WvgOperationLog;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 
**/
public interface LogService {
	
	public void save(ProceedingJoinPoint joinPoint, WvgOperationLog log);
	
	public Page<WvgOperationLog> queryAll(WvgOperationLog log, Pageable pageable);
	
	public WvgOperationLog get(Long id);

}
