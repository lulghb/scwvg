package com.scwvg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.utils.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/12 13:40
 * @desc: 告警查询
**/
@RestController
@RequestMapping("/am")
@Slf4j
public class AccAmController {
	
	private SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	@Value("${wvg.es.addr}")
    private String esAddr = "http://localhost:8087";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Log("告警查询")
	@GetMapping("/list")
	public @ResponseBody PageInfo<JSONObject> queryAll(HttpServletRequest request, Page<Map<String, Object>> page) {
		
		StringBuilder whereBuilder = new StringBuilder();
		
		String ip = request.getParameter("ip");// 告警名称
		if(StringUtils.isNotBlank(ip)) {
			whereBuilder.append(" AND IPADDRESS = '").append(ip).append("'");
		}
		
		String am_name = request.getParameter("am_name");// 告警名称
		if(StringUtils.isNotBlank(am_name)) {
			whereBuilder.append(" AND ALARMNAME LIKE '%").append(am_name).append("%'");
		}
		
		String am_text = request.getParameter("am_text");// 告警正文
		if(StringUtils.isNotBlank(am_text)) {
			whereBuilder.append(" AND ADDITIONALTEXT LIKE '%").append(am_text).append("%'");
		}
		
		String perceivedseverity = request.getParameter("perceivedseverity");// 告警级别
		if(StringUtils.isNotBlank(perceivedseverity)) {
			whereBuilder.append(" AND PERCEIVEDSEVERITY = ").append(perceivedseverity).append("");
		}
		
		String specialty = request.getParameter("specialty");// 专业
		if(StringUtils.isNotBlank(specialty)) {
			whereBuilder.append(" AND SPECIALTY = '").append(specialty).append("'");
		}
		
		String clearancereportflag = request.getParameter("clearancereportflag");// 清除标记
		if(StringUtils.isNotBlank(clearancereportflag)) {
			whereBuilder.append(" AND CLEARANCEREPORTFLAG = ").append(clearancereportflag).append("");
		}
		
		String region = request.getParameter("region");// 地市
		if(StringUtils.isNotBlank(region)) {
			whereBuilder.append(" AND REGION = '").append(region).append("'");
		}
		
		String locationinfo = request.getParameter("locationinfo");// 厂家
		if(StringUtils.isNotBlank(locationinfo)) {
			whereBuilder.append(" AND LOCATIONINFO = '").append(locationinfo).append("'");
		}
		
		String dt = request.getParameter("dt");// 日期
		String time = request.getParameter("time_range"); //时间范围
		if(StringUtils.isNotBlank(dt) && StringUtils.isNotBlank(time)) {
			String[] split = time.split("-");
			String begin_time = String.format("%s %s", dt, split[0].trim());
			String end_time = String.format("%s %s", dt, split[1].trim());
			whereBuilder.append(" AND EVENTTIME >= '").append(begin_time).append("'");
			whereBuilder.append(" AND EVENTTIME <= '").append(end_time).append("'");
		}
		
		if(whereBuilder.length() > 10) {
			whereBuilder = whereBuilder.delete(0, 4);
			whereBuilder = whereBuilder.insert(0, " WHERE ");
		}
		
		String sql = null;
		if(StringUtils.isNotBlank(dt)) {
			sql = String.format("SELECT * FROM acc_am_%s", dt.replace("-", ""));
		} else {
			sql = String.format("SELECT * FROM acc_am_%s", sdf_yyyyMMdd.format(new Date(System.currentTimeMillis() - 86400000)));
		}
		Map<String, String> params = new HashMap<>();
		params.put("pageSize", String.valueOf(page.getPageSize()));
		params.put("pageNum", String.valueOf(page.getPageNum()));
		params.put("sql", String.format("%s%s ORDER BY EVENTTIME DESC", sql, whereBuilder));
		
		String result = get(this.httpClient, String.format("%s/sql/queryForList", this.esAddr), params);
		
		PageInfo pageInfo = JSONObject.parseObject(result, PageInfo.class);
		
		return pageInfo;
	}
	
	private static String get(CloseableHttpClient httpClient, String url, Map<String, String> extraParams) {
		HttpGet get = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			
			if(extraParams != null) {
				for(Map.Entry<String, String> entry : extraParams.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			
			get = new HttpGet(uriBuilder.build());
			log.info(get.getURI().toString());
			CloseableHttpResponse response = httpClient.execute(get);
			
			return EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(get != null) {
				get.releaseConnection();
			}
		}
		return null;
	}

}
