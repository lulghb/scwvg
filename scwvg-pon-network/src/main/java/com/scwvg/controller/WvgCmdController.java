package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdBase;
import com.scwvg.service.WvgCmdService;
import com.scwvg.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：2019/5/28
 * @Explain：指令中心操作类
 **/
@Api("指令操作中心")
@RestController
@RequestMapping("/cmd")
public class WvgCmdController {
    @Autowired
    WvgCmdService cmdService;
    @GetMapping("/queryCmdAll")
    @ApiOperation("指令库管理")
    @Log("指令库查询")
    public @ResponseBody
    PageInfo<Map<String, Object>> queryCmdAll(HttpServletRequest request, Page<Map<String, Object>> page){
        Map<String, Object> params = new HashMap<>();
        params.put("cmd_type", request.getParameter("cmd_type"));
        params.put("res_vendor_id", request.getParameter("res_vendor_id"));
        params.put("spec_id", request.getParameter("spec_id"));
        params.put("cmd_protocol_id", request.getParameter("cmd_protocol_id"));
        params.put("opt_type_id", request.getParameter("opt_type_id"));
        params.put("cmd_is_algo", request.getParameter("cmd_is_algo"));
        params.put("cmd_ch_name", request.getParameter("cmd_ch_name"));
        params.put("cmd_en_name", request.getParameter("cmd_en_name"));

        Page<Map<String, Object>> queryCmdAll=cmdService.queryCmdAll(params,page);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(queryCmdAll.getPageNum(), queryCmdAll.getPageSize(), queryCmdAll.getTotal());
        pageInfo.setTotalPage(queryCmdAll.getPages());
        pageInfo.setItems(queryCmdAll.getResult());
        return pageInfo;
    }

    @PostMapping("/addCmdBase")
    @ApiOperation(value = "指令新增")
    @Log("指令新增")
    @PreAuthorize("hasAuthority('cmd:add')")
    public Msg addCmdBase(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        params.put("cmd_ch_name",request.getParameter("cmd_ch_name"));
        params.put("cmd_en_name",request.getParameter("cmd_en_name"));
        params.put("cmd_index_algo",request.getParameter("cmd_index_algo"));
        params.put("cmd_context",request.getParameter("cmd_context"));
        params.put("cmd_card_type",request.getParameter("cmd_card_type"));
        params.put("cmd_protocol_id",request.getParameter("cmd_protocol_id"));
        params.put("spec_id",request.getParameter("spec_id"));
        params.put("cmd_is_algo",request.getParameter("cmd_is_algo"));
        params.put("res_vendor_id",request.getParameter("res_vendor_id"));
        params.put("cmd_enable",request.getParameter("cmd_enable"));
        params.put("cmd_res_algorithm",request.getParameter("cmd_res_algorithm"));
        params.put("cmd_res_unit",request.getParameter("cmd_res_unit"));
        params.put("opt_type_id",request.getParameter("opt_type_id"));
        return cmdService.saveCmdBaes(params);
    }
    @Log("指令资源明细")
    @GetMapping("/getCmdInfo/{cmd_id}")
    public @ResponseBody Map<String, Object> get(@PathVariable("cmd_id") Long cmd_id) {
        return cmdService.getCmdBaseByid(cmd_id);
    }

    @PostMapping("/editCmdBase")
    @ApiOperation(value = "指令修改")
    @Log("指令修改")
    @PreAuthorize("hasAuthority('cmd:edit')")
    public Msg editCmdBase(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        params.put("cmd_id",request.getParameter("cmd_id"));
        params.put("cmd_ch_name",request.getParameter("cmd_ch_name"));
        params.put("cmd_en_name",request.getParameter("cmd_en_name"));
        params.put("cmd_index_algo",request.getParameter("cmd_index_algo"));
        params.put("cmd_context",request.getParameter("cmd_context"));
        params.put("cmd_card_type",request.getParameter("cmd_card_type_edit"));
        params.put("cmd_protocol_id",request.getParameter("cmd_protocol_id_edit"));
        params.put("spec_id",request.getParameter("spec_id_edit"));
        params.put("cmd_is_algo",request.getParameter("cmd_is_algo_edit"));
        params.put("res_vendor_id",request.getParameter("res_vendor_id_edit"));
        params.put("cmd_enable",request.getParameter("cmd_enable"));
        params.put("cmd_res_algorithm",request.getParameter("cmd_res_algorithm"));
        params.put("cmd_res_unit",request.getParameter("cmd_res_unit"));
        params.put("opt_type_id",request.getParameter("opt_type_id_edit"));
        return cmdService.editCmdBaes(params);
    }
    @GetMapping("/delCmdBase/{cmd_id}")
    @ApiOperation(value = "指令库删除")
    @Log("指令库删除")
    @PreAuthorize("hasAuthority('cmd:del')")
    public Msg delCmdBase(@PathVariable Long cmd_id){
        return cmdService.deleteCmbBase(cmd_id);
    }


