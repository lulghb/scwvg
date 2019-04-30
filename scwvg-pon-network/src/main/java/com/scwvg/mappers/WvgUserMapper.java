package com.scwvg.mappers;

import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：查询用户信息<包含的角色，菜单>
 **/
@Mapper
public interface WvgUserMapper {
    /*登录查询用户名和密码*/
    public WvgUser queryLoginNameAndPasswrd(String wvg_login_name, String wvg_user_password);

    /*查询当前用户所属的角色，权限以及菜单*/
    public WvgUser querUserAndRoleAndMenu(String wvg_login_name);
}
