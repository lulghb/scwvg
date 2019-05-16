package com.scwvg.mappers;

import com.github.pagehelper.Page;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色数据操作
 **/
@Mapper
public interface WvgRoleMapper {
    /*新增角色*/
    @Insert("insert into wvg_role(wvg_role_id,wvg_role_name,wvg_role_description,wvg_add_time," +
            "wvg_user_id) values (#{wvg_role_id},#{wvg_role_name},#{wvg_role_description}," +
            "now(),#{wvg_user_id})")
    public int save(WvgRole role);

    /*统计所有角色*/
    public int countRole(@Param("params") Map<String, Object> params);

    /*通过ID查找角色*/
    @Select("select * from wvg_role where wvg_role_id=#{wvg_role_id}")
    public WvgRole queryRoleById(Long wvg_role_id);

    /*通过role_name查找角色*/
    @Select("select * from wvg_role where wvg_role_name=#{wvg_role_name}")
    public WvgRole queryRoleByName(String wvg_role_name);



    /*存储数据*/
    public int saveRoleMenu(@Param("wvg_role_id") Long wvg_role_id, @Param("wvg_menu_ids") List<Long> wvg_menu_ids);

    /*查询角色列表*/
    public Page<WvgRole> queryAllRoleByPage(Map<String,Object> params);
    /*查询用户姓名*/
    @Select("select wvg_real_name from wvg_user where wvg_user_id=#{wvg_user_id}")
    public String getUserName(long wvg_user_id);

    //查询权限内容
    @Select("select wvg_menu_id,wvg_menu_name,wvg_parent_id from wvg_menu")
    List<WvgMenu> queryTreeList();
    //查询最大ID
    @Select("SELECT MAX(wvg_role_id) from wvg_role")
    int queryMaxRoleId();
    //修改角色
    @Update("update wvg_role set wvg_role_name=#{wvg_role_name}," +
            "wvg_role_description=#{wvg_role_description}," +
            "wvg_update_time=now() " +
            "where wvg_role_id=#{wvg_role_id}")
    int editRole(WvgRole role);
    /*删除中间表*/
    @Delete("delete from wvg_role_menu where wvg_role_id=#{wvg_role_id}")
    int delRoleMenus(Long wvg_role_id);
    /*删除角色表*/
    @Delete("delete from wvg_role where wvg_role_id=#{wvg_role_id}")
    public int  deleteWvgRole(Long wvg_role_id);
    /*查询是否已经给角色赋值权限*/
    @Select("select count(1) from wvg_role_menu where wvg_role_id=#{wvg_role_id}")
    int countRoleMenus(Long wvg_role_id);
}
