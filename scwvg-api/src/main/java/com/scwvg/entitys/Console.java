package com.scwvg.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/23
 * @Explain：首页数据公共类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Console implements Serializable {
    private static final long serialVersionUID = -4417715614021482064L;
    private int city_id;    //属地编码
    private String city_name;//属地名称
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date produce_date; //生产日期
}
