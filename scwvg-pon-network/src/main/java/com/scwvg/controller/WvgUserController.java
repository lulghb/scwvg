package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.service.WvgUserService;
import com.scwvg.utils.PageInfo;
import com.scwvg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasAuthority('sys:user:list')")
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
}
