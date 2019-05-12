package com.scwvg.service.impl;

import com.scwvg.mappers.WvgConsoleMapper;
import com.scwvg.service.WvgConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/10
 * @Explain：
 **/
@Service
public class WvgConsoleImpl implements WvgConsoleService {
    @Autowired
    WvgConsoleMapper consoleMapper;
    @Override
    public List queryVendorBraceAll() {
        return consoleMapper.queryVendorAll();
    }
}
