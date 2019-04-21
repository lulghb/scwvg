package com.scwvg.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scwvg.system.entitys.WvgUser;
import com.scwvg.system.repository.WvgUserRepository;
import com.scwvg.system.service.WvgUserService;
import com.scwvg.system.vo.UserVO;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.scwvg.commons.scwvgpage.PageCond;
import org.scwvg.commons.scwvgpage.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

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

    /**
     * @param pageRequest
     * @param criteria
     * @Description: 命名sql，分页条件查询
     * @Author: chen.baihoo
     * @Date: 2019/4/21
     */
    @Override
    public PageCond<UserVO> queryWvgUserByNamedSqlWithPage(PageRequest pageRequest, Map<String, String> criteria) {

        Long total = sqlSessionTemplate.selectOne("com.scwvg.system.mapper.WvgUserMapper.queryWvgUserWithCount",criteria);
        PageCond<UserVO> pageCond = new PageCond<UserVO>(
                sqlSessionTemplate.selectList(
                        "com.scwvg.system.mapper.WvgUserMapper.queryWvgUser"
                        ,criteria,new RowBounds(pageRequest.getBegin(),pageRequest.getSize())),		// 3.1 设置分页查询条件，数据库检索的数据集
                total.intValue(),                   // 3.2 设置分页查询条件，返回记录数长度
                pageRequest.getCurrentPage(), 	    // 3.3 设置分页状态信息，当前页
                pageRequest.getSize(),		        // 3.4 设置分页状态信息，当前页记录数
                pageRequest.getBegin()              // 3.5 设置数据检索的起始下标
        );
        return pageCond;
    }
}
