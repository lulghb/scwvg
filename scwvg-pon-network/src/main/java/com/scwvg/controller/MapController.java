package com.scwvg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scwvg.annotation.Log;
import com.scwvg.service.EchGraphService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:40
 * @desc: 首页地图
**/
@RestController
@RequestMapping("/ech")
public class MapController {
	
	private static final Map<String, String> cityMapping = new HashMap<String, String>();
	static {
		cityMapping.put("齐齐哈尔市","452");
		cityMapping.put("牡丹江市","453");
		cityMapping.put("大兴安岭地区","457");
		cityMapping.put("鸡西市","467");
		cityMapping.put("佳木斯市","454");
		cityMapping.put("双鸭山市","469");
		cityMapping.put("大庆市","459");
		cityMapping.put("鹤岗市","468");
		cityMapping.put("黑河市","456");
		cityMapping.put("哈尔滨市","451");
		cityMapping.put("绥化市","455");
		cityMapping.put("七台河市","464");
		cityMapping.put("伊春市","458");
	}
	
	@Autowired
	private EchGraphService echGraphService;
	
	@Log("地图设备查询")
	@GetMapping("/queryAll")
	public @ResponseBody ResponseEntity<JSONObject> queryAll(String city_id) {
		
		JSONArray olt = new JSONArray();
		JSONArray olt_vendor = new JSONArray();
		JSONArray onu = new JSONArray();
		
		JSONObject returnJson = new JSONObject();
		returnJson.put("olt_vendor", olt_vendor);
		returnJson.put("olt", olt);
		returnJson.put("onu", onu);
		
		List<Map<String, Object>> findAllOlt = this.echGraphService.findAllOLTByCity(cityMapping.get(city_id));
		if(findAllOlt != null && !findAllOlt.isEmpty()) {
			JSONObject obj = null;
			for (Map<String, Object> map : findAllOlt) {
				obj = new JSONObject();
				obj.put("name", map.get("vendor_name"));
				obj.put("value", map.get("num"));
				olt.add(obj);
				
				if(!olt_vendor.contains(map.get("vendor_name"))) {
					olt_vendor.add(map.get("vendor_name"));
				}
			}
		}
		
		List<Map<String, Object>> findAllOnu = this.echGraphService.findAllONUByCity(cityMapping.get(city_id));
		if(findAllOnu != null && !findAllOnu.isEmpty()) {
			JSONObject obj = null;
			for (Map<String, Object> map : findAllOnu) {
				obj = new JSONObject();
				obj.put("name", map.get("status"));
				obj.put("value", map.get("num"));
				onu.add(obj);
			}
		}
		
		return new ResponseEntity<JSONObject>(returnJson, HttpStatus.OK);
	}
	
	@Log("地图查询所有OLT")
	@GetMapping("/queryAllOLT")
	public @ResponseBody ResponseEntity<JSONArray> queryAllOLT() {
		
		JSONArray olt = new JSONArray();
		
		List<Map<String, Object>> findAllOlt = this.echGraphService.findAllOLT();
		if(findAllOlt != null && !findAllOlt.isEmpty()) {
			JSONObject obj = null;
			for (Map<String, Object> map : findAllOlt) {
				obj = new JSONObject();
				if("大兴安岭市".equals(map.get("city_name"))) {
					obj.put("name", "大兴安岭地区");
				} else {
					obj.put("name", map.get("city_name"));
				}
				obj.put("value", map.get("num"));
				olt.add(obj);
			}
		}
		
		return new ResponseEntity<JSONArray>(olt, HttpStatus.OK);
	}

}
