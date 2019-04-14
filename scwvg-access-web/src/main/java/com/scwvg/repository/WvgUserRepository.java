package com.scwvg.repository;

import com.scwvg.entitys.WvgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 13:45
 * @Description: TODO 用户数据层仓库类，继承 jpa 接口
 * version 0.1
 */
@Repository("wvgUserRepository")
public interface WvgUserRepository  extends JpaRepository<WvgUser,Long> {
    
    /** 
     * @Description: 登陆名和密码查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    WvgUser findByWvgLoginNameAndWvgUserPassword(String loginName , String password);
    /** 
     * @Description: 登陆名查询唯一用户
     * @Author: chen.baihoo
     * @Date: 2019/4/14 
     */ 
    WvgUser findByWvgLoginName(String loginName);
}
