package com.scwvg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.vo.ResVo;
import com.scwvg.service.WvgResDataService;
import com.scwvg.utils.PageInfo;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:40
 * @desc: 
**/
@RestController
@RequestMapping("/res")
public class WvgResDataController {
	
	@Autowired
	private WvgResDataService wvgResDataService;
	
	@GetMapping("/getCity")
	public @ResponseBody List<Map<String, Object>> getCity() {
		return wvgResDataService.queryAllCity();
	}
	
	@Log("OLT资源数据查询")
	@GetMapping("/list")
	public @ResponseBody PageInfo<Map<String, Object>> queryAll(HttpServletRequest request, Page<Map<String, Object>> page) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("city_id", request.getParameter("city_id"));
		params.put("res_area", request.getParameter("res_area"));
		params.put("res_type_id", request.getParameter("res_type_id"));
		params.put("res_vendor_id", request.getParameter("res_vendor_id"));
		params.put("isno_battery", request.getParameter("isno_battery"));
		params.put("isno_dynamicring", request.getParameter("isno_dynamicring"));
		params.put("res_name", request.getParameter("res_name"));
		params.put("res_ip", request.getParameter("res_ip"));
		params.put("snmp_state", request.getParameter("snmp_state"));
		params.put("telnet_state", request.getParameter("telnet_state"));
		
		Page<Map<String, Object>> queryAll = this.wvgResDataService.queryAll(params, page);
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(queryAll.getPageNum(), queryAll.getPageSize(), queryAll.getTotal());
		pageInfo.setTotalPage(queryAll.getPages());
		pageInfo.setItems(queryAll.getResult());
		
		return pageInfo;
	}
	
	@Log("OLT资源数据明细")
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Object> get(@PathVariable("id") String res_id) {
		return wvgResDataService.get(res_id);
	}
	
	@Log("新增OLT资源")
	@PostMapping("/add")
	public @ResponseBody JSONObject add(ResVo params) {
		wvgResDataService.add(params);
		JSONObject result = new JSONObject();
		result.put("code", "200");
		result.put("msg", "保存成功!");
		return result;
	}
	
	@Log("更新OLT资源")
	@PostMapping("/update")
	public @ResponseBody JSONObject update(ResVo params) {
		wvgResDataService.update(params);
		JSONObject result = new JSONObject();
		result.put("code", "200");
		result.put("msg", "更新成功!");
		return result;
	}
	
	@Log("删除OLT资源")
	@PostMapping("/del/{id}")
	public @ResponseBody JSONObject delete(@PathVariable("id") Long res_id) {
		wvgResDataService.delete(res_id);
		JSONObject result = new JSONObject();
		result.put("code", "200");
		result.put("msg", "删除成功!");
		return result;
	}

}
