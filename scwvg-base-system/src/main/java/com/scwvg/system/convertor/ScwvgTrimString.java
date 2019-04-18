package com.scwvg.system.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 18:56
 * @Description: TODO 定义字符串去空处理,spring是默认不会把空值转化为null
 * version 0.1
 */
@Component
public class ScwvgTrimString implements Converter<String , String> {

	@Override
	public String convert(String source) {
		try {
			if(source !=null) {
				source = source.trim();
				if(source.equals("")) {
					source = null;
				}
			}
			return source;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
