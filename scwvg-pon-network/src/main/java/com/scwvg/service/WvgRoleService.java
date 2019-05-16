package com.scwvg.service;

import com.github.pagehelper.Page;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色操作Service
 **/
public interface WvgRoleService {
    /**
     * 查询所有角色
     * @param params
     * @param page
     * @return
     */
    Page<WvgRole> queryAllRols(Map<String,Object> params, Page<WvgRole> page);

    /**
     * 查询所有的权限与菜单
     * @return
     */
    public List<WvgMenu> roledTreeList();

    /**
     * 新增角色
     * @param role
     * @return
     */
    Msg addRole(WvgRole role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    Msg editRole(WvgRole role);

    /**
     * 删除角色
     * @param wvg_role_id
     * @return
     */
    Msg delRoles(Long wvg_role_id);
}
