package com.scwvg.zgy.system.service.impl;

import com.scwvg.zgy.commons.redis.Redis;
import com.scwvg.zgy.commons.thymeleaf.dataset.TAttachment;
import com.scwvg.zgy.commons.thymeleaf.dataset.service.TAttachmentService;
import com.scwvg.zgy.system.entitys.WvgMenu;
import com.scwvg.zgy.system.entitys.WvgRole;
import com.scwvg.zgy.system.entitys.WvgUser;
import com.scwvg.zgy.system.service.WvgMenuService;
import com.scwvg.zgy.system.service.WvgUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Description: TODO
 * @author: chen.baihoo
 * @date: 2019/4/20 23:25
 * version 0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WvgMenuServiceImplTest {

    @Resource
    WvgMenuService wvgMenuService;
    @Resource
    WvgUserService wvgUserService;
    @Test
    public void findWvgMenuByUser() {
        WvgUser user = wvgUserService.findByWvgLoginName("admin");
        Set<WvgRole> roleList = user.getRoleList();
        log.info("roleList = {} " , roleList);
        List<WvgMenu> wvgMenus = wvgMenuService.findWvgMenuByUser(user);
        log.info("wvgMenus = {} " , wvgMenus);
    }

    @Test
    public void findWvgMenuIsForebear() {
        WvgMenu menu = new WvgMenu();
        menu.setWvgMenuId(4);
        menu.setWvgSeq(".0.1.2.");
        List<WvgMenu> wvgMenus = wvgMenuService.findWvgMenuIsForebear(menu);
        log.info("wvgMenus = {} " , wvgMenus);
    }

    @Test
    public void findWvgMenuIsProgeny() {
        WvgMenu menu = new WvgMenu();
        menu.setWvgMenuId(1);
        List<WvgMenu> wvgMenus = wvgMenuService.findWvgMenuIsProgeny(menu);
        log.info("wvgMenus = {} " , wvgMenus);
    }

}