package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgSpecType;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgPublicMapper;
import com.scwvg.service.WvgPublicService;
import com.scwvg.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/21
 * @Explain：公共查询实现类
 **/
@Service
public class WvgPublicServiceImpl implements WvgPublicService {
    Msg msg =new Msg();

    @Autowired
    WvgPublicMapper publicMapper;
    @Override
    public Page<WvgSpecType> querySpecAll(Map<String, Object> params, Page<WvgSpecType> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Page<WvgSpecType> specInfo= publicMapper.querySpecAllByPage(params);
        for(WvgSpecType spec:specInfo){
            spec.setChangeStr(publicMapper.getWvgUserName(spec.getWvg_user_id()));
        }
        return specInfo;
    }

    @Override
    public Msg saveSpec(WvgSpecType specType) {
       int specId= publicMapper.countSpecId();
       specType.setSpec_id(specId+1L);

       WvgUser user = UserUtil.getLoginUser();
       specType.setWvg_user_id(user.getWvg_user_id());
       int res =publicMapper.saveSpec(specType);
       msg.setCode(res==1?"0":"1");
       return msg;
    }

    @Override
    public Msg editSpec(WvgSpecType specType) {
        int res = publicMapper.editSpec(specType);
        msg.setCode(res==1?"0":"1");
        return msg;
    }

    @Override
    public Msg delSpec(Long spec_id) {
        int count=publicMapper.countUserSpec(spec_id);
        if(count>0){
            msg.setCode(count>0?"1":"0");
            msg.setMessage("有"+count+"个用户关联了该专业，无法删除。请先修改用户专业！");
        }
        else{
            int res=publicMapper.deleteSpec(spec_id);
            msg.setCode(res==1?"0":"1");
            msg.setMessage(res==1?"删除成功！":"删除失败");
        }
        return msg;
    }

}
