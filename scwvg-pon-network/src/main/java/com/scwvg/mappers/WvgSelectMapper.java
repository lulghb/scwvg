package com.scwvg.mappers;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain：
 **/
@Mapper
public interface WvgSelectMapper {
    //查询用户身份类型
    @MapKey("user_type_id")
    @Select("select user_type_id,user_type_name from wvg_user_type")
    public Map<Integer,Map<String,Object>> queryUserIdType();
    //查询属地
    @MapKey("city_id")
    @Select("select DISTINCT city_id,city_name from wvg_city")
    public  Map<String,Map<String,Object>> querCitys();
    //查询专业
    @MapKey("spec_id")
    @Select("select DISTINCT spec_id,spec_name from wvg_spec_type ")
    public  Map<Integer,Map<String,Object>> querySpecs();

    //查询角色
    @MapKey("wvg_role_id")
    @Select("select DISTINCT wvg_role_id,wvg_role_description from wvg_role")
    public Map<Integer,Map<String,Object>> queryRoles();
    //查询父节点菜单
    @MapKey("wvg_menu_id")
    @Select("select wvg_menu_id,wvg_menu_name from wvg_menu where wvg_menu_type='L'")
    Map<Integer,Map<String,Object>> queryParentMenus();
}
