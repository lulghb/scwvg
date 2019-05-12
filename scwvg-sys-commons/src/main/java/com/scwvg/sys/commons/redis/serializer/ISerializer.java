package com.scwvg.sys.commons.redis.serializer;

/**
 * @project: baigle-redis
 * @author: chen.baihoo
 * @date: 2019/1/19 18:30
 * @Description: TODO
 * version 0.1
 */
public interface ISerializer {
	
    byte[] keyToBytes(String key);
    String keyFromBytes(byte[] bytes);
    
    byte[] fieldToBytes(Object field);
    Object fieldFromBytes(byte[] bytes);
    
	byte[] valueToBytes(Object value);
    Object valueFromBytes(byte[] bytes);
}



