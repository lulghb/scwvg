package com.scwvg.zgy.commons.thymeleaf.dataset.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.scwvg.zgy.commons.thymeleaf.dataset.TDictionary;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description: TODO
 * @author: chen.baihoo
 * @date: 2019/1/20 16:47
 * version 0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TDictionaryServiceTest {
    @Resource
    TDictionaryService tDictionaryService;

    @Test
    public void insert(){
        TDictionary dictionary = new TDictionary();
        dictionary.setId(1000L);
        dictionary.setDictId("1000");
        dictionary.setDictTypeName("100");
        dictionary.setDictTypeCode("100");
        tDictionaryService.insert(dictionary);
    }

    @Test
    public void getDict() {
        HashMap<String, Object> para = new HashMap<String, Object>();
        para.put("dictTypeCode","SUBARRAYTYPE");
        para.put("excludeId", "01,02");
        para.put("dictId", "");
        para.put("pdictId", "");
        para.put("pdictTypeCode","");
        TDictionary[] dictionaries = tDictionaryService.findTDictionary(para);
        Arrays.asList(dictionaries).forEach(e -> System.out.print(e));
    }

    @Test
    public void getDictJson() {
        HashMap<String, Object> para = new HashMap<String, Object>();
        para.put("dictId", "1000");
        String dictJson = tDictionaryService.getDictJson(para);
        System.out.print(dictJson);
    }
}