package com.scwvg.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/20 22:18
 * @desc: 
**/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelField {
	
	/**
	 * 列名称
	 * @return
	 */
	String name() default "";
	
	/**
	 * 列宽
	 * @return
	 */
	int width() default 0;
	
	/**
	 * 日期格式
	 * @return
	 */
	String dateformat() default "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 水平对齐方式
	 * @return
	 */
	HorizontalAlignment align() default HorizontalAlignment.LEFT;

}
