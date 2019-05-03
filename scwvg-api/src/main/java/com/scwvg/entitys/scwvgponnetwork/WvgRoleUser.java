package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：角色权限中间表
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WvgRoleUser extends WvgUser implements Serializable {
    private static final long serialVersionUID = -184009306207076712L;
    private List<Long> roleIds;
}
