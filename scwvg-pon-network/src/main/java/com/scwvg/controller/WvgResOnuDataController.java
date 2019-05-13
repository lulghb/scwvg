package com.scwvg.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.service.WvgResOnuDataService;
import com.scwvg.utils.PageInfo;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:40
 * @desc: 
**/
@RestController
@RequestMapping("/res_onu")
public class WvgResOnuDataController {
	
	@Autowired
	private WvgResOnuDataService wvgResOnuDataService;
	
	@Log("ONU资源数据查询")
	@GetMapping("/list")
	public @ResponseBody PageInfo<Map<String, Object>> queryAll(HttpServletRequest request, Page<Map<String, Object>> page) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("city_id", request.getParameter("city_id"));
		params.put("res_onu_online_state", request.getParameter("res_onu_online_state"));
		params.put("res_olt_ip", request.getParameter("res_olt_ip"));
		params.put("res_olt_name", request.getParameter("res_olt_name"));
		params.put("res_pon_state", request.getParameter("res_pon_state"));
		params.put("res_onu_ip", request.getParameter("res_onu_ip"));
		params.put("res_onu_loid", request.getParameter("res_onu_loid"));
		params.put("res_onu_state", request.getParameter("res_onu_state"));
		
		Page<Map<String, Object>> queryAll = this.wvgResOnuDataService.queryAll(params, page);
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(queryAll.getPageNum(), queryAll.getPageSize(), queryAll.getTotal());
		pageInfo.setTotalPage(queryAll.getPages());
		pageInfo.setItems(queryAll.getResult());
		
		return pageInfo;
	}
	
	@Log("ONU资源数据明细")
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Object> get(@PathVariable("id") String res_id) {
		return wvgResOnuDataService.get(res_id);
	}

}
