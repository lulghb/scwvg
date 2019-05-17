package com.scwvg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
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
	
	/**
	 * 登录日志页面
	 * @return
	 */
	@GetMapping("/console")
	public String console() {
		return "home/wvg-console";
	}

	/**
	 * 用户管理
	 * @return
	 */
	@GetMapping("/user/getUserList")
	public String userList(){
		return "sys/user/userManager";
	}
	/**
	 * 用户新增
	 * @return
	 */
	@GetMapping("/user/addUser")
	public String userAdd(){
		return "sys/user/addUser";
	}
	/**
	 * 用户修改
	 * @return
	 */
	@GetMapping("/user/editUser")
	public String userEdit(){
		return "sys/user/editUser";
	}

	/**
	 * 角色管理
	 * @return
	 */
	@GetMapping("/role/roleManger")
	public String roleManger(){
		return "sys/role/roleManager";
	}

	/**
	 * 角色新增
	 * @return
	 */
	@GetMapping("/role/addRole")
	public String addRole(){
		return "sys/role/addRole";
	}

	/**
	 * 菜单管理
	 * @return
	 */
    @GetMapping("/menu/menuManger")
	public String menuManger(){
		return "sys/menu/menuManger";
	}


	/**
	 * 资源列表
	 * @return
	 */
	@GetMapping("/res/list")
	public String res_list(){
		return "res/list";
	}

	/**
	 * 告警查询列表
	 * @return
	 */
	@GetMapping("/am/list")
	public String am_list(){
		return "am/list";
	}

	/**
	 * 告警明细
	 * @return
	 */
	@GetMapping("/am/detail")
	public String am_detail(){
		return "am/detail";
	}

}
