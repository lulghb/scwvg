package com.scwvg.zgy.system.service.impl;


import com.scwvg.zgy.commons.redis.Redis;
import com.scwvg.zgy.commons.thymeleaf.dataset.TAttachment;
import com.scwvg.zgy.commons.thymeleaf.dataset.service.TAttachmentService;
import com.scwvg.zgy.system.service.WvgUserService;
import com.scwvg.zgy.system.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.scwvg.zgy.commons.page.PageCond;
import com.scwvg.zgy.commons.page.PageRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: chen.baihoo
 * @date: 2019/4/21 12:28
 * version 0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WvgUserServiceImplTest {
    @Resource
    WvgUserService wvgUserService;

    @Test
    public void queryWvgUserByNamedSqlWithPage() {
        Map<String , String> criteria = new HashMap<String , String>();
        PageCond<UserVO> page = wvgUserService.queryWvgUserByNamedSqlWithPage(new PageRequest(1,1), criteria);
        log.info("page = {}" , page);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Resource
    TAttachmentService tAttachmentService;
    @Test
    public void insert() {
        TAttachment ta = new TAttachment();
        ta.setFileId(7153L);
        ta.setFileName("baigle");
        ta.setFileRealeName("baigle");
        ta.setFileSize("1024");
        ta.setFilePath("g:/baigle-util");
        ta.setSuffix(".log");
        ta.setCreateUser("baigle");
        tAttachmentService.insert(ta);
        log.info("ta = {}" , ta);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Resource
    Redis redis;
    @Test
    public void testJdkSerializer(){

        //redis.lpush("list1",1,2,3,4,5);
        List<Integer> list = redis.lrange("list1",0,-1);
        list.forEach(e-> System.out.println(e));
    }
}