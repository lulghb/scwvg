package com.scwvg.system.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scwvg.system.log.annotation.Log;
import com.scwvg.system.log.domain.WvgLoginLog;
import com.scwvg.system.log.service.LoginLogService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 22:24
 * @desc: 
**/
@Controller
@RequestMapping("/logon_log")
public class LoginLogController {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Log("登录日志查询")
	@GetMapping("/list")
	public @ResponseBody Page<WvgLoginLog> list(WvgLoginLog operationLog, Pageable pageable) {
		return loginLogService.queryAll(operationLog, pageable);
	}
	
	@Log("登录日志详细信息查询")
	@GetMapping("/{id}")
	public @ResponseBody WvgLoginLog get(@PathVariable("id") Long id) {
		return loginLogService.get(id);
	}
	
	@GetMapping
    public String index(){
        return "log/login/list";
    }
	
	@GetMapping("/detail")
    public String detail(){
        return "log/login/detail";
    }

}
