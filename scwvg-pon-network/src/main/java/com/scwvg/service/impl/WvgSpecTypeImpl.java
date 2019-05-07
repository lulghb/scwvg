package com.scwvg.service.impl;

import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import com.scwvg.mappers.WvgSpecTypeMapper;
import com.scwvg.service.WvgSpecTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/7
 * @Explain 专业实现类
 **/
@Service
public class WvgSpecTypeImpl implements WvgSpecTypeService {
    @Autowired
    WvgSpecTypeMapper wvgSpecType;
    @Override
    public String queryAllSpec(int spec_id) {
        return wvgSpecType.queryAllSpec(spec_id);
    }


}
