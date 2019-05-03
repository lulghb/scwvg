package com.scwvg.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：全局中间信息表，传递中间信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg implements Serializable {
    private static final long serialVersionUID = -4417715614021482064L;

    private String code;
    private String message;

    /**
     * code  状态码
     * @param message		返回处理成功，还是失败得提示信息
     */


}
