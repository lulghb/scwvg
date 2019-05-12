package com.scwvg.sys.auth.mapper;

import com.scwvg.sys.auth.entitys.WvgMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/20 17:50
 * @Description: TODO TODO 查询树结构菜单
 * version 0.1
 */
@Mapper
public interface WvgMenuMapper {
    
    /** 
     * @Description: 查询用户角色拥有的菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20 
     */

    public List<WvgMenu> findDistinctByRoleListIsIn(@Param("roleids") List<Long> roleids);

    /**
     * @Description: 查询菜单 和 范围菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */
    public List<WvgMenu> findByWvgMenuIdEqualsOrWvgMenuIdIsIn(@Param("menuid") Long menuid , @Param("menuids")  List<Long> menuids);

    /**
     * @Description: like 查询菜单 和 范围菜单
     * @Author: chen.baihoo
     * @Date: 2019/4/20
     */
    public List<WvgMenu> findByWvgMenuWvgSeqIsLike(@Param("wvgSeq") String wvgSeq);
}
