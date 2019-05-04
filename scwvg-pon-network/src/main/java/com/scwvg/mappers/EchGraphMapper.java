package com.scwvg.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 16:12
 * @desc: 首页地图
**/
@Mapper
public interface EchGraphMapper {
	
	public List<Map<String, Object>> queryAllOLTByCity(Map<String, Object> params);
	
	public List<Map<String, Object>> queryAllONUByCity(Map<String, Object> params);
	
	public List<Map<String, Object>> queryAllOLT();

}
