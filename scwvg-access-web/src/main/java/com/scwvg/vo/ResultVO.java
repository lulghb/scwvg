package com.scwvg.vo;

import lombok.Data;


@Data
/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 11:01
 * @Description: TODO
 * version 0.1
 */
public class ResultVO<T>{
    /**
     * @param code 错误码
     * @param msg 提示信息
     * @param data 具体内容
     */
    private Integer code;
    private String msg;
    private T data;
}
