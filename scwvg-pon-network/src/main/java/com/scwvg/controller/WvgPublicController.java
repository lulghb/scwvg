package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.service.WvgPublicService;
import com.scwvg.utils.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/21
 * @Explain：公共请求类
 **/
@RestController
@RequestMapping("/publics")
public class WvgPublicController {
   @Autowired
    WvgPublicService publicService;

    /*专业查询*/
    @GetMapping("/specQueryAll")
    @ApiOperation(value = "专业列表")
    @Log("专业列表查询")
   public @ResponseBody
    PageInfo<WvgSpecType> specQueryAll(WvgSpecType spec, Page<WvgSpecType> page) {
        Map<String, Object> params = new HashMap<>();
        params.put("spec_name", spec.getSpec_name());
        Page<WvgSpecType> specQuery = publicService.querySpecAll(params, page);
        System.out.println(specQuery.getPageSize());
        PageInfo<WvgSpecType> pageInfo = new PageInfo<>(specQuery.getPageNum(), specQuery.getPageSize(), specQuery.getTotal());
        pageInfo.setTotalPage(specQuery.getPages());
        pageInfo.setItems(specQuery.getResult());
        return pageInfo;
    }

    /*专业新增*/
    @GetMapping("/addSpec")
    @ApiOperation(value = "用户新增")
    @Log("角色新增")
    @PreAuthorize("hasAuthority('spec:add')")
    public Msg saveSpec(WvgSpecType specType){
        return publicService.saveSpec(specType);
    }
    /*专业修改*/
    @GetMapping("/editSpec")
    @ApiOperation(value = "专业修改")
    @Log("专业修改")
    @PreAuthorize("hasAuthority('spec:edit')")
    public Msg editSpec(WvgSpecType specType){
        return publicService.editSpec(specType);
    }
    /*专业删除*/
    @GetMapping("/delSpec/{spec_id}")
    @ApiOperation(value = "角色删除")
    @Log("角色删除")
    @PreAuthorize("hasAuthority('spec:del')")
    public Msg delSpec(@PathVariable Long spec_id){
        return publicService.delSpec(spec_id);
    }

}
