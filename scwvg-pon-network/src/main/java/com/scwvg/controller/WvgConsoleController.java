package com.scwvg.controller;

import com.scwvg.service.WvgConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/10
 * @Explain：首页操作类
 **/
@RestController
@RequestMapping("/console")
public class WvgConsoleController {
    @Autowired
    WvgConsoleService consoleService;
    @GetMapping("/getVendorBrace")
    public List vendorBrace(){
        List vendorLists=consoleService.queryVendorBraceAll();
        return vendorLists;
    }
}
