package com.scwvg.system.entitys;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/23
 * @Explain：首页系统支撑实体类
 **/
@NoArgsConstructor
@Data
@Entity
@Table(name = "wvg_res_vendor")
public class SystemBraceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_vendor_id")
    private Long res_vendor_id;
    @Column(name = "res_vendor_name")
    private String res_vendor_name;
    @Column(name = "res_vendor_admin_name")
    private String res_vendor_admin_name;
    @Column(name = "res_position")
    private String res_position;
    @Column(name = "res_vendor_phone")
    private String res_vendor_phone;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "res_gahter")
    private String res_gahter;
    @Column(name = "res_position_content")
    private String res_position_content;
    @Column(name = "res_createtime")
    private Date res_createtime;
}
