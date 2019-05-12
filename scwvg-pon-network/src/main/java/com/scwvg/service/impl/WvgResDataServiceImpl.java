package com.scwvg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.mappers.WvgResDataMapper;
import com.scwvg.service.WvgResDataService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/11 17:10
 * @desc: 
**/
@Service
public class WvgResDataServiceImpl implements WvgResDataService {
	
	@Autowired
	private WvgResDataMapper wvgResDataMapper;
	
	@Override
	public List<Map<String, Object>> queryAllCity() {
		return this.wvgResDataMapper.queryAllCity();
	}

	@Override
	public Page<Map<String, Object>> queryAll(Map<String, Object> params, Page<Map<String, Object>> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		return this.wvgResDataMapper.queryByPage(params);
	}

	@Override
	public Map<String, Object> get(String res_id) {
		return this.wvgResDataMapper.get(res_id);
	}

}
