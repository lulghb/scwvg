package com.scwvg.entitys;

import lombok.Data;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/3
 * @Explain：
 **/
@Data
public class RequestMassge {

    /**
     * @param success 		判断相应得是否是成功得处理
     * @param message		返回处理成功，还是失败得提示信息
     * @param body			对查询对象数据封装体
     *
     */

    private boolean success;
    private String message;
    private Object body;

    public RequestMassge(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }
    public RequestMassge(boolean success, String message, Object body) {
        super();
        this.success = success;
        this.message = message;
        this.body = body;
    }
}
