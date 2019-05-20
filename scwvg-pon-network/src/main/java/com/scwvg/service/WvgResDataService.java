package com.scwvg.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.scwvg.vo.ResVo;


/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:09
 * @desc: 
**/
public interface WvgResDataService {
	
	public List<Map<String, Object>> queryAllCity();
	
	public Page<Map<String, Object>> queryAll(Map<String, Object> params, Page<Map<String, Object>> page);
	
	public Map<String, Object> get(String res_id);
	
	public void add(ResVo params);
	
	public void update(ResVo params);
	
	public void delete(Long res_id);

}
