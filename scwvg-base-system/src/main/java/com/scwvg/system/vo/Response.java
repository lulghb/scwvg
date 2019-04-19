package com.scwvg.system.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:10
 * @Description: TODO 前台页面，相应返回得对象类
 * version 0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class Response {

	/**
	 * @param success 		判断相应得是否是成功得处理
	 * @param message		返回处理成功，还是失败得提示信息
	 * @param body			对查询对象数据封装体
	 *
	 */

	private boolean success;
	private String message;
	private Object body;

	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public Response(boolean success, String message, Object body) {
		super();
		this.success = success;
		this.message = message;
		this.body = body;
	}
}
