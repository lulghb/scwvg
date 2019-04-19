package com.scwvg.system.service;
import com.scwvg.system.entitys.WvgUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 13:41
 * @Description: TODO 用户服务接口
 * version 0.1
 */
public interface WvgUserService extends UserDetailsService {
    /**
     * @Description: 登陆名和密码查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    WvgUser findByWvgLoginNameAndWvgUserPassword(String loginName, String password);
    /**
     * @Description: 登陆名查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    WvgUser findByWvgLoginName(String loginName);
}
