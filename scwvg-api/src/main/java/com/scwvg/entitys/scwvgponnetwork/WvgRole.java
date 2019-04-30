package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/29
 * @Explain：
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WvgRole implements Serializable {
    private  Integer wvg_role_id  ;//角色ID
    private  String wvg_role_name ;//角色名称
    private  Integer wvg_role_type;//角色类型（0，全部（包括1和2），1，操作<增，删，改> 2，查询）
    private  String wvg_add_time  ;//角色新增时间
    private  Integer wvg_user_id  ;//新增人

}
