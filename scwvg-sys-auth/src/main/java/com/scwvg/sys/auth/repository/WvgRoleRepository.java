package com.scwvg.sys.auth.repository;


import com.scwvg.sys.auth.entitys.WvgRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 18:41
 * @Description: TODO 用户权限角色数据层仓库类，继承 jpa 接口
 * version 0.1
 */
public interface WvgRoleRepository extends JpaRepository<WvgRole, Long> {

}
