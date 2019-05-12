package com.scwvg.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scwvg.elastic.sql.ESql;
import com.scwvg.util.PageInfo;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/12 16:51
 * @desc: 
**/
@RestController
@RequestMapping("/sql")
public class ElasticController {
	
	@Autowired
	private ESql eSql;
	
	@GetMapping("/queryForList")
	public @ResponseBody PageInfo<Map<String, Object>> queryForList(HttpServletRequest request) {
		try {
			String pageSize = request.getParameter("pageSize");
			String pageNum = request.getParameter("pageNum");
			String sql = request.getParameter("sql");
			
			List<Map<String,Object>> dataList = null;
			PageInfo<Map<String, Object>> pageInfo = null;
			if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
				Integer startIndex = (Integer.parseInt(pageNum) - 1) * Integer.parseInt(pageSize);
				
				long totalNum = this.eSql.queryForCount(sql);
				dataList = this.eSql.queryForList(sql, startIndex, Integer.parseInt(pageSize));
				
				pageInfo = new PageInfo<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize), totalNum);
			} else {
				dataList = this.eSql.queryForList(sql);
				pageInfo = new PageInfo<>();
			}
			
			pageInfo.setItems(dataList);
			
			return pageInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/queryForGroup")
	public @ResponseBody PageInfo<Map<String, Object>> queryForGroup(HttpServletRequest request) {
		try {
			String pageSize = request.getParameter("pageSize");
			String sql = request.getParameter("sql");
			
			List<Map<String,Object>> dataList = null;
			PageInfo<Map<String, Object>> pageInfo = null;
			if(StringUtils.isNotBlank(pageSize)) {
				dataList = this.eSql.queryForListGroup(sql, Integer.parseInt(pageSize));
				Long totalNum = (long) dataList.size();
				pageInfo = new PageInfo<>(1, Integer.parseInt(pageSize), totalNum);
			} else {
				dataList = this.eSql.queryForListGroup(sql);
				pageInfo = new PageInfo<>();
			}
			
			pageInfo.setItems(dataList);
			
			return pageInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
