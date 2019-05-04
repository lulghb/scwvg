package com.scwvg.controller;

import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/3
 * @Explain：菜单操作类
 **/
@Api(tags = "权限")
@RestController
@RequestMapping("/menu")
public class WvgMenuController {
    @ApiOperation(value = "当前登录用户拥有的菜单")
    @GetMapping("/current")
    public List<WvgMenu> menuCurrent() {
        WvgLoginUser loginUser = UserUtil.getLoginUser();
        List<WvgMenu> list = loginUser.getMenuList();
        /**
         * 查询出根节点菜单
         */
        final List<WvgMenu> wvgMenus = list.stream().filter(l -> l.getWvg_menu_type().equals("L"))
                .collect(Collectors.toList());

        setChild(wvgMenus);

        /*过滤出wvg_pert_id=0的菜单（根菜单）*/
        return wvgMenus.stream().filter(p -> p.getWvg_parent_id().equals(0L)).collect(Collectors.toList());
    }

    private void setChild(List<WvgMenu> permissions) {
        permissions.parallelStream().forEach(per -> {
            List<WvgMenu> child = permissions.stream().filter(p -> p.getWvg_parent_id().equals(per.getId()))
                    .collect(Collectors.toList());
            per.setMenuChild(child);
        });
    }
}
