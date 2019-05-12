package com.scwvg.sys.content.controller;

import com.scwvg.sys.commons.vo.Layui;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年5月7日18点17分
 * @Description: TODO 数据字典控制层
 * version 0.1
 */
@RestController
@RequestMapping("/sys/content/dict")
public class DictRestController {

    /**
     * 添加数据字典
     * @param params
     * @return
     */
    @PostMapping("/addDict")
    public Layui<String, Object> addDict(@RequestParam Map<String ,Object> params){
        Layui<String , Object> layui= new Layui<String,Object>();
        return layui;
    }
    /**
     * 获取数据字典
     * @param params
     * @return
     */
    @PostMapping("/getDict")
    public Layui<String, Object> getDict(@RequestParam Map<String ,Object> params){
        Layui<String , Object> layui= new Layui<String,Object>();
        return layui;
    }

    /**
     *
     * @param params
     * @return
     */
    @PostMapping("/editDict")
    public Layui<String, Object> editDict(@RequestParam Map<String ,Object> params){
        Layui<String , Object> layui= new Layui<String,Object>();
        return layui;
    }
}
