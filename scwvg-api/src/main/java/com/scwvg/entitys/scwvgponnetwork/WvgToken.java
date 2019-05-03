package com.scwvg.entitys.scwvgponnetwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：用户状态
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WvgToken extends BaseEntity<String> implements  Serializable {

    private static final long serialVersionUID = 6314027741784310221L;
    /** 过期时间戳（毫秒） */
    private Date wvg_token_expireTime;
    /**
     * WvgUser的json串
     */
    private String wvg_token_val;
}
