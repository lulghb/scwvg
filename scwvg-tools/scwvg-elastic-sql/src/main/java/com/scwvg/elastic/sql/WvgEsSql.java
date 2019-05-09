package com.scwvg.elastic.sql;

import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/09 20:32
 * @desc: es-sql工具类
**/
public interface WvgEsSql {
	
	long queryForCount(String sql) throws Exception;
	
	long deleteBySql(String sql) throws Exception;
	
	Map<String, Object> queryForOne(String sql) throws Exception;
	
	List<Map<String, Object>> queryForList(String sql) throws Exception;
	
	List<Map<String, Object>> queryForListUnion(String sql) throws Exception;
	
	List<Map<String, Object>> queryForList(String sql, int s, int r) throws Exception;
	
	List<Map<String, Object>> queryForListGroup(String sql) throws Exception;
	
	List<Map<String, Object>> queryForListGroup(String sql,  int r) throws Exception;
	
	void setClient(Client client);

}
