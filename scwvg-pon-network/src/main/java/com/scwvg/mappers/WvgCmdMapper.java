package com.scwvg.mappers;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/28
 * @Explain：指令操作中心
 **/
@Mapper
public interface WvgCmdMapper {
    /**
     * 查询所有指令
     * @param params
     * @return
     */
    Page<Map<String,Object>> queryCmdAllByPage(Map<String,Object> params);

    /**
     * 查询所有归类数据
     * @param params
     * @return
     */
    Page<Map<String,Object>> getInductionByPage(Map<String,Object> params);
}
