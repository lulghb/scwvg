package com.scwvg.entitys.scwvgponnetwork;

import lombok.Data;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/21
 * @Explain：厂家管理
 **/
@Data
public class WvgVendor extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -6525908145032868837L;
    private Long res_vendor_id;      //厂家ID
    private Long res_parent_id;      //父级ID
    private String res_vendor_name;  //厂家名称
    private String res_vendor_admin_name;//支撑人员姓名
    private String res_position;     //职位
    private String res_vendor_phone; //电话
    private String res_position_content;//职位范畴
}
