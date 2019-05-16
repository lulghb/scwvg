package com.scwvg.controller;

import com.github.pagehelper.Page;
import com.scwvg.annotation.Log;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.WvgMenu;
import com.scwvg.entitys.scwvgponnetwork.WvgRole;
import com.scwvg.entitys.scwvgponnetwork.WvgRoleMenu;
import com.scwvg.service.WvgRoleService;
import com.scwvg.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/14
 * @Explain：角色管理
 **/
@Api(tags = "角色")
@RestController
@RequestMapping("/roles")
public class WvgRoleController {
    @Autowired
    WvgRoleService roleService;


    @GetMapping("/roleList")
    @ApiOperation(value = "角色查询")
    @Log("角色列表查询")
    public @ResponseBody
    PageInfo<WvgRole> queryAllUsers(WvgRole role, Page<WvgRole> page) {
        Map<String, Object> params = new HashMap<>();
        params.put("wvg_role_name", role.getWvg_role_name());
        params.put("wvg_role_description", role.getWvg_role_description());
        Page<WvgRole> queryAllUser = roleService.queryAllRols(params, page);
        PageInfo<WvgRole> pageInfo = new PageInfo<>(queryAllUser.getPageNum(), queryAllUser.getPageSize(), queryAllUser.getTotal());
        pageInfo.setTotalPage(queryAllUser.getPages());
        pageInfo.setItems(queryAllUser.getResult());
        return pageInfo;
    }

    @GetMapping("/addRole")
    @ApiOperation(value = "角色新增")
    @PreAuthorize("hasAuthority('role:add')")
    @Log("角色新增")
    public Msg addRole(WvgRole role) {
        Msg msg =null;
        if(role.getWvg_role_id()!=0){
            //修改
            msg=roleService.editRole(role);
        }
        else{
            //新增
             msg=roleService.addRole(role);
        }
        return msg;
    }

    @GetMapping("/delRole/{wvg_role_id}")
    @ApiOperation(value = "角色删除")
    @PreAuthorize("hasAuthority('role:del')")
    @Log("角色删除")
    public Msg delRole(@PathVariable Long wvg_role_id) {
        Msg msg =roleService.delRoles(wvg_role_id);
        return msg;
    }


    @PostMapping("/roledTreeList")
    @ApiOperation(value = "权限查询")
    @Log("权限查询")
    public @ResponseBody
    Object roledTreeList() {
        List<WvgMenu> menus = roleService.roledTreeList();
        DTree dTree = null;
        List<DTree> list = new ArrayList<DTree>();

        CheckArr checkArr = new CheckArr();

        for (WvgMenu menu : menus) {
            //筛选出父节点
            if (menu.getWvg_parent_id() == 0L) {
                dTree = new DTree();

                dTree.setId(menu.getWvg_menu_id());
                dTree.setTitle(menu.getWvg_menu_name());
                dTree.setParentId(menu.getWvg_parent_id());


                checkArr.setType(0);
                checkArr.setIsChecked(0);
                dTree.setCheckArr(checkArr);

                List<DTree> childList = new ArrayList<>();
                dTree.setChildren(childList);

                for (WvgMenu tmp : menus) {
                    if (tmp.getWvg_parent_id() == menu.getWvg_menu_id()) {
                        checkArr.setType(0);
                        checkArr.setIsChecked(0);

                        DTree childMenu = new DTree();
                        childMenu.setId(tmp.getWvg_menu_id());
                        childMenu.setTitle(tmp.getWvg_menu_name());
                        childMenu.setParentId(tmp.getWvg_parent_id());

                        childMenu.setCheckArr(checkArr);

                        childList.add(childMenu);
                        List<DTree> thirdList = new ArrayList<>();
                        childMenu.setChildren(thirdList);

                        for (WvgMenu third : menus) {
                            if (third.getWvg_parent_id() == tmp.getWvg_menu_id()) {
                                checkArr.setType(0);
                                checkArr.setIsChecked(0);

                                DTree thirdMenu = new DTree();
                                thirdMenu.setId(third.getWvg_menu_id());
                                thirdMenu.setTitle(third.getWvg_menu_name());
                                thirdMenu.setParentId(third.getWvg_parent_id());

                                thirdMenu.setCheckArr(checkArr);

                                thirdList.add(thirdMenu);

                            }

                        }
                    }

                }
                list.add(dTree);
            }
        }
        DTreeResponse dTreeResponse = new DTreeResponse();
        if (menus.size() > 0) {
            Status status = new Status();
            status.setCode(200);
            status.setMsg("查询成功！");
            dTreeResponse.setStatus(status);
            dTreeResponse.setData(list);
        }
        return dTreeResponse;
    }

}

/**
 * response返回类
 */
@Getter
@Setter
class DTreeResponse {
    private Status status;
    /**
     * 数据
     */
    private Object data;
}

@Getter
@Setter
class Status {
    /**
     * 状态码
     */
    private int code;
    /**
     * 信息标识
     */
    private String msg;
}

@Getter
@Setter
        /*树数据类*/
class DTree {
    /**
     * 节点ID
     */
    private long id;
    /**
     * 上级节点ID
     */
    private Long parentId;
    /**
     * 节点名称
     */
    private String title;
    /**
     * 是否展开节点
     */
    private Boolean spread = false;
    private CheckArr checkArr;
    /**
     * 子节点集合
     */
    private List<DTree> children = new ArrayList<DTree>();

}

@Getter
@Setter
class CheckArr {
    private int type = 0;
    private int isChecked = 0;
}