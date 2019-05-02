package com.scwvg.zgy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum LayuiCodeEnum {

    SUCCESS(0,"成功"),
    ERROR(-1,"错误");
    private int code;
    private String mes;
}
