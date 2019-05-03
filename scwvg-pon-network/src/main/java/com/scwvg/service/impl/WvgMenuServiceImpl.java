package com.scwvg.service.impl;

import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.mappers.WvgMenuMapper;
import com.scwvg.service.WvgMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain： 菜单类实现类
 **/
@Service
public class WvgMenuServiceImpl implements WvgMenuService {
    private static final Logger log = LoggerFactory.getLogger("WvgMenuServiceImpl");

    @Autowired
    private WvgMenuMapper wvgMenuMapper;

    /*菜单新增*/
    @Override
    public void save(WvgMenu wvgMenu) {
        /*获取ID*/
        int wvg_menu_id=querMaxMenuId();
        wvgMenu.setWvg_menu_id(wvg_menu_id);
        wvgMenuMapper.insertMenu(wvgMenu);
        log.debug("新增菜单"+wvgMenu.getWvg_menu_name());
    }

    /*菜单修改*/
    @Override
    public void update(WvgMenu wvgMenu) {
      wvgMenuMapper.updateMenu(wvgMenu);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        /*删除父级菜单表*/
        wvgMenuMapper.deleteMenuParentId(id);
        /*删除菜单表数据*/
        wvgMenuMapper.deleteMenu(id);
        log.debug("菜单删除！");
    }

    /**
     * 查询菜单表里最大的ID进行+1
     * @return
     */
    public int querMaxMenuId(){
      int wvg_menu_id=wvgMenuMapper.queryMaxMenuID();
      return wvg_menu_id+1;
    }
}
