package com.scwvg.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/21 9:51
 * @Description: TODO
 * version 0.1
 */
@Data
public class UserVO {
    //用户ID
    @JsonProperty("wvgUserId")
    private  Long wvgUserId;

    //用户登录名
    @JsonProperty("wvgLoginName")
    private  String wvgLoginName;

    //用户真实姓名
    @JsonProperty("wvgRealName")
    private  String wvgRealName;

    //用户证件类型
    @JsonProperty("wvgIdType")
    private  String wvgIdType;

    //专业ID（wvg_spec_type专业表外键ID）
    @JsonProperty("wvgSpecId")
    private  long wvgSpecId;

    //证件号
    @JsonProperty("wvgIdNumber")
    private  String wvgIdNumber;

    //联系电话
    @JsonProperty("wvgUserIphone")
    private  String wvgUserIphone;

    //账号有效期
    @JsonProperty("wvgAccountDate")
    private  String wvgAccountDate;

    //密码有效期
    @JsonProperty("wvgPasswordDate")
    private  String wvgPasswordDate;

    //账号新增时间
    @JsonProperty("wvgAddTime")
    private  String wvgAddTime;

    //用户修改时间
    @JsonProperty("wvgUpdateTime")
    private  String wvgUpdateTime;

    //账号状态（0,活动   1,锁定）
    @JsonProperty("wvgAccountTypeDict")
    private  String wvgAccountTypeDict;

    //账号登录时间
    @JsonProperty("wvgLoginTime")
    private  String wvgLoginTime;

    //账号登录原地址IP
    @JsonProperty("wvgLoginIp")
    private  String wvgLoginIp;

    //账号备注
    @JsonProperty("wvgAccountRemarks")
    private  String wvgAccountRemarks;

    //是否过期帐户
    @JsonProperty("accountNonExpired")
    private boolean accountNonExpired;

    //是否被锁
    @JsonProperty("accountNonLocked")
    public boolean accountNonLocked;

    //是否认证信息有效
    @JsonProperty("credentialsNonExpired")
    public boolean credentialsNonExpired;

    //是否禁用
    @JsonProperty("enabled")
    public boolean enabled;

}
