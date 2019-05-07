package com.scwvg.mappers;

import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/7
 * @Explain：专业管理
 **/
@Mapper
public interface WvgSpecTypeMapper {
    @Select("select spec_name from wvg_spec_type where spec_id=#{spec_id}")
    public String queryAllSpec(int spec_id);
}
