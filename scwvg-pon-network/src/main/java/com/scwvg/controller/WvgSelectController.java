package com.scwvg.controller;

import com.scwvg.service.WvgSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain：下拉框公共类查询
 **/
@RestController
@RequestMapping("/selects")
public class WvgSelectController {
    @Autowired
    WvgSelectService selectService;
    @GetMapping("/getUserTypeAll")
    public @ResponseBody Map<Integer,Map<String,Object>> getUserTypeAll(){
        return selectService.queryUserIdType();
    }
    @GetMapping("/getUserSpec")
    public @ResponseBody Map<Integer,Map<String,Object>> getUserSpec(){
        return selectService.querySpecs();
    }
    @GetMapping("/getUserCity")
    public @ResponseBody Map<String,Map<String,Object>> getUserCity(){
        return selectService.querCitys();
    }
    @GetMapping("/getUserRole")
    public @ResponseBody Map<Integer,Map<String,Object>> getUserRole(){
        return selectService.querRoles();
    }
    @GetMapping("/getParentMenus")
    public @ResponseBody Map<Integer,Map<String,Object>> getParentMenus(){
        return selectService.queryParentMenus();
    }
    @GetMapping("/getinitVendors")
    public @ResponseBody Map<Integer,Map<String,Object>> getinitVendors(){
        return selectService.queryVendorS();
    }
    @GetMapping("/getResVendorId")
    public @ResponseBody Map<Integer,Map<String,Object>> getResVendorId(){
        return selectService.queryVendorS();
    }
    @GetMapping("/queryProtocol")
    public @ResponseBody Map<Integer,Map<String,Object>> queryProtocol(){
        return selectService.queryProtocol();
    }
    @GetMapping("/queryOptType")
    public @ResponseBody Map<Integer,Map<String,Object>> queryOptType(){
        return selectService.queryOptType();
    }
    @GetMapping("/queryDataType")
    public @ResponseBody Map<Integer,Map<String,Object>> queryDataType(){
        return selectService.queryDataType();
    }
    @GetMapping("/queryResType")
    public @ResponseBody Map<Integer,Map<String,Object>> queryResType(){
        return selectService.queryResType();
    }
    @GetMapping("/queryIdtBaseInt")
    public @ResponseBody Map<Integer,Map<String,Object>> queryIdtBaseInt(){
        return selectService.queryIdtBaseInt();
    }
    @GetMapping("/queryCardType")
    public @ResponseBody Map<Integer,Map<String,Object>> queryCardType(){
        return selectService.queryCardType();
    }
    @GetMapping("/queryCycle")
    public @ResponseBody Map<Integer,Map<String,Object>> queryCycle(){
        return selectService.queryCycle();
    }
    @GetMapping("/getResModel")
    public @ResponseBody Map<Integer,Map<String,Object>> queryResModel(){
        return selectService.queryResModel();
    }
}
