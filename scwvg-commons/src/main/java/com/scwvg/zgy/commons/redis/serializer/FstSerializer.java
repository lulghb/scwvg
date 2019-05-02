package com.scwvg.zgy.commons.redis.serializer;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.springframework.stereotype.Service;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @project: baigle-redis
 * @author: chen.baihoo
 * @date: 2019/1/19 18:38
 * @Description: TODO
 * version 0.1
 */
@Service("fstSerializer")
public class FstSerializer implements ISerializer {
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
		FSTObjectOutput fstOut = null;
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			fstOut = new FSTObjectOutput(bytesOut);
			fstOut.writeObject(value);
			fstOut.flush();
			return bytesOut.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fstOut != null)
				try {
					fstOut.close();
				} catch (IOException e) {
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

		FSTObjectInput fstInput = null;
		try {
			fstInput = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return fstInput.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fstInput != null)
				try {
					fstInput.close();
				} catch (IOException e) {
				}
		}
	}
}
