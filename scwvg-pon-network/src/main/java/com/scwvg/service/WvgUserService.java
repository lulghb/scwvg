package com.scwvg.service;

import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgMenuMapper;
import com.scwvg.mappers.WvgUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：用户当前用户所属<角色,菜单>
 **/
@Service
public class WvgUserService implements UserDetailsService {
    /*用户角色*/
    @Autowired
    WvgUserMapper userMapper;
    /*用户权限*/
    @Autowired
    WvgMenuMapper menuMapper;

    Msg msg=new Msg();
    boolean res=true;
    private static String company="www.scwvg.com";


    @Override
    public UserDetails loadUserByUsername(String wvg_login_name) throws UsernameNotFoundException {
        /*此处将用户对于的角色，菜单（权限）查询赋值给用户类进行权限管控*/
        WvgUser user =userMapper.querUserAndRoleAndMenu(wvg_login_name);
        if(user !=null || user.equals("")){
            List<WvgMenu> wvgMenus =menuMapper.findByUserIDMenu(user.getWvg_user_id());
            List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            for(WvgMenu menu:wvgMenus){
                if(menu !=null && menu.getWvg_menu_name() !=null){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(menu.getWvg_menu_name());
                    auths.add(grantedAuthority);
                }
            }
            //开始判断账号状态
            if(user.getWvg_act_date()>user.getWvg_account_data())
            {
                msg.setTitle("账号已过期！");
                msg.setContent("账号已过期，请联系统供应商处理！");
                msg.setEtraInfo("系统供应商："+company);
                throw new UsernameNotFoundException("您的账号已过有效期！请联系统供应商处理！");
            }
            else if(user.getWvg_account_enabled() !=1){
                msg.setTitle("账号未启用！");
                msg.setContent("您的账号未启用！请联系统供应商处理！");
                msg.setEtraInfo("系统供应商："+company);
                throw new UsernameNotFoundException("您的账号未启用！请联系统供应商处理！");

            }
            else if(user.getWvg_account_type()!=0){
                msg.setTitle("账号锁定！");
                msg.setContent("您的账号已锁定！请联系统供应商处理！");
                msg.setEtraInfo("系统供应商："+company);
                throw new UsernameNotFoundException("您的账号未启用！请联系统供应商处理！");
            }
            else if(user.getWvg_pwd_date()>user.getWvg_account_data()){
                msg.setTitle("密码过期！");
                msg.setContent("您的密码已过期！请联系管理员处理！");
                msg.setEtraInfo("系统供应商："+company);
                throw new UsernameNotFoundException("您的账号未启用！请联系统供应商处理！");
            }
            else {
                return new User(user.getWvg_login_name(),user.getWvg_user_password(),auths);
            }
        }else {
            throw new UsernameNotFoundException("用户不存在!");
        }
    }
}
