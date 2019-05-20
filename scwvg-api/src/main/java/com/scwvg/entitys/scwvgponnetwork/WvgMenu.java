package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：菜单表
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WvgMenu extends BaseEntity<Long>{
    private Long wvg_menu_id;      //菜单ID',
    private Long wvg_parent_id;    //父级菜单ID',
    private String wvg_menu_name;   //菜单名称',
    private String wvg_menu_url;    //菜单地址',
    private String wvg_menu_type;     //菜单类型(L根节点，M树节点，N叶子节点)',
    private String wvg_menu_type_name;//菜单类型名称（根节点，树节点，叶子节点）',
    private String wvg_authority;    //权限标识',
    private Date wvg_add_time; //菜单新增时间',
    private Date wvg_updata_time;//修改时间',
    private String wvg_menu_css; //菜单样式',
    private String wvg_menu_icon; //菜单图标',
    private Integer wvg_menu_state;  //菜单状态（0，根菜单   1，挂载  2，未挂载）',
    private String wvg_menu_explain;  //对菜单功能进行说明
    private Long wvg_user_id;   //创建人',

    private List<WvgMenu> child; //下级菜单集合

}
