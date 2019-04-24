package com.scwvg.system.service.systemBrace.impl;

import com.scwvg.system.entitys.SystemBraceEntity;
import com.scwvg.system.repository.WvgSystemBraceRepository;
import com.scwvg.system.service.systemBrace.WvgSystemBraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/23
 * @Explain：实现类
 **/
@Service
public class WvgSystemBraceImpl implements WvgSystemBraceService {
    @Autowired
    private WvgSystemBraceRepository systemBraceDao;
    @Override
    public List<SystemBraceEntity> queryBraceAll() {
        return systemBraceDao.findAll();
    }
}
