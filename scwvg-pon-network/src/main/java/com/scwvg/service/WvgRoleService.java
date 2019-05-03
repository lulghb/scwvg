package com.scwvg.service;

import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色操作Service
 **/
public interface WvgRoleService {
    /**
     * 保存角色ID和菜单ID
     * @param roleMenu
     */
    void saveRole(WvgRoleMenu roleMenu);
    /**
     * 删除角色
     * @param
     */
    void deleteRole(Long id);
}
