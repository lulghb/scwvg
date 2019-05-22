package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.entitys.scwvgponnetwork.WvgVendor;
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





    /*厂家查询*/
    @GetMapping("/vendorQueryAll")
    @ApiOperation(value = "厂家列表")
    @Log("厂家列表查询")
    public @ResponseBody
    PageInfo<WvgVendor> vendorQueryAll(WvgVendor vendor, Page<WvgSpecType> page) {
        Map<String, Object> params = new HashMap<>();
        params.put("res_parent_id", vendor.getRes_parent_id());
        params.put("res_vendor_admin_name", vendor.getRes_vendor_admin_name());
        Page<WvgVendor> vendorQuery = publicService.queryVendorAll(params, page);
        PageInfo<WvgVendor> pageInfo = new PageInfo<>(vendorQuery.getPageNum(), vendorQuery.getPageSize(), vendorQuery.getTotal());
        pageInfo.setTotalPage(vendorQuery.getPages());
        pageInfo.setItems(vendorQuery.getResult());
        return pageInfo;
    }
    /*厂家新增*/
    @GetMapping("/addVendor")
    @ApiOperation(value = "厂家新增")
    @Log("厂家新增")
    @PreAuthorize("hasAuthority('vendor:add')")
    public Msg saveVendor(WvgVendor vendor){
        return publicService.saveVendor(vendor);
    }

    /*厂家新增*/
    @GetMapping("/editVendor")
    @ApiOperation(value = "厂家修改")
    @Log("厂家修改")
    @PreAuthorize("hasAuthority('vendor:edit')")
    public Msg editVendor(WvgVendor vendor){
        return publicService.editVendor(vendor);
    }
    /*厂家删除*/
    @GetMapping("/delVendor/{res_vendor_id}")
    @ApiOperation(value = "厂家删除")
    @Log("厂家删除")
    @PreAuthorize("hasAuthority('vendor:del')")
    public Msg delVendor(@PathVariable Long res_vendor_id){
        return publicService.delVendor(res_vendor_id);
    }
}
