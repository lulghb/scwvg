package com.scwvg.mappers;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：用户类
 **/
@Mapper
public interface WvgUserMapper {
    @Select("select count(1) from wvg_user where (wvg_login_name = #{wvg_login_name} " +
            "OR wvg_id_number = #{wvg_user_iphone} " +
            "OR wvg_user_iphone = #{wvg_id_number})")
    public int querUserNameAndIhoneAndIdnumber(@Param("wvg_login_name")  String wvg_login_name,
                                                   @Param("wvg_user_iphone")  String wvg_user_iphone,
                                                   @Param("wvg_id_number")  String wvg_id_number);

    /*修改用户在线信息*/
    @Update("update wvg_user set wvg_login_time=SYSDATE()," +
            "wvg_login_ip=#{wvg_login_ip}," +
            "wvg_online_state=#{wvg_online_state} " +
            "where wvg_user_id=#{wvg_user_id}")
    public int upDateUsLoginTimeAndIp(WvgLoginUser loginUser);

    /*根据ID查询用户信息*/
    @Select("select * from wvg_user where wvg_user_id=#{wvg_user_id}")
    public WvgUser getById(@Param("wvg_user_id") int wvg_user_id);

    /*根据用户名查询用户*/
    @Select("SELECT\n" +
            "                a.wvg_user_id,\n" +
            "                a.wvg_login_name,\n" +
            "                a.wvg_real_name,\n" +
            "                a.wvg_id_type,\n" +
            "                a.wvg_city_id,\n" +
            "                a.wvg_spec_id,\n" +
            "                a.wvg_id_number,\n" +
            "                a.wvg_user_iphone,\n" +
            "                a.wvg_account_enabled,\n" +
            "                a.wvg_account_data,\n" +
            "                a.wvg_user_password,\n" +
            "                a.wvg_password_data,\n" +
            "                a.wvg_add_time,\n" +
            "                a.wvg_update_time,\n" +
            "                a.wvg_online_state,\n" +
            "                a.wvg_login_time,\n" +
            "                a.wvg_login_ip,\n" +
            "                date_add(now(),interval -a.wvg_account_data month)<a.wvg_add_time as wvg_act_date,\n" +
            "                date_add(now(),interval -a.wvg_password_data month)<a.wvg_add_time as wvg_pwd_date,\n" +
            "                a.wvg_account_remarks\n" +
            "                FROM\n" +
            "                wvg_user a\n" +
            "                WHERE\n" +
            "                a.wvg_login_name =#{wvg_user_name}")
    public WvgUser querUserInfo(@Param("wvg_user_name") String wvg_user_name);

    /*根据ID修改密码*/
    @Update("update wvg_user x set x.wvg_user_password=#{wvg_user_password} " +
            "where x.wvg_user_id=#{wvg_user_id}")
    public int changePassword(@Param("wvg_user_id") Long id,
                              @Param("wvg_user_password") String wvg_user_password);

    /*查询统计用户数*/
    public Integer countUserAll(@Param("params") Map<String, Object> params);



    /*新增用户*/
    @Options(useGeneratedKeys = true, keyColumn = "wvg_user_id")
    @Insert("insert into wvg_user(wvg_user_id,\n" +
            "wvg_login_name,\n" +
            "wvg_real_name,\n" +
            "wvg_id_type,\n" +
            "wvg_city_id,\n" +
            "wvg_spec_id,\n" +
            "wvg_id_number,\n" +
            "wvg_user_iphone,\n" +
            "wvg_account_data,\n" +
            "wvg_account_enabled,\n" +
            "wvg_user_password,\n" +
            "wvg_password_data,\n" +
            "wvg_add_time,\n" +
            "wvg_update_time,\n" +
            "wvg_login_time,\n" +
            "wvg_login_ip,\n" +
            "wvg_account_remarks) values" +
            "(#{wvg_user_id},   \n" +
            "#{wvg_login_name},\n" +
            "#{wvg_real_name},\n" +
            "#{wvg_id_type},\n" +
            "#{wvg_city_id},\n" +
            "#{wvg_spec_id},\n" +
            "#{wvg_id_number},\n" +
            "#{wvg_user_iphone},\n" +
            "#{wvg_account_data},\n" +
            "#{wvg_account_enabled},\n" +
            "#{wvg_user_password},\n" +
            "#{wvg_password_data},\n" +
            "now(),\n" +
            "now(),\n" +
            "#{wvg_login_time},\n" +
            "#{wvg_login_ip},\n" +
            "#{wvg_account_remarks})")
    public int saveUserInfo(WvgUser wvgUser);

    /*通过用户ID删除用户*/
    @Delete("delete from wvg_user where wvg_user_id = #{wvg_user_id}")
    public int deleteUserByID(Long wvg_user_id);

    /*新增一个角色,系统目前只支持一个用户属于一个角色*/
    @Update("INSERT into wvg_role_user(wvg_role_id,wvg_user_id) values(#{wvg_role_id},#{wvg_user_id}) ")
    public int saveUserRoles(@Param("wvg_role_id") Long wvg_role_id, @Param("wvg_user_id") Long wvg_user_id);
    /*修改用户信息*/
    public int updateUserInfo(WvgUser user);
    /*修改用户对应的角色信息*/
    @Update("update wvg_role_user set wvg_role_id=#{id}  where wvg_user_id=#{wvg_user_id}")
    int editRoleUserIds(WvgUser user);
    /*查询所有用户信息*/
    public Page<WvgUser> queryAllUserByPage(Map<String, Object> params);

    /*token刷新或者过期或者用户未登录先进行删除登录IP，登录时间，登录状态*/
    @Update("update wvg_user set wvg_login_time=#{wvg_login_time}," +
            "wvg_login_ip=#{wvg_login_ip},wvg_online_state=#{wvg_online_state}")
    int updateLgIpAndlgDateAndlgonlineState(WvgLoginUser user);
    /*通过属地ID查找属地名*/
    @Select("select city_name from wvg_city where city_id=#{city_id}")
    public String queryCityByCityId(int city_id);
    /*剔除用户下线*/
    @Delete("delete from  wvg_token where wvg_user_id=#{wvg_user_id}")
    public int userOffline(Long wvg_user_id);
    /*修改用户在线状态*/
    @Update("UPDATE wvg_user\n" +
            "SET wvg_login_time = Now(),\n" +
            " wvg_online_state = #{wvg_online_state},\n" +
            " wvg_login_ip = ''\n" +
            "WHERE\n" +
            "\twvg_user_id = #{wvg_user_id}")
    public int updateUserOffline(@Param("wvg_user_id") Long wvg_user_id,@Param("wvg_online_state") int wvg_online_state);

    /*查询用户所属证件类型*/
    @Select("select user_type_name from wvg_user_type where user_type_id=#{idType}")
    public String queryIdName(int idType);
    /*查询用户所属角色名*/
    @Select("select x.wvg_role_description from wvg_role x LEFT JOIN wvg_role_user y on x.wvg_role_id = y.wvg_role_id where 1=1 and y.wvg_user_id=#{user_id}")
    public String queryRoleName(Long user_id);

    /*删除角色用户中间表*/
    @Delete("delete from wvg_role_user where wvg_user_id=#{wvg_user_id}")
    public int deleteRoleUserId(Long wvg_user_id);


    /*查询最大的ID*/
    @Select("SELECT MAX(wvg_user_id) from wvg_user")
   public int queryMaxUserID();



}