    @GetMapping("/getInduction")
    @ApiOperation("宏指令")
    @Log("宏指令查询")
    public @ResponseBody
    PageInfo<Map<String, Object>> getInduction(HttpServletRequest request, Page<Map<String, Object>> page){
        Map<String, Object> params = new HashMap<>();
        params.put("data_type_id", request.getParameter("data_type_id"));
        params.put("res_type_id", request.getParameter("res_type_id"));
        params.put("idt_is_test", request.getParameter("idt_is_test"));
        params.put("idt_is_addlib", request.getParameter("idt_is_addlib"));
        params.put("idt_base_int", request.getParameter("idt_base_int"));
        params.put("idt_cmd_name", request.getParameter("idt_cmd_name"));
        params.put("cycle_id", request.getParameter("cycle_id"));

        Page<Map<String, Object>> induction=cmdService.getInduction(params,page);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(induction.getPageNum(), induction.getPageSize(), induction.getTotal());
        pageInfo.setTotalPage(induction.getPages());
        pageInfo.setItems(induction.getResult());
        return pageInfo;
    }

    /**
     * 左侧查询所有指令
     * @return
     */
    @GetMapping("/queryCmdBase/{vendor_id}")
    public @ResponseBody
    List<WvgCmdBase> queryCmdBase(@PathVariable("vendor_id") int vendor_id){
        return cmdService.queryCmdBase(vendor_id);
    }
    /**
     * 右侧查询所有指令
     * @return
     */
    @GetMapping("/queryCmdBaseById/{idt_cmd_id}")
    public @ResponseBody
    List<WvgCmdBase> queryCmdBaseById(@PathVariable("idt_cmd_id") Long idt_cmd_id){
        return cmdService.queryCmdBaseById(idt_cmd_id);
    }



    @PostMapping("/addInduction")
    @ApiOperation(value = "宏指令新增（指令组）")
    @Log("指令新增（指令组）")
    @PreAuthorize("hasAuthority('idt:add')")
    public Msg addInduction(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        //宏指令管理
        params.put("idt_cmd_name",request.getParameter("idt_cmd_name"));
        params.put("data_type_id",request.getParameter("data_type_id"));
        params.put("spec_id",request.getParameter("spec_id"));
        params.put("res_type_id",request.getParameter("res_type_id"));
        params.put("cycle_id",request.getParameter("cycle_id"));
        params.put("cycle_select",request.getParameter("cycle_select"));
        params.put("idt_gather_time",request.getParameter("idt_gather_time"));
        params.put("idt_is_addlib",request.getParameter("idt_is_addlib"));
        params.put("base_id",request.getParameter("base_id"));
        params.put("idt_retry_number",request.getParameter("idt_retry_number"));
        //组管理
        params.put("vendor_id",request.getParameter("vendor_id"));
        params.put("cmd_card_type",request.getParameter("cmd_card_type"));
        params.put("res_model_id",request.getParameter("res_model_id"));
        params.put("cmd_id_group",request.getParameter("cmd_id_group"));

        return cmdService.saveInduction(params);
    }



    @GetMapping("/getCmdAlgo")
    @ApiOperation("指令算法获取")
    @Log("查询指令算法")
    public @ResponseBody
    PageInfo<Map<String, Object>> getCmdAlgo(HttpServletRequest request, Page<Map<String, Object>> page){
        Map<String, Object> params = new HashMap<>();
        params.put("algo_name", request.getParameter("algo_name"));
        params.put("idt_cmd_name", request.getParameter("idt_cmd_name"));
        Page<Map<String, Object>> algo=cmdService.getCmdAlgo(params,page);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(algo.getPageNum(), algo.getPageSize(), algo.getTotal());
        pageInfo.setTotalPage(algo.getPages());
        pageInfo.setItems(algo.getResult());
        return pageInfo;
    }


    @Log("ID查询宏指令")
    @GetMapping("/getIdtInfo/{idt_cmd_id}")
    public @ResponseBody Map<String, Object> getIdtInof(@PathVariable("idt_cmd_id") Long idt_cmd_id) {
        return cmdService.getIdtCmdBaseById(idt_cmd_id);
    }

    @PostMapping("/editInduction")
    @ApiOperation(value = "宏指令修改（指令组）")
    @Log("指令修改（指令组）")
    @PreAuthorize("hasAuthority('idt:edit')")
    public Msg editInduction(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        //宏指令管理
        params.put("idt_cmd_name",request.getParameter("idt_cmd_name"));
        params.put("data_type_id",request.getParameter("data_type_id"));
        params.put("spec_id",request.getParameter("spec_id"));
        params.put("res_type_id",request.getParameter("res_type_id"));
        params.put("cycle_id",request.getParameter("cycle_id"));
        params.put("cycle_select",request.getParameter("cycle_select"));
        params.put("idt_gather_time",request.getParameter("idt_gather_time"));
        params.put("idt_is_addlib",request.getParameter("idt_is_addlib"));
        params.put("base_id",request.getParameter("base_id"));
        params.put("idt_retry_number",request.getParameter("idt_retry_number"));
        //组管理
        params.put("vendor_id",request.getParameter("vendor_id"));
        params.put("cmd_card_type",request.getParameter("cmd_card_type"));
        params.put("res_model_id",request.getParameter("res_model_id"));
        params.put("cmd_id_group",request.getParameter("cmd_id_group"));

        params.put("idt_cmd_id",request.getParameter("idt_cmd_id"));

        return cmdService.editInduction(params);
    }
    @GetMapping("/delIdtCmd/{idt_cmd_id}")
    @ApiOperation(value = "指令组删除")
    @Log("指令库删除")
    @PreAuthorize("hasAuthority('idt:del')")
    public Msg delIdtCmd(@PathVariable Long idt_cmd_id){
        return cmdService.deleteIdtGroup(idt_cmd_id);
    }


}
