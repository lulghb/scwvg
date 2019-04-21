package com.scwvg.system.service;

import com.scwvg.system.entitys.WvgMenu;
import com.scwvg.system.entitys.WvgUser;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/20 17:50
 * @Description: TODO TODO 查询树结构菜单
 * version 0.1
 */
public interface WvgMenuService {

    /**
     * @Description: 查询用户角色拥有的菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */

    public List<WvgMenu> findWvgMenuByUser(WvgUser wvgUser);

    /** 
     * @Description: 查询其所有祖宗菜单
     * @Param:  
     * @return:  
     * @Author: chen.baihoo
     * @Date: 2019/4/20 
     */ 
    public List<WvgMenu> findWvgMenuIsForebear(WvgMenu wvgMenu);
    /** 
     * @Description: 查询子孙菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20 
     */ 
    public List<WvgMenu> findWvgMenuIsProgeny(WvgMenu wvgMenu);
}
