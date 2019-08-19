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
    @Select("select wvg_menu_id,wvg_menu_name from wvg_menu where (wvg_menu_type='L' or wvg_menu_type='N')")
    Map<Integer,Map<String,Object>> queryParentMenus();

    //厂家查询
    @MapKey("res_parent_id")
    @Select("select  DISTINCT res_parent_id,res_vendor_name from wvg_res_vendor;")
    Map<Integer,Map<String,Object>> queryVendorS();

    /**
     * 采集协议查询
     * @return
     */
    @MapKey("cmd_type_id")
    @Select("select cmd_type_id,cmd_type_name from wvg_protocol_type")
    Map<Integer,Map<String,Object>> queryProtocol();

    /**
     * 指令操作类型查询
     * @return
     */
    @MapKey("opt_type_id")
    @Select("select opt_type_id,opt_type_name from wvg_opt_type")
    Map<Integer,Map<String,Object>> queryOptType();

    /**
     * 数据类型查询
     * @return
     */
    @MapKey("data_type_id")
    @Select("select data_type_id,data_type_name from wvg_data_type")
    Map<Integer,Map<String,Object>> queryDataType();

    /**
     * 资源类型查询
     * @return
     */
    @MapKey("res_type_id")
    @Select("select res_type_id,res_type_name from wvg_res_type")
    Map<Integer,Map<String,Object>> queryResType();

    /**
     * 数据库选择
     * @return
     */
    @MapKey("base_id")
    @Select("select base_id,base_name from wvg_data_base")
    Map<Integer,Map<String,Object>> queryIdtBaseInt();

    /**
     * 板卡类型选择
     * @return
     */
    @MapKey("card_type_id")
    @Select("select card_type_id,card_type_name from wvg_card_type")
    Map<Integer,Map<String,Object>> queryCardType();

    /**
     * 采集时段
     * @return
     */
    @MapKey("cycle_id")
    @Select("select cycle_id,cycle_name from wvg_cycle")
    Map<Integer,Map<String,Object>> queryCycle();

    /**
     * 型号查询
     * @return
     */
    @MapKey("res_model_id")
    @Select("select res_model_id, res_model_name from wvg_res_model")
    Map<Integer,Map<String,Object>> queryResModel();
}
