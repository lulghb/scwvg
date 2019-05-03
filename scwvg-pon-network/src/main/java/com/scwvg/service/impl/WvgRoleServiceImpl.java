package com.scwvg.service.impl;

import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;
import com.scwvg.mappers.WvgRoleMapper;
import com.scwvg.service.WvgRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    @Autowired
    WvgRoleMapper roleMapper;

    @Override
    @Transactional
    public void saveRole(WvgRoleMenu roleMenu) {
        WvgRole wvgRole=roleMenu;
        List<Long> menuIds =roleMenu.getWvgmenuIds();
        menuIds.remove(0L);
        if(wvgRole.getId() !=null){
            updateRole(roleMenu,menuIds);  //修改角色
        }else{
            saveRole(roleMenu,menuIds);
        }

    }
    private void updateRole(WvgRole role, List<Long> menuIds) {
        WvgRole r = roleMapper.queryRoleByName(role.getWvg_role_name());
        if (r != null && r.getId() != role.getId()) {
            throw new IllegalArgumentException(role.getWvg_role_name() + "已存在");
        }
        roleMapper.update(role);
        roleMapper.deleteWvgRoleWvgMenu(role.getId());
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMapper.saveRoleMenu(role.getId(), menuIds);
        }
        log.debug("修改角色{}", role.getWvg_role_name());
    }
    private void saveRole(WvgRole role, List<Long> menuIds) {
        WvgRole r = roleMapper.queryRoleByName(role.getWvg_role_name());
        if (r != null) {
            throw new IllegalArgumentException(role.getWvg_role_name() + "已存在");
        }

        roleMapper.save(role);
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMapper.saveRoleMenu(role.getId(), menuIds);
        }
        log.debug("新增角色{}", role.getWvg_role_name());
    }

    @Override
    public void deleteRole(Long id) {
        /*通过角色ID删除菜单角色中间表*/
        roleMapper.deleteWvgRoleWvgMenu(id);
        /*通过角色ID删除用户和角色表*/
        roleMapper.deleteWvgRoleUser(id);
        /*删除觉表数据*/
        roleMapper.deleteWvgRole(id);
       log.debug("角色删除完成！"+id);
    }
}
