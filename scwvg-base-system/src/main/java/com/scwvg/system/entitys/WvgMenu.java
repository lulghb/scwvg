package com.scwvg.system.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月19日11点04分
 * @Description: TODO 菜单
 * version 0.1
 */
@Entity(name="wvg_menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WvgMenu implements Serializable {

    //菜单ID
    @Id
    @Column(name="wvg_menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long wvgMenuId;

    //父级菜单ID
    @Column(name = "wvg_parent_id")
    @NotEmpty(message = "WvgMenu.wvgParentId.notnull")
    private  long wvgParentId = 0;

    //顶级菜单ID
    @Column(name = "wvg_top_id",columnDefinition = "int(11) DEFAULT 0 COMMENT '顶级菜单ID'")
    private  long wvgTopId;

    //菜单名称
    @Column(name = "wvg_menu_name")
    @Size(min = 2,max = 30 , message = "WvgMenu.wvgMenuName.size")
    @NotEmpty(message = "WvgMenu.wvgMenuName.notnull")
    private  String wvgMenuName;

    //菜单地址
    @Column(name = "wvg_menu_url")
    private  String wvgMenuUrl;

    //菜单新增时间
    @Column(name = "wvg_add_time")
    private  String wvgAddTime;

    //修改时间
    @Column(name = "wvg_updata_time")
    private  String wvgUpdataTime;

    //菜单图标
    @Column(name = "wvg_menu_icon")
    private  String wvgMenuIcon;

    //菜单排序
    @Column(name = "wvg_menu_Order" , nullable = false,
            columnDefinition = "int(3) DEFAULT 0 COMMENT '菜单排序'")
    @NotEmpty(message = "WvgMenu.wvgMenuOrder.notnull")
    private int wvgMenuOrder = 0;

    //菜单状态（0，根菜单   1，挂载  2，未挂载）
    @Column(name = "wvg_menu_state")
    @NotEmpty(message = "WvgMenu.wvgMenuState.notnull")
    private  long wvgMenuState = 2;

    // 树级菜单序列字段
    @Column(name = "wvg_seq", columnDefinition = "varchar(512) COMMENT '树级菜单序列字段，格式: .0.1.2.3.4.'")
    private  String wvgSeq;

    // 树级菜单层级
    @Column(name = "wvg_menu_level",nullable = false ,
            columnDefinition = "int(2) DEFAULT 0 COMMENT '树级菜单层级'")
    @NotEmpty(message = "WvgMenu.wvgMenuLevel.notnull")
    private int wvgMenuLevel = 0;

    // 角色与菜单关系
    @ManyToMany(mappedBy = "menuList")
    private List<WvgRole> roleList;

}
