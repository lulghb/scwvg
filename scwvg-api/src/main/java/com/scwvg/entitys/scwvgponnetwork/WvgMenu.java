package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/29
 * @Explain：菜单表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WvgMenu implements Serializable {
    private Integer wvg_menu_id    		;//  菜单ID
    private Integer wvg_parent_id  		;//   COMMENT  父级菜单ID
    private String  wvg_menu_name  		;//  菜单名称
    private String  wvg_menu_url  		;//  菜单地址
    private char    wvg_menu_type 		;//  菜单类型 L根节点，M树节点，N叶子节点)
    private String  wvg_menu_type_name  ;//  菜单类型名称（根节点，树节点，叶子节点）
    private String  wvg_add_time 		;//  菜单新增时间
    private String  wvg_updata_time  	;//  修改时间
    private String  wvg_menu_icon  		;//  菜单图标
    private Integer wvg_menu_state  	;//  菜单状态（0，根菜单   1，挂载  2，未挂载）
    private String  wvg_seq  			;//  树级菜单序列字段
    private Integer wvg_user_id  	    ;//  创建人
}
