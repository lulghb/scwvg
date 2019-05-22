package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WvgRole extends BaseEntity<Long> {
    private static final long serialVersionUID = -3802292814767103648L;
    private long wvg_role_id;//	             角色ID
    private String wvg_role_name;//          角色名称
    private String wvg_role_description;//   角色描述

}
