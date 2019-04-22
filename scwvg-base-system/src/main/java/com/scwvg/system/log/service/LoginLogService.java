package com.scwvg.system.log.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scwvg.system.log.domain.WvgLoginLog;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 
**/
public interface LoginLogService {
	
	public void save(WvgLoginLog loginLog);
	
	public Page<WvgLoginLog> queryAll(WvgLoginLog log, Pageable pageable);
	
	public WvgLoginLog get(Long id);
	

}
