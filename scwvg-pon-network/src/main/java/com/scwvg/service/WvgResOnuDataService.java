package com.scwvg.service;

import java.util.Map;

import com.github.pagehelper.Page;


/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 
**/
public interface WvgResOnuDataService {
	
	public Page<Map<String, Object>> queryAll(Map<String, Object> params, Page<Map<String, Object>> page);
	
	public Map<String, Object> get(String loid);

}
