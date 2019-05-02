package com.scwvg.zgy.commons.scwvgpage;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: PageResult
 * @Description: 查詢結果
 * @author: Administrator
 * @date: 2018年7月29日 下午4:37:43
 * @param <T>
 */

@Data
public class PageResult<T> {
	/**
	 * @param list				数据集
	 * @param length		数据总共多少
	 */
	private List<T> list = new ArrayList<T>();
	private int length;
}
