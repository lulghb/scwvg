package com.scwvg.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/04 16:36
 * @desc: 
**/
@Mapper
public interface WvgResDataMapper {
	
	public List<Map<String, Object>> queryAllCity();
	
	public Long getSnmpMaxId();
	
	public void insertSnmp(Map<String, Object> params);
	
	public void updateSnmp(Map<String, Object> params);
	
	public void deleteSnmp(long snmp_id);
	
	public Long getResMaxId();
	
	public void insertRes(Map<String, Object> params);
	
	public void updateRes(Map<String, Object> params);
	
	public void deleteRes(long res_id);
	
	public void insertResSubs(Map<String, Object> params);
	
	public void updateResSubs(Map<String, Object> params);
	
	public void deleteResSubs(long res_id);
	
	public Page<Map<String, Object>> queryByPage(Map<String, Object> params);
	
	public Map<String, Object> get(String res_id);

}
