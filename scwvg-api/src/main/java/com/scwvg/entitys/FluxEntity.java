package com.scwvg.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/27：10:48
 * 流量采集实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FluxEntity implements Serializable {
    private String olt_ip;       //OLT设备IP
    private String olt_id;       //OLT设备ID
    private String ponid;        //PON 口
    private String onu_index;    //ONU索引
    private String oid_read;     //MIB读口令
    private String oid_vsersion; //版本
    private String verdor_id;     //厂家ID
    private String mac_addr;      //mac地址
    private String barch;          //带宽
    private String db_source;     //所在表

}
