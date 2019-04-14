package com.scwvg.entitys;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 12:38
 * @Description: TODO 登陆用户
 * version 0.1
 */
@Entity(name="wvg_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WvgUser  implements UserDetails, Serializable {

    //用户ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wvg_user_id")
    private  long wvgUserId;

    //用户登录名
    @NotEmpty(message = "{WvgUser.wvgLoginName.notnull}")
    @Size(min=2, max=30)
    @Column(name="wvg_login_name" , nullable = false, length = 30)
    private  String wvgLoginName;

    //用户真实姓名
    @NotEmpty(message = "{WvgUser.wvgRealName.notnull}")
    @Size(min=2, max=11)
    @Column(name="wvg_real_name" , nullable = false, length = 11)
    private  String wvgRealName;

    //用户证件类型
    @NotEmpty(message = "{WvgUser.wvgIdType.notnull}")
    @Size(min=11, max=11)
    @Column(name="wvg_id_type" , nullable = false, length = 11)
    private  String wvgIdType;

    //专业ID（wvg_spec_type专业表外键ID）
    @Column(name="wvg_spec_id")
    private  long wvgSpecId;

    //证件号
    @NotEmpty(message = "{WvgUser.wvgIdNumber.notnull}")
    @Size(min=17, max=18)
    @Column(name="wvg_id_number" , nullable = false, length = 18)
    private  String wvgIdNumber;

    //联系电话
    @NotEmpty(message = "{WvgUser.wvgUserIphone.notnull}")
    @Size(min=11, max=11)
    @Column(name="wvg_user_iphone" , nullable = false, length = 11)
    private  String wvgUserIphone;

    //账号有效期
    @Column(name="wvg_account_data")
    private  long wvgAccountData = 12;

    //用户密码
    @NotEmpty(message = "{WvgUser.wvgUserPassword.notnull}")
    @Size(min=15, max=60)
    @Column(name="wvg_user_password" , nullable = false, length = 60)
    private  String wvgUserPassword;

    //密码有效期
    @Column(name="wvg_password_data")
    private  long wvgPasswordData = 3;

    //账号新增时间
    @Column(name="wvg_add_time")
    private  String wvgAddTime;

    //用户修改时间
    @Column(name="wvg_update_time")
    private  String wvgUpdateTime;

    //账号状态（0,活动   1,锁定）
    @Column(name="wvg_account_type")
    private  long wvgAccountType = 0;

    //账号登录时间
    @Column(name="wvg_login_time")
    private  String wvgLoginTime;

    //账号登录原地址IP
    @Column(name="wvg_login_ip")
    private  String wvgLoginIp;

    //用户权限（<0所有权> <1查询权><2新增权><3删除权><4修改权><5查询权and新增权><6新增and修改><7删除and修改><04增删改>）根据此字段在页面封装隐藏显示菜单\r\n
    @Column(name="wvg_user_power")
    private  long wvgUserPower = 0;

    //账号备注
    @Column(name = "wvg_account_remarks")
    private  String wvgAccountRemarks;

    // 用户和权限角色，多对多映射关系配置
    @ManyToMany(cascade = CascadeType.DETACH , fetch = FetchType.EAGER)
    @JoinTable(name = "wvg_role_user" ,
            joinColumns = @JoinColumn(name = "wvg_user_id",referencedColumnName = "wvg_user_id"),
            inverseJoinColumns = @JoinColumn(name = "wvg_role_id" , referencedColumnName = "wvg_role_id")
    )
    private List<WvgRole> roleList;

    /** 
     * @Description: 需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        if(this.roleList !=null) {
            this.roleList.forEach(e->{
                simpleAuthorities.add(new SimpleGrantedAuthority(e.getAuthority()));
            });
        }
        return simpleAuthorities;
    }

    @Override
    public String getPassword() {
        return this.wvgUserPassword;
    }
    @Override
    public String getUsername() {
        return this.wvgLoginName;
    }
    /** 
     * @Description: 指示用户帐户是否已过期。过期帐户无法进行身份验证。重载默认是false,我们需要改成true
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * @Description: 指示用户是锁定还是解锁。无法对锁定的用户进行身份验证。重载默认是false,我们需要改成true
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * @Description: 指示用户的凭据(密码)是否已过期。过期凭据阻止身份验证。重载默认是false,我们需要改成true
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /** 
     * @Description: 指示用户是否已启用或禁用,不能对禁用用户进行身份验证。重载默认是false,我们需要改成true
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public boolean isEnabled() {
        return true;
    }

    /** 
     * @Description: 对密码 BCrypt 加密
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    public void setPasswordEncoder(String password) {
        PasswordEncoder  encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.wvgUserPassword = encodePasswd;
    }
}
