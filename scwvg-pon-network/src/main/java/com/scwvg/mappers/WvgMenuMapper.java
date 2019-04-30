package com.scwvg.mappers;

import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：查询用户所属菜单
 **/
@Mapper
public interface WvgMenuMapper {
    /*查询所有菜单*/
    public List<WvgMenu> findAll();
    /*查询用户所属ID的菜单（权限）*/
    public List<WvgMenu> findByUserIDMenu(Integer wvg_user_id);
}
