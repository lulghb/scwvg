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
 * @Explain：角色与菜单实体类（数据库表）
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WvgRoleMenu extends WvgRole implements Serializable {
    private static final long serialVersionUID = 4218495592167610193L;
    private Long wvgmenuIds;
}
