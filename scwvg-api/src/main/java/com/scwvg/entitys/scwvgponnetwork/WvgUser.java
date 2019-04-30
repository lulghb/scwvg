package com.scwvg.entitys.scwvgponnetwork;

import com.scwvg.entitys.Msg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/29
 * @Explain：用户表
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WvgUser implements Serializable {
    private Integer wvg_user_id;  //用户ID
    private String wvg_login_name;//用户登录名
    private String wvg_real_name;//用户真实姓名
    private String wvg_id_type;//用户证件类型
    private Integer wvg_spec_id;//专业ID（wvg_spec_type专业表外键ID）
    private String wvg_id_number;//证件号
    private String wvg_user_iphone;//联系电话
    private Integer wvg_account_enabled;//账号是否激活（0未激活，1激活）
    private Integer wvg_account_data;//账号有效期
    private String wvg_user_password;//用户密码
    private Integer wvg_password_data;//密码有效期
    private Date wvg_add_time;//账号新增时间
    private Date wvg_update_time;//账号修改时间
    private Integer wvg_account_type;//账号状态（0,活动   1,锁定）
    private String wvg_login_time;//账号登录时间
    private String wvg_login_ip;//账号登录原地址IP
    private Integer wvg_user_power;//用户权限（<0所有权> <1查询权><2新增权><3删除权><4修改权><5查询权and新增权><6新增and修改><7删除and修改><04增删改>）根据此字段在页面封装隐藏显示菜单
    private String wvg_account_remarks;//账号备注

    /**
     * 存储账号有效时间
     */
    private int Wvg_act_date;
    /**
     * 存储密码有效时间
     */
    private int Wvg_pwd_date;
    /**
     * 存储用户所属角色
     */
    private List<WvgRole> roleList;

    Msg msg =new Msg();
    boolean res=true;


    /**
     * 对密码 BCrypt 动态加密
     * @param password
     */
    public void setPasswordEncoder(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.wvg_user_password = encodePasswd;
    }
}
