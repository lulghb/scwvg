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
import com.scwvg.system.log.domain.WvgOperationLog;
import com.scwvg.system.log.service.LogService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 22:24
 * @desc: 
**/
@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@Log("操作日志查询")
	@GetMapping("/list")
	public @ResponseBody Page<WvgOperationLog> list(WvgOperationLog operationLog, Pageable pageable) {
		return logService.queryAll(operationLog, pageable);
	}
	
	@Log("日志详细信息查询")
	@GetMapping("/{id}")
	public @ResponseBody WvgOperationLog get(@PathVariable("id") Long id) {
		return logService.get(id);
	}
	
	@GetMapping
    public String index(){
        return "log/list";
    }
	
	@GetMapping("/detail")
    public String detail(){
        return "log/detail";
    }

}
