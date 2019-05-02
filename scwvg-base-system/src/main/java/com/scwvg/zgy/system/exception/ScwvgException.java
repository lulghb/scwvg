package com.scwvg.zgy.system.exception;

import com.scwvg.zgy.system.enums.ExceptionResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:17
 * @Description: TODO 工程异常处理类
 * version 0.1
 */
@Getter
@Setter
public class ScwvgException extends Exception {
	
	/**
	 * @param 状态码
	 */
	private Integer statusCode;  
	/**
	 * 自定义错误异常信息
	 * @param message
	 */
	public ScwvgException(String message) {
		super(message);
	}
	public ScwvgException(ExceptionResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.statusCode = resultEnum.getStatusCode();
	}
}
