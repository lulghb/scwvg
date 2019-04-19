package com.scwvg.system.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 13:07
 * @Description: TODO 用户角色权限
 * version 0.1
 */
@Entity(name="wvg_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WvgRole implements GrantedAuthority {

    //角色ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wvg_role_id")
    private  long wvgRoleId;

    //角色英文名称
    @NotEmpty(message = "{WvgUser.wvgRoleName.notnull}")
    @Size(min=2, max=30)
    @Column(name="wvg_role_name" , nullable = false, length = 30)
    private  String wvgRoleName;

    //角色中文名称
    @NotEmpty(message = "{WvgUser.wvgRoleShow.notnull}")
    @Size(min=2, max=30)
    @Column(name="wvg_role_show" , nullable = false, length = 30)
    private  String wvgRoleShow;

    //角色类型（0，全部（包括1和2），1，操作<增，删，改> 2，查询）
    @Column(name="wvg_role_type")
    private  long wvgRoleType = 0;

    //角色新增时间
    @Column(name="wvg_add_time")
    private  String wvgAddTime;

    //新增人
    @Column(name="wvg_user_id")
    private  long wvgUserId;

    // 用户和权限角色，多对多映射关系配置
    @ManyToMany(mappedBy = "roleList")
    private List<WvgUser> userList;

    // 角色与菜单关系
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "wvg_role_menu" ,
            joinColumns = @JoinColumn(name = "wvg_role_id",referencedColumnName = "wvg_role_id"),
            inverseJoinColumns = @JoinColumn(name = "wvg_menu_id" , referencedColumnName = "wvg_menu_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<WvgMenu> menuList;

    // 角色与功能权限关系
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "wvg_role_perm" ,
            joinColumns = @JoinColumn(name = "wvg_role_id",referencedColumnName = "wvg_role_id"),
            inverseJoinColumns = @JoinColumn(name = "wvg_perm_id" , referencedColumnName = "wvg_perm_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<WvgPerm> permList;

    /** 
     * @Description: 获取权限：角色名称
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public String getAuthority() {
        return wvgRoleName;
    }
}
