package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgUser;
import com.scwvg.service.WvgMenuService;
import com.scwvg.utils.PageInfo;
import com.scwvg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/3
 * @Explain：菜单操作类
 **/
@Api(tags = "权限菜单")
@RestController
@RequestMapping("/menus")
public class WvgMenuController {
    @Autowired
    WvgMenuService menuService;

    @GetMapping("/menuList")
    @ApiOperation(value = "角色管理列表")
    @Log("角色列表查询")
    public @ResponseBody PageInfo<WvgMenu> queryMenuAll(WvgMenu wvgMenu, Page<WvgMenu> page){
        Map<String, Object> params = new HashMap<>();
        params.put("wvg_menu_name",wvgMenu.getWvg_menu_name());
        params.put("wvg_menu_type",wvgMenu.getWvg_menu_type());
        Page<WvgMenu> menus = menuService.queryMenuAll(params,page);
        PageInfo<WvgMenu> pageInfo = new PageInfo<>(menus.getPageNum(), menus.getPageSize(), menus.getTotal());
        pageInfo.setTotalPage(menus.getPages());
        pageInfo.setItems(menus.getResult());
        return pageInfo;
    }
    /**
     * 菜单新增
     */
   @GetMapping("/add/menu")
   @ApiOperation(value = "菜单新增")
   @Log("菜单新增")
   @PreAuthorize("hasAuthority('menus:add')")
   public Msg menuAdd(WvgMenu wvgMenu){
    return menuService.save(wvgMenu);
   }
    /**
     * 菜单修改
     */
    @GetMapping("/edit/menu")
    @ApiOperation(value = "菜单修改")
    @Log("菜单修改")
    @PreAuthorize("hasAuthority('menus:edit')")
    public Msg menuEdit(WvgMenu wvgMenu){
        return menuService.update(wvgMenu);
    }

    @GetMapping("/del/menu/{wvg_menu_id}")
    @ApiOperation(value = "菜单删除")
    @Log("菜单修改")
    @PreAuthorize("hasAuthority('menus:del')")
    public Msg menuDel(@PathVariable Long wvg_menu_id){
        return menuService.delete(wvg_menu_id);
    }
    /**
     * 校验权限
     * @return
     */
    @GetMapping("/owns")
    @ApiOperation(value = "校验当前用户的权限")
    public Set<String> ownsWvgMenu() {
        List<WvgMenu> wvgMenus = UserUtil.getLoginUser().getMenuList();
        if (CollectionUtils.isEmpty(wvgMenus)) {
            return Collections.emptySet();
        }

        return wvgMenus.parallelStream().filter(p -> !StringUtils.isEmpty(p.getWvg_authority()))
                .map(WvgMenu::getWvg_authority).collect(Collectors.toSet());
    }
    @ApiOperation(value = "当前登录用户拥有的菜单")
    @GetMapping("/current")
    public List<WvgMenu> menuCurrent() {
        WvgLoginUser loginUser = UserUtil.getLoginUser();
        /**
         * 查询出当前登录用户的菜单
         */
        List<WvgMenu> list = loginUser.getMenuList();
      /**
         * 查询出菜单
         */
        List<WvgMenu>  wvgMenus = list.stream().collect(Collectors.toList());

        setChild(wvgMenus);
        /*过滤出wvg_pert_id=0的菜单（根菜单）*/

        List<WvgMenu> tmp = wvgMenus.stream().filter(p -> p.getWvg_parent_id().equals(0L)).collect(Collectors.toList());
        return tmp;
    }

    /**
     * 筛选出叶节点对应的根节点
     * @param wvgMenus
     */
    private void setChild(List<WvgMenu> wvgMenus) {
        wvgMenus.parallelStream().forEach(per -> {
            List<WvgMenu> child = wvgMenus.stream().filter(p -> p.getWvg_parent_id()==(per.getWvg_menu_id()))
                    .collect(Collectors.toList());
            per.setChild(child);
        });
    }
}
