package com.scwvg.zgy.system.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:18
 * @Description: TODO 把错误码及错误信息，组装起来统一管理。定义一个业务异常的枚举
 * version 0.1
 */
@Getter
@AllArgsConstructor
public enum ExceptionResultEnum {
	
	UNKONW_ERROR(-1,"未知错误"),
	SUCCESS(0,"成功"),
	ERROR(1,"失败"),
	;
	private Integer statusCode;
	private String message;

}
