package com.scwvg.entitys.scwvgponnetwork;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain 用户实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WvgUser extends BaseEntity<Long> implements Serializable  {
    private static final long serialVersionUID = -6525908145032868837L;
    private Long wvg_user_id			;//用户ID ,
    private String wvg_login_name		;//用户登录名 ,
    private String wvg_real_name		;//用户真实姓名 ,
    private String wvg_id_type			;//用户证件类型 ,
    private Integer wvg_spec_id			;//专业ID（wvg_spec_type专业表外键ID） ,
    private String wvg_id_number		;//证件号 ,
    private String wvg_user_iphone		;//联系电话 ,
    private Integer wvg_account_data	;//账号有效期 ,
    private Integer wvg_account_enabled	;//账号是否激活（0未激活，1激活） ,
    private String wvg_user_password	;//用户密码 ,
    private Integer wvg_password_data	;//密码有效期 ,
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date wvg_add_time	 	    ;//账号新增时间 ,
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date wvg_update_time		;//账号修改时间 ,
    private Integer wvg_account_type	;//账号状态（0,活动   1,锁定） ,
    private String wvg_login_time		;//账号登录时间 ,
    private String wvg_login_ip		    ;//账号登录原地址IP ,
    private String wvg_account_remarks	;//账号备注 ,


    private int wvg_act_date; //账号是否到期（1，不到期  0,到期）
    private int Wvg_pwd_date; //密码是否到期（1，不到期  0,到期）
/*    *//*用户所属角色集合*//*
    private List<Long> wvg_role_ids;
    private List<WvgMenu> menuLists;*/

}
