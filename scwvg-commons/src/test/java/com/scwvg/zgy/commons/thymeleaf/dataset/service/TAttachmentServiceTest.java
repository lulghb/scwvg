package com.scwvg.zgy.commons.thymeleaf.dataset.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.scwvg.zgy.commons.thymeleaf.dataset.TAttachment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO
 * @author: chen.baihoo
 * @date: 2019/1/20 19:42
 * version 0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TAttachmentServiceTest {
    @Resource
    TAttachmentService tAttachmentService;
    @Test
    public void insert() {
        TAttachment ta = new TAttachment();
        ta.setFileId(7046L);
        ta.setFileName("baigle");
        ta.setFileRealeName("baigle");
        ta.setFileSize("1024");
        ta.setFilePath("g:/baigle-util");
        ta.setSuffix(".log");
        ta.setCreateUser("baigle");
        tAttachmentService.insert(ta);
        log.info("ta = {}" , ta);
    }

    @Test
    public void queryByFileIds() {
        List<TAttachment> attachments = tAttachmentService.findByFileIdsIn(new String[]{"7041","7042"});
        log.info("attachments={}",attachments);
    }
}