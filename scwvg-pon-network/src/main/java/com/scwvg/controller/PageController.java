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
	 * 操作日志列表页面
	 * @return
	 */
	@GetMapping("/log/list")
	public String log_list() {
		return "sys/log/list";
	}
	
	/**
	 * 操作日志详细信息页面
	 * @return
	 */
	@GetMapping("/log/detail")
	public String log_detail() {
		return "sys/log/detail";
	}
	
	/**
	 * 登录日志页面
	 * @return
	 */
	@GetMapping("/lonlog/list")
	public String lonlog_detail() {
		return "sys/log/login/list";
	}
	
	/**
	 * 首页
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
	 * 菜单操作
	 * @return
	 */
	@GetMapping("/menu/addMenus")
	public String addMenus(){
		return "sys/menu/menuOption";
	}
	/**
	 * 菜单样式
	 * @return
	 */
	@GetMapping("/menu/css")
	public String css(){
		return "sys/menu/icon";
	}
	/**
	 * 专业管理
	 * @return
	 */
	@GetMapping("/spec/specManager")
	public String specManager(){
		return "sys/spec/specManager";
	}
	/**
	 * 专业添加
	 * @return
	 */
	@GetMapping("/spec/addSpec")
	public String addSpec(){
		return "sys/spec/specOption";
	}
	/**
	 * 专业修改
	 * @return
	 */
	@GetMapping("/spec/editSpec")
	public String editSpec(){
		return "sys/spec/specOption";
	}

    /**
     * 厂家管理
     * @return
     */
    @GetMapping("/vendor/manger")
    public String vendorManger(){
        return "sys/vendor/vendorManger";
    }

	/**
	 * 厂家新增
	 * @return
	 */
	@GetMapping("/vendor/addVendor")
	public String addVendor(){
		return "sys/vendor/vendorOption";
	}


	/**
	 * 厂家修改
	 * @return
	 */
	@GetMapping("/vendor/editVendor")
	public String editVendor(){
		return "sys/vendor/vendorOption";
	}



	/**
	 * 资源列表
	 * @return
	 */
	@GetMapping("/res/list")
	public String res_list(){
		return "sys/res/list";
	}
	
	/**
	 * 新增资源
	 * @return
	 */
	@GetMapping("/res/add")
	public String res_add(){
		return "sys/res/add";
	}
	
	/**
	 * 编辑资源
	 * @return
	 */
	@GetMapping("/res/edit")
	public String res_edit(){
		return "sys/res/edit";
	}
	
	/**
	 * 批量导入资源
	 * @return
	 */
	@GetMapping("/res/import")
	public String res_import(){
		return "sys/res/import";
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

	/**
	 * 告警明细
	 * @return
	 */
	@GetMapping("/flux/manager")
	public String fluxManager(){
		return "sys/flux/fluxManager";
	}

	/**
	 * 采集模型库管理
	 * @return
	 */
	@GetMapping("/lib/manger")
	public String libManager(){
		return "sys/lib/gatherLibManage";
	}

	/**
	 * 采集模型库新增
	 * @return
	 */
	@GetMapping("/lib/addcmdbase")
	public String addcmdbase(){
		return "sys/lib/cmdAdd";
	}
	/**
	 * 采集模型库修改
	 * @return
	 */
	@GetMapping("/lib/editcmdbase")
	public String editcmdbase(){
		return "sys/lib/cmdEdit";
	}
	/**
	 * 宏指令新增
	 * @return
	 */
	@GetMapping("/lib/addInduction")
	public String addInduction(){
		return "sys/lib/IdtAdd";
	}

	/**
	 * 宏指令新增
	 * @return
	 */
	@GetMapping("/lib/editInduction")
	public String editInduction(){
		return "sys/lib/IdtEdit";
	}

}
