package com.scwvg.service.impl;

import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgMenuMapper;
import com.scwvg.mappers.WvgUserMapper;
import com.scwvg.service.WvgUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：spring Security登录获取用户信息
 **/
@Service
public class WvgUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    WvgUserService userService;
    @Autowired
    WvgMenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WvgUser user=userService.querUserInfo(username);
        if(user ==null){
            throw new AuthenticationCredentialsNotFoundException("用户名不存在!");
        }else if(user.getWvg_act_date() !=1)
        {
            throw new UsernameNotFoundException("您的账号已过有效期！请联系统供应商处理！");
        }
        else if(user.getWvg_pwd_date() !=1){
            throw new UsernameNotFoundException("您的密码已过期！请联系统供应商处理！");
        }
        else if(user.getWvg_account_enabled() !=1){
            throw new UsernameNotFoundException("您的账号未启用！请联系统供应商处理！");
        }
        WvgLoginUser loginUser= new WvgLoginUser();
        BeanUtils.copyProperties(user,loginUser);

        //通过用户ID查找到用户所属菜单
        List<WvgMenu> menuList = menuMapper.queryMenuByUserId(user.getWvg_user_id());
        loginUser.setMenuList(menuList);
        return loginUser;
    }
}
