package com.scwvg.service;

import java.util.List;
import java.util.Map;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/02 21:47
 * @desc: 
**/
public interface EchGraphService {
	
	List<Map<String, Object>> findAllOLTByCity(String city_id);
	
	List<Map<String, Object>> findAllONUByCity(String city_id);
	
	List<Map<String, Object>> findAllOLT();

}
