package com.scwvg.mappers;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/21
 * @Explain： 公共查询类mapper
 **/
@Mapper
public interface WvgPublicMapper {
    /*查询专业名称*/
    @Select("select spec_name from wvg_spec_type where spec_id=#{spec_id}")
    public String queryAllSpec(int spec_id);

    /*获取新增人*/
    @Select("select wvg_real_name from wvg_user where wvg_user_id=#{wvg_user_id} ")
    String getWvgUserName(Long wvg_user_id);

    /*专业查询*/
    public Page<WvgSpecType> querySpecAllByPage(Map<String, Object> params);

    /*最大ID*/
    @Select("SELECT MAX(spec_id) from wvg_spec_type")
    int countSpecId();

    /*新增专业*/
    @Insert("INSERT into wvg_spec_type (spec_id,spec_name,createTime,wvg_user_id) " +
            "values(#{spec_id},#{spec_name},now(),#{wvg_user_id})")
    int saveSpec(WvgSpecType specType);

    /*专业修改*/
    @Update("update wvg_spec_type set spec_name=#{spec_name},updateTime=now() " +
            "where spec_id=#{spec_id}")
    int editSpec(WvgSpecType specType);

    /**
     * 专业删除
     * @param spec_id
     * @return
     */
    @Delete("delete from wvg_spec_type where spec_id=#{spec_id}")
    int deleteSpec(Long spec_id);

    /*查询用户表里是否关联了该专业*/
    @Select("select count(1) from wvg_user where wvg_spec_id=#{spec_id}")
    int countUserSpec(Long spec_id);
}
