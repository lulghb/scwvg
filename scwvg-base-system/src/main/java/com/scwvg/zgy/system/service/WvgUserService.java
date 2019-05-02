package com.scwvg.zgy.system.service;
import com.scwvg.zgy.system.entitys.WvgUser;
import com.scwvg.zgy.system.vo.Layui;
import com.scwvg.zgy.system.vo.UserVO;
import com.scwvg.zgy.commons.page.PageCond;
import com.scwvg.zgy.commons.page.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

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

    /** 
     * @Description: 命名sql，分页条件查询
     * @Author: chen.baihoo
     * @Date: 2019/4/21 
     */
    PageCond<UserVO> queryWvgUserByNamedSqlWithPage(PageRequest pageRequest, Map<String, String> criteria);

    /**
     *
     * @param params
     * @return
     */
    Layui<String,Object> save(Map<String,Object> params);

    Layui<String , Object> delete(Long id);
}
