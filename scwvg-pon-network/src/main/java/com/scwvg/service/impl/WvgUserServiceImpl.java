package com.scwvg.service.impl;

import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleUser;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgUserMapper;
import com.scwvg.service.WvgUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：用户信息操作实现类
 **/
@Service
public class WvgUserServiceImpl implements WvgUserService {
    private static final Logger log = LoggerFactory.getLogger("WvgUserServiceImpl");
    @Autowired
    private WvgUserMapper userMapper; //用户操作mapper注入进来

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //密码动态加密

    /**
     * 新增用户
     *
     * @param roleUsers
     * @return
     */
    @Override
    @Transactional
    public WvgUser saveUser(WvgRoleUser roleUsers) {
        WvgUser user = roleUsers;
        user.setWvg_user_password(passwordEncoder.encode(user.getWvg_user_password()));
        user.setWvg_account_enabled(0);  //账号状态，默认为0 不启用（普通地市用户需要管理员，其他用户启用需要系统管理员）
        userMapper.saveUserInfo(user);   //用户新增
        saveRoleUserIds(user.getWvg_user_id(), roleUsers.getRoleIds());  //角色新增
        log.debug("用户新增！" + user.getWvg_real_name());
        return user;
    }

    //新增用户与角色中间表
    private void saveRoleUserIds(Long wvg_user_id, List<Long> roleIds) {
        if (roleIds != null) {
            userMapper.deleteUserByID(wvg_user_id);
            if (!CollectionUtils.isEmpty(roleIds)) {
                userMapper.saveUserRoles(wvg_user_id, roleIds);
            }
        }
    }

    /*修改用户*/
    @Override
    public WvgUser updateUser(WvgRoleUser roleUser) {
        userMapper.updateUserInfo(roleUser);
        saveRoleUserIds(roleUser.getWvg_user_id(), roleUser.getRoleIds());
        return roleUser;
    }
    /*根据用户名查询用户*/
    @Override
    public WvgUser querUserInfo(String username) {
        return userMapper.querUserInfo(username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        WvgLoginUser user = (WvgLoginUser) userMapper.querUserInfo(username);
        if(user ==null){
            throw new IllegalArgumentException("用户不存在!请咨询系统供应商！");
        }
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new IllegalArgumentException("旧密码错误!请咨询系统供应商！");
        }
        userMapper.changePassword(user.getWvg_user_id(),passwordEncoder.encode(newPassword));
        log.debug("修改"+user.getUsername()+"的密码！");
    }
}