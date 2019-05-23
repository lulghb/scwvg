package com.scwvg.mappers;

import com.github.pagehelper.Page;
import com.scwvg.entitys.AlarmCounts;
import com.scwvg.entitys.BandUserCounts;
import com.scwvg.entitys.FluxCounts;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import com.scwvg.entitys.scwvgponnetwork.WvgVendor;
import org.apache.ibatis.annotations.*;

import java.util.List;
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

    /*支撑厂家查询*/
    Page<WvgVendor> queryVendorAllByPage(Map<String,Object> params);

    /*查询最大的ID*/
    @Select("SELECT MAX(res_vendor_id) from wvg_res_vendor")
    int countVendorId();
    /*支撑厂家新增*/
    int saveVendor(WvgVendor vendor);

    /*厂家修改*/
    @Update("UPDATE wvg_res_vendor \n" +
            "set\n" +
            "\tres_parent_id=#{res_parent_id},\n" +
            "\tres_vendor_name=#{res_vendor_name},\n" +
            "\tres_vendor_admin_name=#{res_vendor_admin_name},\n" +
            "\tres_position=#{res_position},\n" +
            "\tres_vendor_phone=#{res_vendor_phone},\n" +
            "\tres_position_content=#{res_position_content},\n" +
            "\tupdateTime=NOW(),\n" +
            " wvg_user_id=#{wvg_user_id}\n" +
            "where res_vendor_id=#{res_vendor_id}")
    int editVendor(WvgVendor vendor);

    /*查询有多少个设备与厂家进行了关联*/
    @Select("select count(1) from wvg_res_data where res_vendor_id=#{res_vendor_id}")
    int countResData(Long res_vendor_id);

    /*删除厂厂家信息*/
    @Delete("delete from wvg_res_vendor where res_vendor_id=#{res_vendor_id}")
    int deleteVendor(Long res_vendor_id);

    /*首页厂家支撑信息查询*/
    @Select("select  res_vendor_name,res_vendor_admin_name,res_vendor_phone from wvg_res_vendor")
    List<WvgVendor> queryMainVendors();

    /*首页【今日用户增减情况】*/
    @Select("select * from wvg_banduser_counts")
    Page<BandUserCounts> queryBandUsers();
    /*首页【今日告警情况】*/
    @Select("select * from wvg_alarm_counts")
    Page<AlarmCounts> queryAlarms();
    /*首页【今日流量拥塞量】*/
    @Select("select * from wvg_flux_counts")
    Page<FluxCounts> queryFluxs();
}
