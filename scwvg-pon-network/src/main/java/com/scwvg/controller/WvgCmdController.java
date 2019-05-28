package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.service.WvgCmdService;
import com.scwvg.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/28
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

        Page<Map<String, Object>> induction=cmdService.getInduction(params,page);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(induction.getPageNum(), induction.getPageSize(), induction.getTotal());
        pageInfo.setTotalPage(induction.getPages());
        pageInfo.setItems(induction.getResult());
        return pageInfo;
    }

}
