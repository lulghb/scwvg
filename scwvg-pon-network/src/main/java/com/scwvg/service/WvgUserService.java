package com.scwvg.service;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginLog;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleUser;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：
 **/
public interface WvgUserService {
    /*查找所有用户*/
    Page<WvgUser> queryAllUsers(Map<String,Object> params, Page<WvgUser> page);
    WvgUser saveUser(WvgRoleUser roleUsers);
    WvgUser updateUser(WvgRoleUser roleUser);
    WvgUser querUserInfo(String username);
    void changePassword(String username, String oldPassword, String newPassword);
    /*用户tonke过期或者其他的需要重新登录时，先进行删除用户表的字段*/
    void updateLgIpAndlgDateAndlgonlineState(WvgLoginUser user);
    /*用户登录成功插入用户登录IP地址与登录时间*/
    void upDateUsLoginTimeAndIp(WvgLoginUser loginUser);

}
