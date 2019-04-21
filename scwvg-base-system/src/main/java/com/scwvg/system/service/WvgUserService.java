package com.scwvg.system.service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.scwvg.system.entitys.WvgUser;
import com.scwvg.system.vo.UserVO;
import org.apache.ibatis.session.RowBounds;
import org.scwvg.commons.scwvgpage.PageCond;
import org.scwvg.commons.scwvgpage.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
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
}
