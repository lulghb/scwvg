package com.scwvg.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgOperationLog;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/04 16:36
 * @desc: 
**/
@Mapper
public interface WvgOperationLogMapper {
	
	public void save(WvgOperationLog param);
	
	public Page<WvgOperationLog> queryByPage(Map<String, Object> params);
	
	public WvgOperationLog get(Long id);

}
