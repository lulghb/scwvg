package com.scwvg.controller;

import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：用户登录的时候，去查询用户的token是否已过期。
 * 没过期就进行免登陆
 **/
@Api(tags = "用户")
@RestController
@RequestMapping("/users")
public class WvgUserController {
    @ApiOperation(value = "当前登录用户")
    /*提供给前台做token验证*/
    @GetMapping("/current")
    public WvgUser currentUser() {
        return UserUtil.getLoginUser();
    }

}
