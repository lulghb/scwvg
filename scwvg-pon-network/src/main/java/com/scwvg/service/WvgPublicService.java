package com.scwvg.service;

import com.github.pagehelper.Page;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/21
 * @Explain：公告查询类接口
 **/
public interface WvgPublicService {
    /**
     * 专业查询
     * @return
     */
    Page<WvgSpecType> querySpecAll(Map<String,Object> params,Page<WvgSpecType> page);

    /**
     * 专业新增
     * @param specType
     * @return
     */
    Msg saveSpec(WvgSpecType specType);

    /**
     * 专业修改
     * @param specType
     * @return
     */
    Msg editSpec(WvgSpecType specType);

    /**
     * 专业删除
     * @param spec_id
     * @return
     */
    Msg delSpec(Long spec_id);
}
