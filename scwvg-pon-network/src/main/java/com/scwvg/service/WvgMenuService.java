package com.scwvg.service;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：菜单操作Service
 **/
public interface WvgMenuService {
    /**
     * 查询菜单（菜单管理）
     * @param params
     * @param page
     * @return
     */
    public Page<WvgMenu> queryMenuAll(Map<String,Object> params, Page<WvgMenu> page);
    /**
     * 新增菜单
     * @param wvgMenu
     */
    void save(WvgMenu wvgMenu);

    /**
     * 修改菜单
     * @param wvgMenu
     */
    void update(WvgMenu wvgMenu);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);
}
