package com.scwvg.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/04 16:36
 * @desc: 登录日志Mapper
**/
@Mapper
public interface WvgLoginLogMapper {
	
	public void save(WvgLoginLog param);
	
	public Page<WvgLoginLog> queryByPage(Map<String, Object> params);
	
	public WvgLoginLog get(Long id);

}
