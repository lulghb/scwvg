package com.scwvg.system.service.impl;


import com.scwvg.system.service.WvgUserService;
import com.scwvg.system.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scwvg.commons.scwvgpage.PageCond;
import org.scwvg.commons.scwvgpage.PageRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
}