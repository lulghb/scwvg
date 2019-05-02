package com.scwvg.zgy.system.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月19日14点54分
 * @Description: TODO 功能权限
 * version 0.1
 */
@Entity(name = "wvg_perm")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WvgPerm implements  Serializable{


    //功能权限主键
    @Id
    @Column(name="wvg_perm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long wvgPermId;

    //功能权限中文名
    @Column(name = "wvg_perm_show" , nullable = false)
    @Size(min = 2,max = 30 , message = "WvgPerm.wvgPermShow.size")
    @NotEmpty(message = "WvgPerm.wvgPermShow.notnull")
    private  String wvgPermShow;

    //功能权限英文名称
    @Column(name = "wvg_perm_name" , nullable = false)
    @Size(min = 2,max = 30 , message = "WvgPerm.wvgPermName.size")
    @NotEmpty(message = "WvgPerm.wvgPermName.notnull")
    private  String wvgPermName;

    //添加时间
    @Column(name = "wvg_add_time")
    private  String wvgAddTime;

    //修改时间
    @Column(name = "wvg_update_time")
    private  String wvgUpdateTime;

    //权限类型（0，有效，-1 作废）
    @Column(name = "wvg_perm_status",nullable = false)
    private  int wvgPermStatus = 0;

    // 角色与权限关系
    @ManyToMany(mappedBy = "permList")
    private List<WvgRole> roleList;

}
