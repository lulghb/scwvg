package com.scwvg.service;

import com.scwvg.entitys.scwvgponnetwork.WvgRoleUser;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：
 **/
public interface WvgUserService {
    WvgUser saveUser(WvgRoleUser roleUsers);
    WvgUser updateUser(WvgRoleUser roleUser);
    WvgUser querUserInfo(String username);
    void changePassword(String username, String oldPassword, String newPassword);
}
