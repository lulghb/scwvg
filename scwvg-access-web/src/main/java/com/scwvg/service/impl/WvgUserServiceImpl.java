package com.scwvg.service.impl;

import com.scwvg.entitys.WvgUser;
import com.scwvg.repository.WvgUserRepository;
import com.scwvg.service.WvgUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 13:43
 * @Description: TODO 用户服务接口实现
 * version 0.1
 */
@Service("wvgUserService")
public class WvgUserServiceImpl implements WvgUserService {


    @Resource
    private WvgUserRepository wvgUserRepository;

    /** 
     * @Description: 登陆用户名查询加载出唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return wvgUserRepository.findByWvgLoginName(s);
    }

    /**
     * @param loginName
     * @param password
     * @Description: 登陆名和密码查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @Override
    public WvgUser findByWvgLoginNameAndWvgUserPassword(String loginName, String password) {
        return wvgUserRepository.findByWvgLoginNameAndWvgUserPassword(loginName,password);
    }

    /**
     * @param loginName
     * @Description: 登陆名查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @Override
    public WvgUser findByWvgLoginName(String loginName) {
        return wvgUserRepository.findByWvgLoginName(loginName);
    }
}
