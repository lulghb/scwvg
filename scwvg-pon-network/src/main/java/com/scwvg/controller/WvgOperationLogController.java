package com.scwvg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.scwvgponnetwork.WvgOperationLog;
import com.scwvg.service.LogService;
import com.scwvg.utils.PageInfo;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:40
 * @desc: 
**/
@RestController
@RequestMapping("/oplog")
public class WvgOperationLogController {
	
	@Autowired
	private LogService logService;
	
	@Log("操作日志查询")
	@GetMapping("/list")
	public @ResponseBody PageInfo<WvgOperationLog> queryAll(WvgOperationLog opLog, Page<WvgOperationLog> page) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("username", opLog.getUsername());
		params.put("beginTime", opLog.getQ_beginTime());
		params.put("endTime", opLog.getQ_endTime());
		Page<WvgOperationLog> queryAll = this.logService.queryAll(params, page);
		
		PageInfo<WvgOperationLog> pageInfo = new PageInfo<>(queryAll.getPageNum(), queryAll.getPageSize(), queryAll.getTotal());
		pageInfo.setTotalPage(queryAll.getPages());
		pageInfo.setItems(queryAll.getResult());
		
		return pageInfo;
	}
	
	@Log("操作日志明细")
	@GetMapping("/{id}")
	public @ResponseBody WvgOperationLog get(@PathVariable("id") Long id) {
		return logService.get(id);
	}

}
