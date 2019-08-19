package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdBase;
import com.scwvg.entitys.scwvgponnetwork.WvgCmdGroup;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgCmdMapper;
import com.scwvg.service.WvgCmdService;
import com.scwvg.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<WvgCmdBase> queryCmdBase(int vendor_id) {
        return cmdMapper.queryCmdBase(vendor_id);
    }

    @Override
    public List<WvgCmdBase> queryCmdBaseById(Long idt_cmd_id) {
        List<WvgCmdBase> cmdBases = new ArrayList<>();
        //先查出指令组里所有指令ID
        List<WvgCmdGroup> groupList= cmdMapper.queryCmdGroupByid(idt_cmd_id);
        for(int i=0;i<groupList.size();i++){
            String group=groupList.get(i).getCmd_id_group();
            String[] cmdGroup=group.split("\\|");
            for(int j=0;j<cmdGroup.length;j++){
                int cmd_id=Integer.valueOf(cmdGroup[j]).intValue();
                cmdBases.add(cmdMapper.queryCmdBaseById(cmd_id));
            }
        }
        return cmdBases;
    }

    /**
     * 新增宏指令
     * @param params
     * @return
     */
    @Override
    public Msg saveInduction(Map<String, Object> params) {
        WvgUser user =UserUtil.getLoginUser();
        params.put("wvg_user_id",user.getWvg_user_id());
        params.put("idt_is_test",0);

        int idtCount = cmdMapper.queryMaxInduction();
        params.put("idt_cmd_id",idtCount+1);


        String  rString= (String) params.get("cmd_id_group");
        params.put("group",rString.replaceAll(",","|"));

        if(params.get("base_id").equals("") ||params.get("base_id")==null){
            params.put("base_id",0);
        }

        res=cmdMapper.saveInduction(params);
        if(res >0){
            res=cmdMapper.saveGroup(params);
            msg.setCode(res!=0? "0":"1");
        }
        else{
            msg.setCode("1");
        }
        return msg;
    }

    @Override
    public Map<String, Object> getIdtCmdBaseById(Long idt_cmd_id) {
        Map<String, Object> map=cmdMapper.getIdtCmdBaseById(idt_cmd_id);
        return map;
    }

    /**
     * 修改
     * @param params
     * @return
     */
    @Override
    public Msg editInduction(Map<String, Object> params) {
        WvgUser user =UserUtil.getLoginUser();
        params.put("wvg_user_id",user.getWvg_user_id());
        params.put("idt_is_test",0);

        String  rString= (String) params.get("cmd_id_group");
        params.put("group",rString.replaceAll(",","|"));

        if(params.get("base_id").equals("") ||params.get("base_id")==null){
            params.put("base_id",0);
        }
        res = cmdMapper.editInduction(params);
        if(res>0){
                res =cmdMapper.editCmdGroup(params);
                msg.setCode(res>=1?"0":"1");
        }
        return msg;
    }

    @Override
    public Msg deleteIdtGroup(Long idt_cmd_id) {
        //先删除组数据
        res=cmdMapper.deleteGroup(idt_cmd_id);
        if(res>0){
            res=cmdMapper.deleteIdt(idt_cmd_id);
            msg.setCode(res==1?"0":"1");
            msg.setMessage(res==1?"删除成功!":"删除失败，请联系系统厂商处理！");
        }
        else {
            msg.setCode("1");
            msg.setMessage("组管理删除失败，请联系系统厂商处理！");
        }
        return msg;
    }
}
