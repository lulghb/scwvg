package com.scwvg.service;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain：Select控件公共查询类
 **/
public interface WvgSelectService {
    //查询用户身份类型
    public  Map<Integer,Map<String,Object>> queryUserIdType();
    //查询属地
    public  Map<String,Map<String,Object>> querCitys();
    //查询专业
    public  Map<Integer,Map<String,Object>> querySpecs();
    //查询角色
    public Map<Integer,Map<String,Object>> querRoles();
}
