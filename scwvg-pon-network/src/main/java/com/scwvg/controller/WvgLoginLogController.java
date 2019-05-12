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
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.service.LoginLogService;
import com.scwvg.utils.PageInfo;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:40
 * @desc: 登录日志API
**/
@RestController
@RequestMapping("/lonlog")
public class WvgLoginLogController {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Log("登录日志查询")
	@GetMapping("/list")
	public @ResponseBody PageInfo<WvgLoginLog> queryAll(WvgLoginLog opLog, Page<WvgLoginLog> page) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("username", opLog.getUsername());
		params.put("beginTime", opLog.getQ_beginTime());
		params.put("endTime", opLog.getQ_endTime());
		Page<WvgLoginLog> queryAll = this.loginLogService.queryAll(params, page);
		
		PageInfo<WvgLoginLog> pageInfo = new PageInfo<>(queryAll.getPageNum(), queryAll.getPageSize(), queryAll.getTotal());
		pageInfo.setTotalPage(queryAll.getPages());
		pageInfo.setItems(queryAll.getResult());
		
		return pageInfo;
	}
	
	@Log("登录日志明细")
	@GetMapping("/{id}")
	public @ResponseBody WvgLoginLog get(@PathVariable("id") Long id) {
		return loginLogService.get(id);
	}

}
