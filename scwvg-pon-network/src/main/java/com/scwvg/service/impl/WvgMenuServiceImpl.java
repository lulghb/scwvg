package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgMenuMapper;
import com.scwvg.service.WvgMenuService;
import com.scwvg.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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

    Msg msg=new Msg();;

    @Autowired
    private WvgMenuMapper wvgMenuMapper;

    @Override
    public Page<WvgMenu> queryMenuAll(Map<String, Object> params, Page<WvgMenu> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Page<WvgMenu> wvgMenus = wvgMenuMapper.queryMenuAllByPage(params);
        for(WvgMenu menu:wvgMenus){
           menu.setChangeStr(wvgMenuMapper.getWvgUserName(menu.getWvg_user_id()));
           menu.setChangeStr1(menu.getWvg_menu_state()==1?"启用":"停用");
        }
        return wvgMenus;
    }

    /*菜单新增*/
    @Override
    public Msg save(WvgMenu wvgMenu) {
        /*获取ID*/
        Long wvg_menu_id=querMaxMenuId();
        wvgMenu.setWvg_menu_id(wvg_menu_id);
        WvgUser user=UserUtil.getLoginUser();
        wvgMenu.setWvg_user_id(user.getWvg_user_id() );
        int res= wvgMenuMapper.insertMenu(wvgMenu);
        msg.setCode(res==1? "0":"1");
        log.debug("新增菜单"+wvgMenu.getWvg_menu_name());
        return msg;
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
    public Long querMaxMenuId(){
      int wvg_menu_id=wvgMenuMapper.queryMaxMenuID();
      return wvg_menu_id+1L;
    }
}
