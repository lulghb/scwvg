package com.scwvg.mappers;

import com.scwvg.entitys.scwvgponnetwork.WvgRole;
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
    /*新增角色（此处有两个问题，1，ID需要查询数据库再++,wvg_user_id需要查询当前操作人的用户ID）*/
    @Insert("insert into wvg_role(wvg_role_id,wvg_role_name,wvg_role_description,wvg_add_time," +
            "wvg_update_time,wvg_user_id) values (#{wvg_role_id},#{wvg_role_name},#{wvg_role_description})" +
            "#{wvg_add_time},null,#{wvg_user_id}")
    public int save(WvgRole role);

    /*统计所有角色*/
    public int countRole(@Param("params") Map<String, Object> params);

    /*查询角色列表*/
    public List<WvgRole> queryRolelist(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                                       @Param("limit") Integer limit);

    /*通过ID查找角色*/
    @Select("select * from wvg_role where wvg_role_id=#{wvg_role_id}")
    public WvgRole queryRoleById(Long wvg_role_id);

    /*通过role_name查找角色*/
    @Select("select * from wvg_role where wvg_role_name=#{wvg_role_name}")
    public WvgRole queryRoleByName(String wvg_role_name);

    /*修改角色*/
    @Update("update wvg_role t set wvg_role_name=#{wvg_role_name}," +
            "set wvg_role_description=#{wvg_role_description}," +
            "set wvg_add_time=#{wvg_add_time},set wvg_update_time " +
            "where wvg_user_id=#{wvg_user_id}")
    public int update(WvgRole role);

    /*通过用户ID查找角色列表*/
    @Select("SELECT\n" +
            "\tx.*\n" +
            "FROM\n" +
            "\twvg_role x\n" +
            "inner join wvg_role_user y\n" +
            "on x.wvg_role_id = y.wvg_role_id\n" +
            "INNER JOIN wvg_user u\n" +
            "on y.wvg_user_id=u.wvg_user_id\n" +
            "where u.wvg_user_id=#{wvg_uer_id}")
    public List<WvgRole> queryWvgUserById(Long wvg_uer_id);

    @Delete("delete from wvg_role_menu where wvg_role_id=#{id}")
    public int deleteWvgRoleWvgMenu(Long id);
    @Delete("delete from wvg_role_user where wvg_role_id=#{id}")
    public int deleteWvgRoleUser(Long id);
    @Delete("delete from wvg_role where wvg_role_id=#{id}")
    public int  deleteWvgRole(Long id);


    /*存储数据*/
    public int saveRoleMenu(@Param("wvg_role_id") Long wvg_role_id, @Param("wvg_menu_ids") List<Long> wvg_menu_ids);


}
