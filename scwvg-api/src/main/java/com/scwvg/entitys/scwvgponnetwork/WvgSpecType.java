package com.scwvg.entitys.scwvgponnetwork;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/7
 * @Explain：专业管理
 **/
@Data
public class WvgSpecType extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -6525908145032868837L;
    private Long  spec_id;     //专业ID
    private String spec_name;  //专业名称
    private Long wvg_user_id;  //专业新增人
}
