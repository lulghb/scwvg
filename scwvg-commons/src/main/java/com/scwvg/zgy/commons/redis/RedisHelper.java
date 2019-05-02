package com.scwvg.zgy.commons.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @project: baigle-redis
 * @author: chen.baihoo
 * @date: 2019/1/19 18:09
 * @Description: redis缓存操作
 * version 0.1
 */
@Component
@SuppressWarnings("all")
public class RedisHelper {

	private static Redis redis;

	@Autowired
	public void setRedis(Redis redis){
		RedisHelper.redis = redis;
	}
	/**
	 * @Description:  获取缓存数据
	 * @Param: [key]
	 * @return: T
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static <T> T getCache(Object key) {
		return redis.get(key);
	}
	/**
	 * @Description:  添加缓存
	 * @Param:
	 * @return:
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static boolean setCache(Object key, Object value) {
		return redis.set(key, value);
	}

	/**
	 * @Description:  设置缓存时效
	 * @Param: [key, seconds]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static boolean setCacheExpire(Object key,int seconds) {
		return redis.expire(key, seconds);
	}

	/**
	 * @Description:  添加含有时效的缓存
	 * @Param: [key, value, seconds]
	 * @return: java.lang.String
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static boolean setCache(Object key, Object value, int seconds) {
		return redis.setex(key, seconds, value);
	}

	/**
	 * @Description:  删除缓存
	 * @Param: [key]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static Long deleteCache(Object key) {
		return redis.del(key);
	}

	/**
	 * @Description:  删除缓存
	 * @Param: [keys]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static Long deleteCache(Object... keys) {
		return redis.del(keys);
	}

	/**
	 * @Description:  添加list缓存
	 * @Param: [key, datas]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static Long setCaches(Object key,List<Object> datas){
		return redis.rpush(key, datas.toArray(new Object[0]));
	}

	/**
	 * @Description:  添加list缓存
	 * @Param: [key, datas]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static Long setCaches(Object key,Object... datas){
		return redis.rpush(key, datas);
	}

	/**
	 * @Description:  获取list缓存，获取 list 中所有数据：getCache(key, 0, -1), 获取 list 中下标 1 到 3 的数据： getCache(key, 1, 3);
	 * @Param: [key, start, end]
	 * @return: java.util.List<T>
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static <T> List<T> getCaches(Object key,long start,long end){
		return redis.lrange(key, start, end);
	}

	/**
	 * @Description:  获取list缓存长度
	 * @Param: [key]
	 * @return: java.lang.Long
	 * @Author: chen.baihoo
	 * @Date: 2019/1/19
	 */
	public static Long getCachesllen(Object key){
		return redis.llen(key);
	}
}
