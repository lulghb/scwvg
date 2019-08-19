package com.scwvg.mappers;

import com.github.pagehelper.Page;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdBase;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;
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


    /**
     * 查询所有指令
     * @return
     */
    @Select("select DISTINCT cmd_id,cmd_ch_name from wvg_cmd_base where res_vendor_id=#{vendor_id} ORDER BY cmd_id desc")
    List<WvgCmdBase> queryCmdBase(int vendor_id);

    /**
     * 查询最大ID
     * @return
     */
    @Select("select MAX(idt_cmd_id) from wvg_cmd_induction")
    int queryMaxInduction();
    /**
     * 插入指令组
     * @param params
     * @return
     */
    @Insert("INSERT INTO wvg_cmd_induction (\n" +
            "\tidt_cmd_id,\n" +
            "\tidt_cmd_name,\n" +
            "\tdata_type_id,\n" +
            "\tspec_id,\n" +
            "\tres_type_id,\n" +
            "\tidt_retry_number,\n" +
            "\tcycle_id,\n" +
            "\tcycle_select,\n" +
            "\tidt_gather_time,\n" +
            "\tidt_is_test,\n" +
            "\tidt_is_addlib,\n" +
            "\tbase_id,\n" +
            "\tcreateTime,\n" +
            "\twvg_user_id\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{idt_cmd_id},\n" +
            "\t\t#{idt_cmd_name},\n" +
            "\t\t#{data_type_id},\n" +
            "\t\t#{spec_id},\n" +
            "\t\t#{res_type_id},\n" +
            "\t\t#{idt_retry_number},\n" +
            "\t\t#{cycle_id},\n" +
            "\t\t#{cycle_select},\n" +
            "\t\t#{idt_gather_time},\n" +
            "\t\t#{idt_is_test},\n" +
            "\t\t#{idt_is_addlib},\n" +
            "\t\t#{base_id},\n" +
            "\t\tnow(),\n" +
            "\t\t#{wvg_user_id})")
    int saveInduction(Map<String,Object> params);
    /**
     * 插入组管理
     * @return
     */
    @Insert("INSERT INTO wvg_cmd_group \n" +
            "(idt_cmd_id,\n" +
            "vendor_id,\n" +
            "cmd_id_group,\n" +
            "cmd_card_type,\n" +
            "res_model_id)\n" +
            "VALUES\n" +
            "(#{idt_cmd_id},\n" +
            "#{vendor_id},\n" +
            "#{group},\n" +
            "#{cmd_card_type},\n" +
            "#{res_model_id})")
    int saveGroup(Map<String,Object> params);

    /**
     * 修改宏指令
     * @param params
     * @return
     */
    @Update("UPDATE wvg_cmd_induction\n" +
            "SET \n" +
            "idt_cmd_name = #{idt_cmd_name},\n" +
            "data_type_id=#{data_type_id},\n" +
            "spec_id=#{spec_id},\n" +
            "res_type_id=#{res_type_id},\n" +
            "cycle_id=#{cycle_id},\n" +
            "cycle_select=#{cycle_select},\n" +
            "idt_gather_time=#{idt_gather_time},\n" +
            "idt_is_addlib=#{idt_is_addlib},\n" +
            "base_id=#{base_id},\n" +
            "updateTime=now(),\n" +
            "idt_retry_number=#{idt_retry_number},\n" +
            "wvg_user_id=#{wvg_user_id} \n"+
            "where idt_cmd_id=#{idt_cmd_id}")
    int editInduction(Map<String,Object> params);
    /**
     * 修改指令组
     * @param params
     * @return
     */
    @Insert("INSERT INTO wvg_cmd_group \n" +
            "(idt_cmd_id,\n" +
            "vendor_id,\n" +
            "cmd_id_group,\n" +
            "cmd_card_type,\n" +
            "res_model_id)\n" +
            "VALUES\n" +
            "(#{idt_cmd_id},\n" +
            "#{vendor_id},\n" +
            "#{group},\n" +
            "#{cmd_card_type},\n" +
            "#{res_model_id})")
    int editCmdGroup(Map<String,Object> params);

    /**
     * 查询宏指令明细
     * @param idt_cmd_id
     * @return
     */
    @Select("select \n" +
            "x.idt_cmd_id,\n" +
            "x.idt_cmd_name,\n" +
            "x.data_type_id,\n" +
            "x.spec_id,\n" +
            "x.res_type_id,\n" +
            "x.idt_retry_number,\n" +
            "x.cycle_id,\n" +
            "x.cycle_select,\n" +
            "x.idt_gather_time,\n" +
            "x.idt_is_addlib,\n" +
            "x.base_id\n" +
            "from wvg_cmd_induction x\n" +
            "where x.idt_cmd_id=#{idt_cmd_id}")
    Map<String,Object> getIdtCmdBaseById(Long idt_cmd_id);

    /**
     * 查询右侧数据第一步
     * @param
     * @return
     */
    @Select("select * from wvg_cmd_group where idt_cmd_id=#{idt_cmd_id}")
   List<WvgCmdGroup> queryCmdGroupByid(Long idt_cmd_id);

    /**
     * 查询右侧数据第二步
     * @param
     * @return
     */
    @Select("select DISTINCT cmd_id,cmd_ch_name from wvg_cmd_base where cmd_id=#{cmd_id}")
    WvgCmdBase queryCmdBaseById(int cmd_id);

    /**
     * 删除组数据
     * @param idt_cmd_id
     * @return
     */
    @Delete("delete from wvg_cmd_group where idt_cmd_id=#{idt_cmd_id}")
    int deleteGroup(Long idt_cmd_id);

    /**
     * 删除宏指令管理
     * @param idt_cmd_id
     * @return
     */
    @Delete("delete from wvg_cmd_induction where idt_cmd_id=#{idt_cmd_id}")
    int deleteIdt(Long idt_cmd_id);
}
