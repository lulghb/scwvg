package com.scwvg.sys.auth.from;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 17:09
 * @Description: TODO 用户登陆表单类
 * version 0.1
 */
@Data
public class
UserFrom {
    // 用户名
    @NotEmpty(message = "UserFrom.username.notnull")
    private String username;
    // 密码
    @NotEmpty(message = "UserFrom.password.notnull")
    private String password;
    // 检验码
    @NotEmpty(message = "UserFrom.vercode.notnull")
    private String vercode;
}
