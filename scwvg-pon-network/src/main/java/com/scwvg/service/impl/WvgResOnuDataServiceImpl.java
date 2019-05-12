package com.scwvg.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.mappers.WvgResOnuDataMapper;
import com.scwvg.service.WvgResOnuDataService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/11 17:10
 * @desc: 
**/
@Service
public class WvgResOnuDataServiceImpl implements WvgResOnuDataService {
	
	@Autowired
	private WvgResOnuDataMapper wvgResOnuDataMapper;

	@Override
	public Page<Map<String, Object>> queryAll(Map<String, Object> params, Page<Map<String, Object>> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		return this.wvgResOnuDataMapper.queryByPage(params);
	}

	@Override
	public Map<String, Object> get(String res_id) {
		return this.wvgResOnuDataMapper.get(res_id);
	}

}
