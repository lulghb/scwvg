package com.scwvg.service;

import com.github.pagehelper.Page;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdBase;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/28
 * @Explain： 指令中心操作
 **/
public interface WvgCmdService {
    /**
     * 查询所有指令类型
     * @param params
     * @param page
     * @return
     */
    Page<Map<String,Object>> queryCmdAll(Map<String,Object> params, Page<Map<String,Object>> page);

    /**
     * 查询所有宏指令
     * @param params
     * @param page
     * @return
     */
    Page<Map<String,Object>> getInduction(Map<String,Object> params, Page<Map<String,Object>> page);

    /**
     * 查询所有宏算法
     * @param params
     * @param page
     * @return
     */
    Page<Map<String,Object>> getCmdAlgo(Map<String,Object> params, Page<Map<String,Object>> page);

    /**
     * 指令新增
     * @param params
     * @return
     */
    Msg saveCmdBaes(Map<String,Object> params);

    /**
     * 单个指令查询
     * @param cmd_id
     * @return
     */
    Map<String,Object> getCmdBaseByid(Long cmd_id);

    /**
     * 修改指令
     * @param params
     * @return
     */
    Msg editCmdBaes(Map<String,Object> params);

    /**
     * 指令删除
     * @param cmd_id
     * @return
     */
    Msg deleteCmbBase(Long cmd_id);

    List<WvgCmdBase> queryCmdBase(int vendor_id);

    List<WvgCmdBase> queryCmdBaseById(Long idt_cmd_id);

    Msg saveInduction(Map<String,Object> params);

    Map<String,Object> getIdtCmdBaseById(Long idt_cmd_id);

    Msg editInduction(Map<String,Object> params);

    Msg deleteIdtGroup(Long idt_cmd_id);
}
