package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.mappers.WvgCmdMapper;
import com.scwvg.service.WvgCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/28
 * @Explain： 实现类
 **/
@Service
public class WvgCmdServiceImpl implements WvgCmdService {
    @Autowired
    WvgCmdMapper cmdMapper;
    @Override
    public Page<Map<String, Object>> queryCmdAll(Map<String, Object> params, Page<Map<String, Object>> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return cmdMapper.queryCmdAllByPage(params);
    }

    @Override
    public Page<Map<String, Object>> getInduction(Map<String, Object> params, Page<Map<String, Object>> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return cmdMapper.getInductionByPage(params);
    }
}
