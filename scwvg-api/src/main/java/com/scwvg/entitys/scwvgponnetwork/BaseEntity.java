package com.scwvg.entitys.scwvgponnetwork;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：此处因为很多表都需要用到UUID和创建时间，修改时间，所以进行单独定义
 **/
@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable
{
    private static final long serialVersionUID = 2054813493011812469L;
    //生成UUID
    private ID id; //TokenID

    private Date createTime = new Date();
    private Date updateTime = new Date();

    public ID getId() {
        return id;
    }
}
