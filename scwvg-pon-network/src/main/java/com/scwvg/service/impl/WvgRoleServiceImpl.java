package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.mappers.WvgRoleMapper;
import com.scwvg.mappers.WvgUserMapper;
import com.scwvg.service.WvgRoleService;
import com.scwvg.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色操作类
 **/
@Service
public class WvgRoleServiceImpl implements WvgRoleService {
    private static final Logger log = LoggerFactory.getLogger("WvgRoleServiceImpl");

    Msg msg = new Msg();
    private int res;
    @Autowired
    WvgRoleMapper roleMapper;

    @Override
    public Page<WvgRole> queryAllRols(Map<String, Object> params, Page<WvgRole> page) {
        //分页必须使用此段代码
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        Page<WvgRole> roles = roleMapper.queryAllRoleByPage(params);
        for (int i = 0; i < roles.size(); i++) {
          String wvg_real_name =roleMapper.getUserName(roles.get(i).getWvg_user_id());
          roles.get(i).setChangeStr(wvg_real_name);
        }
        return roles;
    }

    @Override
    public List<WvgMenu> roledTreeList() {
        List<WvgMenu> roleMenus =roleMapper.queryTreeList();
        return roleMenus;
    }

    @Override
    public List<WvgMenu> queryMenuRoleId(Long wvg_role_id) {
        return roleMapper.queryMenuRoleId(wvg_role_id);
    }

    @Override
    public Msg addRole(WvgRole role) {
        WvgRole r = roleMapper.queryRoleByName(role.getWvg_role_name());
        if (r != null && r.getId() != role.getId()) {
            msg.setCode("0");
            msg.setMessage("角色已存在！新增失败！");
            return msg;
        }
        int wvg_role_id=roleMapper.queryMaxRoleId();
        role.setWvg_role_id(wvg_role_id+1L);

        role.setWvg_user_id(UserUtil.getLoginUser().getWvg_user_id());
        res=roleMapper.save(role);
        msg.setCode(res==1?"0":"1");
        msg.setMessage(res==1?"新增成功！":"新增失败！");
        return msg;
    }

    @Override
    public Msg editRole(WvgRole role) {
        res=roleMapper.editRole(role);
        msg.setCode(res==1?"0":"1");
        msg.setMessage(res==1?"修改成功！":"修改失败！");
        return msg;
    }

    @Override
    public Msg delRoles(Long wvg_role_id) {
        res =roleMapper.countRoleMenus(wvg_role_id);
        if(res>0){
            res=roleMapper.delRoleMenus(wvg_role_id); //删除角色菜单中间表
        }
        res = roleMapper.countRoleUser(wvg_role_id);
        if(res>0){
            res=roleMapper.delRoleUsers(wvg_role_id);//删除角色用户中间表
        }

        res =roleMapper.deleteWvgRole(wvg_role_id);
        msg.setCode(res==1?"0":"1");
        msg.setMessage(res >=1?"删除成功":"删除失败！");
        return msg;
    }

    @Override
    public Msg addRoleAuthority(String wvg_role_id,String wvg_menu_id) {
        String[] menuids = wvg_menu_id.split(",");
        int role_id = Integer.parseInt(wvg_role_id);
        //先删除角色对应的所有权限，再根据用户点击的权限进行新增
        res=roleMapper.delRoleMenus(Long.valueOf(wvg_role_id));
        for(int i=0;i<menuids.length;i++){
            int menu_id=Integer.parseInt(menuids[i]);
            res= roleMapper.saveWvgRoleMenus(role_id,menu_id);
            msg.setMessage(res==1?"操作成功！":"操作失败！");
        }
        return msg;
    }


}
