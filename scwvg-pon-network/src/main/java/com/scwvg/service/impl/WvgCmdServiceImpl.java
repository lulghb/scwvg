package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgCmdMapper;
import com.scwvg.service.WvgCmdService;
import com.scwvg.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/28
 * @Explain： 实现类
 **/
@Service
public class WvgCmdServiceImpl implements WvgCmdService {

    Msg msg=new Msg();
    int res;
    @Autowired
    WvgCmdMapper cmdMapper;
    @Override
    public Page<Map<String, Object>> queryCmdAll(Map<String, Object> params, Page<Map<String, Object>> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return cmdMapper.queryCmdAllByPage(params);
    }

    @Override
    public Page<Map<String, Object>> getInduction(Map<String, Object> params, Page<Map<String, Object>> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return cmdMapper.getInductionByPage(params);
    }

    @Override
    public Page<Map<String, Object>> getCmdAlgo(Map<String, Object> params, Page<Map<String, Object>> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return cmdMapper.getCmdAlgoByPage(params);
    }


    @Override
    public Msg saveCmdBaes(Map<String, Object> params) {
        WvgUser user =UserUtil.getLoginUser();
        params.put("wvg_user_id",user.getWvg_user_id());
        params.put("cmd_is_test","0");  //插入数据测试状态为未测试
       res= cmdMapper.saveCmdBaes(params);
       msg.setCode(res==1?"0":"1");
       return msg;
    }

    @Override
    public Map<String, Object> getCmdBaseByid(Long cmd_id) {
        return cmdMapper.getCmdBaseByid(cmd_id);
    }

    @Override
    public Msg editCmdBaes(Map<String, Object> params) {
        WvgUser user =UserUtil.getLoginUser();
        params.put("wvg_user_id",user.getWvg_user_id());
        res= cmdMapper.editCmdBaes(params);
        msg.setCode(res==1?"0":"1");
        return msg;
    }

    @Override
    public Msg deleteCmbBase(Long cmd_id) {
        int count=cmdMapper.getCuntGroup(cmd_id);
        if(count == 0){
            res=cmdMapper.deleteCmbBase(cmd_id);
            msg.setCode(res==1?"0":"1");
            msg.setMessage(res==1?"删除成功!":"删除失败，请联系系统厂商处理！");
        }
        else {
            msg.setCode("1");
            msg.setMessage("该指令已经分配组，请先进行组移除！");
        }
        return msg;
    }
}
