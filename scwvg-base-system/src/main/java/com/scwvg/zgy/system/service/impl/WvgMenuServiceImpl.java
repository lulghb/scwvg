package com.scwvg.zgy.system.service.impl;


import com.scwvg.zgy.system.entitys.WvgMenu;
import com.scwvg.zgy.system.entitys.WvgRole;
import com.scwvg.zgy.system.entitys.WvgUser;
import com.scwvg.zgy.system.mapper.WvgMenuMapper;
import com.scwvg.zgy.system.service.WvgMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/20 21:31
 * @Description: TODO
 * version 0.1
 */
@Service("wvgMenuService")
public class WvgMenuServiceImpl implements WvgMenuService {

    @Resource
    private WvgMenuMapper wvgMenuMapper;
    /**
     * @param wvgUser
     * @Description: 查询用户角色拥有的菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */
    @Override
    public List<WvgMenu> findWvgMenuByUser(WvgUser wvgUser) {
        if(wvgUser!=null && wvgUser.getRoleList() !=null){
            Set<WvgRole> roles = wvgUser.getRoleList();
            List<Long> longs = roles.stream().map(WvgRole::getWvgRoleId).distinct().collect(Collectors.toList());
            return wvgMenuMapper.findDistinctByRoleListIsIn(longs);
        }

        return null;
    }

    /**
     * @param wvgMenu
     * @Description: 查询其所有祖宗菜单
     * @Param:
     * @return:
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */
    @Override
    public List<WvgMenu> findWvgMenuIsForebear(WvgMenu wvgMenu) {
        // 注意：如果是 根菜单 wvgSeq 就是空的，格式：.0.1.2.3.4.
        if(wvgMenu !=null && wvgMenu.getWvgSeq()!=null){
            // 1. 获取序列的菜单id
            String wvgSeq = wvgMenu.getWvgSeq();
            // 2. 取出字符串前和结尾后的点；结果格式：0.1.2.3.4
            wvgSeq = wvgSeq.substring(1,wvgSeq.length()-1);
            // 3. 替换 点 为数据库识别 逗号；结果格式：0,1,2,3,4
            List<Long> menuids = new ArrayList<>();
            Arrays.asList(wvgSeq.split("\\.")).forEach(e->{
                menuids.add(Long.parseLong(e));
            });

           return wvgMenuMapper.findByWvgMenuIdEqualsOrWvgMenuIdIsIn(wvgMenu.getWvgMenuId(),menuids);
        }

        return null;
    }

    /**
     * @param wvgMenu
     * @Description: 查询子孙菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */
    @Override
    public List<WvgMenu> findWvgMenuIsProgeny(WvgMenu wvgMenu) {
        // 注意：如果是 根菜单 wvgSeq 就是空的，格式：.0.1.2.3.4.
        // like "%.3.%"
        // .3.
        // .3.4.
        // .3.4.5.
        if(wvgMenu !=null){
          return wvgMenuMapper.findByWvgMenuWvgSeqIsLike("."+wvgMenu.getWvgMenuId()+".");
        }
        return null;
    }
}
