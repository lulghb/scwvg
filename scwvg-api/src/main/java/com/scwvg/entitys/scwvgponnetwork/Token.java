package com.scwvg.entitys.scwvgponnetwork;

import java.io.Serializable;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：
 **/
public class Token implements Serializable {
    private static final long serialVersionUID = 6314027741784310221L;

    private String token;
    /** 登陆时间戳（毫秒） */
    private Long loginTime;

    public Token(String token, Long loginTime) {
        super();
        this.token = token;
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
