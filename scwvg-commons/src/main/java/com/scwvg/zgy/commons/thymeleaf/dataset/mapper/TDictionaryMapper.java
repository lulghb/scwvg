package com.scwvg.zgy.commons.thymeleaf.dataset.mapper;


import com.scwvg.zgy.commons.thymeleaf.dataset.TDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


/**
 * @project: baigle-util
 * @author: chen.baihoo
 * @date: 2019/1/19 17:45
 * @Description: 数据字典服务接口
 * version 0.1
 */
@Mapper
public interface TDictionaryMapper {
	/**
	 * 新增数据字典
	 * @param dictionary
	 */
	public void insert(@Param("td") TDictionary dictionary);
	/**
	 * 根据条件查询数据字典的数据
	 * @param para
	 * @return List<TDictionary>
	 */
	public List<TDictionary> findTDictionary(@Param("para") HashMap para);

}
