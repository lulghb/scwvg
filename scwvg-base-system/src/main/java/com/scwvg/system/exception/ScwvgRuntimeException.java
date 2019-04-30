package com.scwvg.system.exception;

import com.scwvg.system.enums.ExceptionResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:20
 * @Description: TODO 工程运行时异常处理类
 * version 0.1
 */
@Getter
@Setter
public class ScwvgRuntimeException extends RuntimeException {
	
	/**
	 * @param 状态码
	 */
	private Integer statusCode;  

	public ScwvgRuntimeException(ExceptionResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.statusCode = resultEnum.getStatusCode();
	}
}
