package com.scwvg.zgy.commons.redis.serializer;

import org.springframework.stereotype.Service;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @project: baigle-redis
 * @author: chen.baihoo
 * @date: 2019/1/19 18:51
 * @Description: TODO
 * version 0.1
 */
@Service("jdkSerializer")
public class JdkSerializer implements ISerializer {

	/**
	 * 字符串 key 转化成字节
	 * @param key
	 * @return
	 */
	public byte[] keyToBytes(String key) {
		return SafeEncoder.encode(key);
	}
	/**
	 * 字节 key 转化成字符串
	 * @param bytes
	 * @return
	 */
	public String keyFromBytes(byte[] bytes) {
		return SafeEncoder.encode(bytes);
	}
	/**
	 * 对象字段 转化成 字节
	 * @param field
	 * @return
	 */
	public byte[] fieldToBytes(Object field) {
		return valueToBytes(field);
	}
	/**
	 * 字节 转化成对象
	 * @param bytes
	 * @return
	 */
	public Object fieldFromBytes(byte[] bytes) {
		return valueFromBytes(bytes);
	}
	/**
	 *  值对象 转化 成字节
	 * @param value
	 * @return
	 */
	public byte[] valueToBytes(Object value) {
		ObjectOutputStream objectOut = null;
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			objectOut = new ObjectOutputStream(bytesOut);
			objectOut.writeObject(value);
			objectOut.flush();
			return bytesOut.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (objectOut != null)
				try {
					objectOut.close();
				} catch (Exception e) {
					
				}
		}
	}
	/**
	 *  字节值 转化 成对象
	 * @param bytes
	 * @return
	 */
	public Object valueFromBytes(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return null;

		ObjectInputStream objectInput = null;
		try {
			ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes);
			objectInput = new ObjectInputStream(bytesInput);
			return objectInput.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (objectInput != null)
				try {
					objectInput.close();
				} catch (Exception e) { }
		}
	}
}
