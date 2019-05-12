package com.scwvg.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/10
 * @Explain：
 **/
@Mapper
public interface WvgConsoleMapper {
    @Select("select * from wvg_res_vendor")
    public List queryVendorAll();
}
