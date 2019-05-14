package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.service.WvgUserService;
import com.scwvg.utils.PageInfo;
import com.scwvg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ReactiveTypeDescriptor;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：用户登录的时候，去查询用户的token是否已过期。 没过期就进行免登陆
 **/
@Api(tags = "用户")
@RestController
@RequestMapping("/users")
public class WvgUserController {

    @Autowired
    WvgUserService wvgUserService;


    @ApiOperation(value = "当前登录用户")
    /*提供给前台做token验证*/
    @GetMapping("/current")
    public @ResponseBody
    WvgUser currentUser() {
        return UserUtil.getLoginUser();
    }


    @GetMapping("/userList")
    @ApiOperation(value = "用户列表")
    @Log("用户列表查询")
    @PreAuthorize("hasAuthority('users:query:list')")
    public @ResponseBody PageInfo<WvgUser> queryAllUsers(WvgUser wvgUser, Page<WvgUser> page) {
        Map<String, Object> params = new HashMap<>();
        params.put("wvg_login_name", wvgUser.getWvg_login_name());
        params.put("wvg_real_name", wvgUser.getWvg_real_name());
        params.put("wvg_user_iphone", wvgUser.getWvg_user_iphone());
        Page<WvgUser> queryAllUser = wvgUserService.queryAllUsers(params, page);
        PageInfo<WvgUser> pageInfo = new PageInfo<>(queryAllUser.getPageNum(), queryAllUser.getPageSize(), queryAllUser.getTotal());
        pageInfo.setTotalPage(queryAllUser.getPages());
        pageInfo.setItems(queryAllUser.getResult());
        return pageInfo;
    }

    /*用户新增*/
    @PostMapping("/add/user")
    @ApiOperation(value = "用户新增")
    @Log("用户新增")
    @PreAuthorize("hasAuthority('users:add')")
    public Msg saveUser(WvgUser user){
        return wvgUserService.saveUser(user);
    }
    /*用户修改*/
    @PostMapping("/edit/user")
    @ApiOperation(value = "用户修改")
    @Log("用户修改")
    @PreAuthorize("hasAuthority('users:update')")
    public Msg editUser(WvgUser user){
        return wvgUserService.updateUser(user);
    }

    @GetMapping("/quit/{wvg_user_id}")
    @Log("用户退出，踢用户下线")
    @PreAuthorize("hasAuthority('users:quit')")
    public Msg offlineUser(@PathVariable Long wvg_user_id){
        return wvgUserService.userOffline(wvg_user_id);
    }

    @GetMapping("/reset/{wvg_user_id}")
    @Log("密码重置！")
    @PreAuthorize("hasAuthority('users:reset')")
    public Msg reset(@PathVariable Long wvg_user_id){
        return wvgUserService.userReset(wvg_user_id);
    }

    @GetMapping("/delete/{wvg_user_id}")
    @Log("用户删除！")
    @PreAuthorize("hasAuthority('users:del')")
    public Msg delete(@PathVariable Long wvg_user_id){
        return wvgUserService.deleteUser(wvg_user_id);
    }
}
