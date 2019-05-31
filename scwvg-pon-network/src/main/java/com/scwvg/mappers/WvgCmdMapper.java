package com.scwvg.mappers;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

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

    /**
     * 查询所有算法
     * @param params
     * @return
     */
    Page<Map<String,Object>> getCmdAlgoByPage(Map<String,Object> params);

    /**
     * 新增指令库
     * @param params
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into wvg_cmd_base(\n" +
            "cmd_ch_name,\n" +
            "cmd_en_name,\n" +
            "cmd_context,\n" +
            "cmd_index_algo,\n" +
            "cmd_card_type,\n" +
            "res_vendor_id,\n" +
            "spec_id,\n" +
            "cmd_is_algo,\n" +
            "cmd_res_algorithm,\n" +
            "cmd_res_unit,\n" +
            "cmd_protocol_id,\n" +
            "opt_type_id,\n" +
            "cmd_enable,\n" +
            "cmd_is_test,\n" +
            "createTime,\n" +
            "wvg_user_id\n" +
            ")values(\n" +
            "#{cmd_ch_name},\n" +
            "#{cmd_en_name},\n" +
            "#{cmd_context},\n" +
            "#{cmd_index_algo},\n" +
            "#{cmd_card_type},\n" +
            "#{res_vendor_id},\n" +
            "#{spec_id},\n" +
            "#{cmd_is_algo},\n" +
            "#{cmd_res_algorithm},\n" +
            "#{cmd_res_unit},\n" +
            "#{cmd_protocol_id},\n" +
            "#{opt_type_id},\n" +
            "#{cmd_enable},\n" +
            "#{cmd_is_test},\n" +
            "now(),\n" +
            "#{wvg_user_id})")
    int saveCmdBaes(Map<String,Object> params);

    /**
     * 根据ID查询指令明细
     * @param cmd_id
     * @return
     */
    @Select("SELECT\n" +
            "\tcmd_id,\n" +
            "\tcmd_ch_name,\n" +
            "\tcmd_en_name,\n" +
            "\tcmd_context,\n" +
            "\tcmd_index_algo,\n" +
            "\tcmd_card_type,\n" +
            "\tres_vendor_id,\n" +
            "\tspec_id,\n" +
            "\tcmd_is_algo,\n" +
            "\tcmd_res_algorithm,\n" +
            "\tcmd_res_unit,\n" +
            "\tcmd_protocol_id,\n" +
            "\topt_type_id,\n" +
            "\tcmd_enable,\n" +
            "\tcmd_is_test\n" +
            "FROM\n" +
            "\twvg_cmd_base\n" +
            "where cmd_id=#{cmd_id}")
    Map<String,Object> getCmdBaseByid(Long cmd_id);

    /**
     * 指令库修改
     * @param params
     * @return
     */
    @Update("update wvg_cmd_base\n" +
            "set \n" +
            "cmd_ch_name=#{cmd_ch_name},\n" +
            "cmd_en_name=#{cmd_en_name},\n" +
            "cmd_context=#{cmd_context},\n" +
            "cmd_index_algo=#{cmd_index_algo},\n" +
            "cmd_card_type=#{cmd_card_type},\n" +
            "res_vendor_id=#{res_vendor_id},\n" +
            "spec_id=#{spec_id},\n" +
            "cmd_is_algo=#{cmd_is_algo},\n" +
            "cmd_res_algorithm=#{cmd_res_algorithm},\n" +
            "cmd_res_unit=#{cmd_res_unit},\n" +
            "cmd_protocol_id=#{cmd_protocol_id},\n" +
            "opt_type_id=#{opt_type_id},\n" +
            "cmd_enable=#{cmd_enable},\n" +
            "updateTime=now(),\n" +
            "wvg_user_id=#{wvg_user_id}\n" +
            "where cmd_id=#{cmd_id}")
    int editCmdBaes(Map<String,Object> params);

    /**
     * 查询当前指令是否已经在指令组里存在
     * @param cmd_id
     * @return
     */
    int getCuntGroup(Long cmd_id);
    /**
     * 删除指令
     * @param cmd_id
     * @return
     */
    @Delete("delete from wvg_cmd_base where cmd_id=#{cmd_id}")
    int deleteCmbBase(Long cmd_id);


}
