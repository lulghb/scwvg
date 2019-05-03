package com.scwvg.service;

import com.scwvg.entitys.scwvgponnetwork.WvgMenu;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：菜单操作Service
 **/
public interface WvgMenuService {
    /*新增菜单*/
    void save(WvgMenu wvgMenu);
    /*修改菜单*/
    void update(WvgMenu wvgMenu);
    /*删除菜单*/
    void delete(Long id);
}
