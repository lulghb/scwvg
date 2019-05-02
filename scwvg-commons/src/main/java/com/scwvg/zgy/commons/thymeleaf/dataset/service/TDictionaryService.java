package com.scwvg.zgy.commons.thymeleaf.dataset.service;

import com.alibaba.fastjson.JSONObject;
import com.scwvg.zgy.commons.redis.RedisHelper;
import com.scwvg.zgy.commons.thymeleaf.dataset.TDictionary;
import com.scwvg.zgy.commons.thymeleaf.dataset.mapper.TDictionaryMapper;
import com.scwvg.zgy.commons.thymeleaf.dataset.repository.TDictionaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @project: baigle-util
 * @author: chen.baihoo
 * @date: 2019/1/19 17:51
 * @Description: 数据字典服务
 * version 0.1
 */
@Service("tDictionaryService")
public class TDictionaryService{

	@Resource
	private TDictionaryMapper tDictionaryMapper;
	@Resource
	private TDictionaryRepository tDictionaryRepository;

	/**
	 * 保存数据字典
	 * @Param: [TDictionary tDictionary]
	 * @Author: chen.baihoo
	 * @Date: 2019年4月25日
	 * @param tDictionary
	 */
	public void insert(TDictionary tDictionary){
		tDictionaryRepository.save(tDictionary);
	}
	/**
	 * @Description:  获取数据字典
	 * @Param: [HashMap para]
	 * @return: TDictionary[]
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public TDictionary[] findTDictionary(HashMap para) {
		StringBuilder cachekey=new StringBuilder();
		String dicttypecode = (String) para.get("dictTypeCode");
		if(StringUtils.isNotBlank(dicttypecode)){
			cachekey.append("d"+dicttypecode);
		}

		String excludeId = (String) para.get("excludeId");
		if(StringUtils.isNotBlank(excludeId)){
			cachekey.append("ex"+excludeId.replace("\\.", ""));
		}
		String dictid = (String) para.get("dictId");
		if(StringUtils.isNotBlank(dictid)){
			cachekey.append("di"+dictid);
		}
		String pdictid = (String) para.get("pDictId");
		if(StringUtils.isNotBlank(pdictid)){
			cachekey.append("pdi"+pdictid);
		}
		String pdicttypecode = (String) para.get("pDictTypeCode");
		if(StringUtils.isNotBlank(pdicttypecode)){
			cachekey.append("pdic"+pdicttypecode);
		}
		if(StringUtils.isNotBlank(cachekey.toString())){
			cachekey.append("alldict");
		}
		TDictionary[] dicts=RedisHelper.getCache(cachekey.toString());
		if(dicts==null||dicts.length==0){
			List<TDictionary> list = tDictionaryMapper.
					findTDictionary(para);
			dicts = list.toArray(new TDictionary[list.size()]);
			RedisHelper.setCache(cachekey.toString(), dicts,300);
		}
		return dicts;
	}
	
	/**
	 * @project: 以json格式获取数据字典
	 * @author: chen.baihoo
	 * @date: 2019/1/19 19:48
	 * @Description: TODO
	 * version 0.1
	 */
	public String getDictJson(HashMap para) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuilder cachekey=new StringBuilder();
		String dicttypecode = (String) para.get("dictTypeCode");
		if(StringUtils.isNotBlank(dicttypecode)){
			cachekey.append("d"+dicttypecode);
		}
		String excludeId = (String) para.get("excludeId");
		if(StringUtils.isNotBlank(excludeId)){
			cachekey.append("ex"+excludeId.replace("\\.", ""));
		}
		String dictid = (String) para.get("dictId");
		if(StringUtils.isNotBlank(dictid)){
			cachekey.append("di"+dictid);
		}
		String pdictid = (String) para.get("pDictId");
		if(StringUtils.isNotBlank(pdictid)){
			cachekey.append("pdi"+pdictid);
		}
		String pdicttypecode = (String) para.get("pDictTypeCode");
		if(StringUtils.isNotBlank(pdicttypecode)){
			cachekey.append("pdic"+pdicttypecode);
		}
		if(StringUtils.isNotBlank(cachekey.toString())){
			cachekey.append("alldict");
		}
		TDictionary[] dicts=RedisHelper.getCache(cachekey.toString());
		if(dicts==null||dicts.length==0){
			List<TDictionary> tDictionaries = tDictionaryMapper.
					findTDictionary(para);
			dicts = tDictionaries.toArray(new TDictionary[tDictionaries.size()]);
			RedisHelper.setCache(cachekey.toString(), dicts,300);
		}
		String defaultsel = (String) para.get("DEFAULTSEL");
		if(StringUtils.isNotBlank(defaultsel)){
			JSONObject json = new JSONObject();
			json.put("title", defaultsel);
			json.put("value", "");
			list.add(json);
		}
		for (int j = 0; j < dicts.length; j++) {
			JSONObject json = new JSONObject();
			json.put("title", dicts[j].getDictName());
			json.put("value", dicts[j].getDictId());
			list.add(json);
		}
		return list.toString();
	}

}
