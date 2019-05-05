package com.scwvg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/03 22:16
 * @desc: 页面控制器
**/
@Controller
@RequestMapping("/page")
public class PageController {
	
	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/map/hljmap")
	public String map() {
		return "map/hljmap";
	}
	
	/**
	 * 操作日志列表页面
	 * @return
	 */
	@GetMapping("/log/list")
	public String log_list() {
		return "log/list";
	}
	
	/**
	 * 操作日志详细信息页面
	 * @return
	 */
	@GetMapping("/log/detail")
	public String log_detail() {
		return "log/detail";
	}
	
	/**
	 * 登录日志页面
	 * @return
	 */
	@GetMapping("/lonlog/list")
	public String lonlog_detail() {
		return "log/login/list";
	}

}