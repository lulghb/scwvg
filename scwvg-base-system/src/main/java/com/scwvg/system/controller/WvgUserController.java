package com.scwvg.system.controller;

import com.scwvg.system.service.WvgUserService;
import com.scwvg.system.utils.ResultVOUtil;
import com.scwvg.system.vo.UserVO;
import org.scwvg.commons.scwvgpage.PageCond;
import org.scwvg.commons.scwvgpage.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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
    @RequestMapping("/user/list")
    public Map<String , Object> userList(@RequestParam Map<String, String> criteria
            , @RequestParam(value = "pageNum" , defaultValue = "1" , required = false) Integer pageNum
            , @RequestParam(value = "pageSize" , defaultValue = "10" , required = false) Integer pageSize){
        Map<String , Object> map= new HashMap<String , Object>();
        PageCond<UserVO> pageCond = wvgUserServer.queryWvgUserByNamedSqlWithPage(new PageRequest(pageNum,pageSize),criteria);
        map.put("data" , pageCond.getList());
        map.put("count",pageCond.getLength());
        map.put("pageNum",pageCond.getCurrentPage());
        map.put("pageSize",pageCond.getSize());
        map.put("code",0);
        return map;
    }
}
