package com.scwvg.service.impl;

import com.scwvg.mappers.WvgSelectMapper;
import com.scwvg.service.WvgSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain： 公共查询类实现
 **/
@Service
public class WvgSelectServiceImpl implements WvgSelectService {
    @Autowired
    WvgSelectMapper selectMapper;

    @Override
    public Map<Integer,Map<String,Object>> queryUserIdType() {
        return selectMapper.queryUserIdType();
    }

    @Override
    public Map<String, Map<String, Object>> querCitys() {
        return selectMapper.querCitys();
    }

    @Override
    public Map<Integer, Map<String, Object>> querySpecs() {
        return selectMapper.querySpecs();
    }

    @Override
    public Map<Integer, Map<String, Object>> querRoles() {
        return selectMapper.queryRoles();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryParentMenus() {
        return selectMapper.queryParentMenus();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryVendorS() {
        return selectMapper.queryVendorS();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryProtocol() {
        return selectMapper.queryProtocol();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryOptType() {
        return selectMapper.queryOptType();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryDataType() {
        return selectMapper.queryDataType();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryResType() {
        return selectMapper.queryResType();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryIdtBaseInt() {
        return selectMapper.queryIdtBaseInt();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryCardType() {
        return selectMapper.queryCardType();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryCycle() {
        return selectMapper.queryCycle();
    }

    @Override
    public Map<Integer, Map<String, Object>> queryResModel() {
        return selectMapper.queryResModel();
    }

}
