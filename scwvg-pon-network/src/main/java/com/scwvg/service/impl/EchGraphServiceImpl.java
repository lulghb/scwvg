package com.scwvg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scwvg.mappers.EchGraphMapper;
import com.scwvg.service.EchGraphService;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/02 21:47
 * @desc: 
**/
@Service
public class EchGraphServiceImpl implements EchGraphService {
	
	@Autowired
	private EchGraphMapper echGraphMapper;

	@Override
	public List<Map<String, Object>> findAllOLTByCity(String city_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("city_id", city_id);
		
		return this.echGraphMapper.queryAllOLTByCity(params);
	}
	
	@Override
	public List<Map<String, Object>> findAllONUByCity(String city_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("city_id", city_id);
		
		return this.echGraphMapper.queryAllONUByCity(params);
	}

	@Override
	public List<Map<String, Object>> findAllOLT() {
		return this.echGraphMapper.queryAllOLT();
	}
	
}
