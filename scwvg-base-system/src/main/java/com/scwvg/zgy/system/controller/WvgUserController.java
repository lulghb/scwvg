package com.scwvg.zgy.system.controller;

import com.scwvg.zgy.system.enums.LayuiCodeEnum;
import com.scwvg.zgy.system.service.WvgUserService;
import com.scwvg.zgy.system.vo.Layui;
import com.scwvg.zgy.system.vo.UserVO;
import com.scwvg.zgy.commons.page.PageCond;
import com.scwvg.zgy.commons.page.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/21 8:15
 * @Description: TODO 用户 web 控制层
 * version 0.1
 */
@RestController
public class WvgUserController {
    @Resource
    private WvgUserService wvgUserServer;
    
    /** 
     * @Description:
     * @Author: chen.baihoo
     * @Date: 2019/4/21 
     */ 
    @RequestMapping("/user/userManager")
    public Layui<String , Object> userList(@RequestParam Map<String, String> criteria
            , @RequestParam(value = "pageNum" , defaultValue = "1" , required = false) Integer pageNum
            , @RequestParam(value = "pageSize" , defaultValue = "10" , required = false) Integer pageSize){
        Layui<String , Object> layui= new Layui<String,Object>();

        PageCond<UserVO> pageCond = wvgUserServer.queryWvgUserByNamedSqlWithPage(new PageRequest(pageNum,pageSize),criteria);
        layui.put("data" , pageCond.getList());
        layui.put("count",pageCond.getLength());
        layui.put("pageNum",pageCond.getCurrentPage());
        layui.put("pageSize",pageCond.getSize());
        layui.put("code",LayuiCodeEnum.SUCCESS.getCode());
        return layui;
    }

    /**
     *
     * @param params
     * @return
     */
    @PostMapping("/user/edit")
    @PreAuthorize("ROLE_ADMIN")
    public Layui<String, Object> userEdit(@RequestParam Map<String ,Object> params){
        return wvgUserServer.save(params);
    }

    @DeleteMapping("/user/del/{id}")
    @PreAuthorize("ROLE_ADMIN")
    public Layui<String,Object> userDel(@PathVariable("id") Long id){
        return wvgUserServer.delete(id);
    }

}
