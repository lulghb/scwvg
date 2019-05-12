package com.scwvg.sys.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设置用账号状态类型
 */
@Getter
@AllArgsConstructor
public enum  AccountTypeEnum {

    ACCOUNT_DEL(-1,"软删除"),
    ACCOUNT_ACTIVE(0, "账号活动"),
    ACOUNT_LOCK(1,"账号锁定");

    private int status;
    private String msg;
}
