package com.scwvg.sys.commons.redis;

import com.scwvg.sys.commons.redis.serializer.ISerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;


/**
 * @project: redis缓存
 * @author: chen.baihoo
 * @date: 2019/1/19 18:24
 * @Description: TODO
 * version 0.1
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class Redis {

	// redis 模板对象
	@Resource
	private RedisTemplate redisTemplate;

	// 自定义的序列化类
	@Autowired
	@Qualifier("fstSerializer")
	private ISerializer serializer;
	// 可自定义 key 命名策略
	private IKeyNamingPolicy keyNamingPolicy = IKeyNamingPolicy.defaultKeyNamingPolicy;


	/**
	 *  防止线程并发，同步获取 redis 连接
	 * @return
	 */
	private  synchronized RedisConnection getRedisConnection() {
		return redisTemplate.getRequiredConnectionFactory().getConnection();
	}

	/**
	 * 对象 key 转化成字节
	 * @param key
	 * @return
	 */
	private byte[] keyToBytes(Object key) {
		String keyStr = keyNamingPolicy.getKeyName(key);
		return serializer.keyToBytes(keyStr);
	}

	/**
	 *  对象 值 value 转化成字节
	 * @param value
	 * @return
	 */
	private byte[] valueToBytes(Object value) {
		return serializer.valueToBytes(value);
	}

	/**
	 *  字节 转化对象
	 * @param bytes
	 * @return
	 */
	private Object valueFromBytes(byte[] bytes) {
		return serializer.valueFromBytes(bytes);
	}

	/**
	 *	多键 key 对象转化成 字节二维数组
	 * @param keys
	 * @return
	 */
	private byte[][] keysToBytesArray(Object... keys) {
		byte[][] result = new byte[keys.length][];
		for (int i=0; i<result.length; i++)
			result[i] = keyToBytes(keys[i]);
		return result;
	}

	/**
	 *	字节列表 转化成 对象列表
	 * @param data
	 * @return
	 */
	private List<Object> valueListFromBytesList(List<byte[]> data) {
		List<Object> result = new ArrayList<Object>();
		data.stream().forEach(e->{
			result.add(valueFromBytes(e));
		});
		return result;
	}

	/**
	 * 字段对象 转化成 字节
	 * @param field
	 * @return
	 */
	private byte[] fieldToBytes(Object field) {
		return serializer.fieldToBytes(field);
	}

	/**
	 * 多字段对象 转化成 字节数组
	 * @param fieldsArray
	 * @return
	 */
	private byte[][] fieldsToBytesArray(Object... fieldsArray) {
		byte[][] data = new byte[fieldsArray.length][];
		for (int i=0; i<data.length; i++)
			data[i] = fieldToBytes(fieldsArray[i]);
		return data;
	}

	/**
	 * 字节 转化成 字段对象
	 * @param bytes
	 * @return
	 */
	private Object fieldFromBytes(byte[] bytes) {
		return serializer.fieldFromBytes(bytes);
	}

	/**
	 * 字段set集 转化成 字节set集
	 * @param data
	 * @param result
	 */
	private void fieldSetFromBytesSet(Set<byte[]> data, Set<Object> result) {
		data.stream().forEach(e->{
			result.add(fieldFromBytes(e));
		});
	}

	/**
	 *  数组值对象 转化成 字节数组
	 * @param valuesArray
	 * @return
	 */
	private byte[][] valuesToBytesArray(Object... valuesArray) {
		byte[][] data = new byte[valuesArray.length][];
		for (int i=0; i<data.length; i++)
			data[i] = valueToBytes(valuesArray[i]);
		return data;
	}

	/**
	 * 字节值列表 转换 成对象列表
	 * @param data
	 * @return
	 */
	private List<Object> keyValueListFromBytesList(List<byte[]> data) {
		List<Object> result = new ArrayList<Object>();
		result.add(keyFromBytes(data.get(0)));
		result.add(valueFromBytes(data.get(1)));
		return result;
	}

	/**
	 * 字节 转化成 对象
	 * @param bytes
	 * @return
	 */
	private Object keyFromBytes(byte[] bytes) {
		return serializer.keyFromBytes(bytes);
	}

	/**
	 * 对象set集 转化成 字节set集
	 * @param data
	 * @param result
	 */
	private void valueSetFromBytesSet(Set<byte[]> data, Set<Object> result) {
		for (byte[] valueBytes : data) {
			result.add(valueFromBytes(valueBytes));
		}
	}

	/**
	 *		存放 key value 对到 redis 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
	 *	对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时，这个键
	 *	原有的 TTL 将被清除
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(Object key, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.set(keyToBytes(key), valueToBytes(value));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 存放 key value 对到 redis，并将 key 的生存时间设为 seconds (以秒为单位)。
	 * 如果 key 已经存在， SETEX 命令将覆写旧值。
	 */
	public boolean setex(Object key, int seconds, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.setEx(keyToBytes(key), seconds, valueToBytes(value));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回 key 所关联的 value 值 如果 key 不存在那么返回特殊值 nil 。
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.get(keyToBytes(key)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 删除给定的一个 key 不存在的 key 会被忽略。
	 */
	public Long del(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.del(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 删除给定的多个 key 不存在的 key 会被忽略。
	 */
	public Long del(Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.del(keysToBytesArray(keys));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 查找所有符合给定模式 pattern 的 key 。 KEYS * 匹配数据库中所有 key 。 KEYS h?llo 匹配 hello ，hallo 和 hxllo 等。
	 * KEYS h*llo 匹配 hllo 和 heeeeello 等。 KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。 特殊符号用 \ 隔开
	 */
	public Set<String> keys(String pattern) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<String> set = new HashSet<>();
			redisConnection.keys(keyToBytes(pattern)).stream().forEach(e->{
				set.add(valueFromBytes(e).toString());
			});
			return set;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 	同时设置一个或多个 key-value 对。 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的
	 * 	效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。MSET 是一个原子性(atomic)操作，所有
	 * 	给定 key 都会在同一时间内被设置，某些给定 key 被更新而另一些给定 key 没有改变的情况，不可能发生。
	 *
	 * <pre>
	 * 例子：
	 * Cache cache = RedisKit.use();			// 使用 Redis 的 cache
	 * cache.mset("k1", "v1", "k2", "v2");		// 放入多个 key value 键值对
	 * List list = cache.mget("k1", "k2");		// 利用多个键值得到上面代码放入的值
	 * </pre>
	 * @param keysValues
	 * @return
	 */
	public boolean mset(Object... keysValues) {
		if (keysValues.length % 2 != 0)
			throw new IllegalArgumentException(
					"wrong number of arguments for met, keysValues length can not be odd");
		RedisConnection redisConnection = getRedisConnection();
		try {
			byte[][] kv = new byte[keysValues.length][];

			Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();

			for (int i = 0; i < keysValues.length; i++) {
				// 0 / 2 = 0 .......0
				// 1 / 2 = 0.5 ....... 0 (不成立，虽然余数为0，但除数为小数点)
				// 2 / 2 = 2 ...... 0
				// 3 / 2 = 1 ...... 1
				// 4 / 2 = 2 ...... 0
				if (i % 2 == 0)
					map.put(keyToBytes(keysValues[i]),valueToBytes(keysValues[i+1]));
			}
			return redisConnection.mSet(map);
		} finally {
			redisConnection.close();
		}
	}
	/**
	 *  map 存放 key-value
	 * <pre>
	 * 例子：
	 * Cache cache = RedisKit.use();			// 使用 Redis 的 cache
	 * cache.mset(map);		// 放入多个 key value 键值对
	 * List list = cache.mget("k1", "k2");		// 利用多个键值得到上面代码放入的值
	 * </pre>
	 * @param map
	 * @return
	 */
	public boolean mset(Map<byte[], byte[]> map) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.mSet(map);
		} finally {
			redisConnection.close();
		}
	}
	/**
	 * 返回所有(一个或多个)给定 key 的值。 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命
	 * 令永不失败。
	 */
	@SuppressWarnings("rawtypes")
	public List mget(Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			byte[][] keysBytesArray = keysToBytesArray(keys);
			List<byte[]> data = redisConnection.mGet(keysBytesArray);
			return valueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 中储存的数字值减一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。如果值包含错误的类
	 * 型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在 64 位(bit)有符号数字表示之内。关于递增
	 * (increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令。
	 */
	public Long decr(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.decr(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 所储存的值减去减量 decrement 。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。 如
	 * 果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * 关于更多递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令。
	 */
	public Long decrBy(Object key, long longValue) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.decrBy(keyToBytes(key), longValue);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 中储存的数字值增一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。如果值包含错误的类
	 * 型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 */
	public Long incr(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.incr(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 所储存的值加上增量 increment 。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY
	 * 命令。 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在 64 位(bit)
	 * 有符号数字表示之内。关于递增(increment) / 递减(decrement)操作的更多信息，参见 INCR 命令。
	 */
	public Long incrBy(Object key, long longValue) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.incrBy(keyToBytes(key), longValue);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 检查给定 key 是否存在。
	 */
	public boolean exists(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.exists(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 从当前数据库中随机返回(不删除)一个 key 。
	 */
	public String randomKey() {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return keyFromBytes(redisConnection.randomKey()).toString();
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 改名为 newkey 。 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。 当 newkey 已经存在时，
	 * RENAME 命令将覆盖旧值。
	 */
	public void rename(Object oldkey, Object newkey) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.rename(keyToBytes(oldkey), keyToBytes(newkey));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将当前数据库的 key 移动到给定的数据库 db 当中。 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者
	 * key 不存在于当前数据库，那么 MOVE 没有任何效果。 因此，也可以利用这一特性，将 MOVE
	 * 当作锁(locking)原语(primitive)。
	 */
	public boolean move(Object key, int dbIndex) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.move(keyToBytes(key), dbIndex);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
	 */
	public void migrate(Object key,RedisNode target, int dbIndex, @Nullable RedisServerCommands.MigrateOption option,
						long timeout) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.migrate(keyToBytes(key),target,dbIndex,RedisServerCommands.MigrateOption.REPLACE ,timeout);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 切换到指定的数据库，数据库索引号 index 用数字值指定，以 0 作为起始索引值。 默认使用 0 号数据库。 注意：在 redisConnection
	 * 对象被关闭时，数据库又会重新被设置为初始值，所以本方法 select(...) 正常工作需要使用如下方式之一： 1：使用
	 * RedisInterceptor，在本线程内共享同一个 redisConnection 对象 2：使用 Redis.call(ICallback) 进行操作
	 * 3：自行获取 redisConnection 对象进行操作
	 */
	public void select(int databaseIndex) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.select(databaseIndex);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 在 Redis 中，带有生存时间的 key
	 * 被称为『易失的』(volatile)。
	 */
	public boolean expire(Object key, int seconds) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.expire(keyToBytes(key), seconds);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。不同在于 EXPIREAT 命令接受的时间参数是 UNIX
	 * 时间戳(unix timestamp)。
	 */
	public boolean expireAt(Object key, long unixTime) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.expireAt(keyToBytes(key), unixTime);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 这个命令和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像 EXPIRE 命令那样，以秒为单位。
	 */
	public boolean pexpire(Object key, long milliseconds) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.pExpire(keyToBytes(key), milliseconds);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 这个命令和 EXPIREAT 命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳，而不是像 EXPIREAT 那样，以秒为单位。
	 */
	public boolean pexpireAt(Object key, long millisecondsTimestamp) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.pExpireAt(keyToBytes(key), millisecondsTimestamp);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。 当 key 存在但不是字符串类型时，返回一个错误。
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSet(Object key, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.getSet(keyToBytes(key),
					valueToBytes(value)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
	 */
	public boolean persist(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.persist(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回 key 所储存的值的类型。
	 */
	public String type(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			DataType dataType = redisConnection.type(keyToBytes(key));
			return dataType.code();
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
	 */
	public Long ttl(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.ttl(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 这个命令类似于 TTL 命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像 TTL 命令那样，以秒为单位。
	 */
	public Long pttl(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.pTtl(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 对象被引用的数量
	 */
	public Long objectRefcount(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.refcount(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 对象没有被访问的空闲时间
	 */
	public Long objectIdletime(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.idletime(keyToBytes(key)).getSeconds();
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
	 * field 已经存在于哈希表中，旧值将被覆盖。
	 */
	public boolean hset(Object key, Object field, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hSet(keyToBytes(key), fieldToBytes(field),
					valueToBytes(value));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 此命令会覆盖哈希表中已存在的域。 如果 key
	 * 不存在，一个空哈希表被创建并执行 HMSET 操作。
	 */
	public void hmset(Object key, Map<Object, Object> hash) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Map<byte[], byte[]> para = new HashMap<byte[], byte[]>();
			for (Entry<Object, Object> e : hash.entrySet())
				para.put(fieldToBytes(e.getKey()), valueToBytes(e.getValue()));
			redisConnection.hMSet(keyToBytes(key), para);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 */
	@SuppressWarnings("unchecked")
	public <T> T hget(Object key, Object field) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.hGet(keyToBytes(key),
					fieldToBytes(field)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 nil 值。 因为不存在的 key
	 * 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 */
	@SuppressWarnings("rawtypes")
	public List hmget(Object key, Object... fields) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.hMGet(keyToBytes(key),
					fieldsToBytesArray(fields));
			return valueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 */
	public Long hdel(Object key, Object... fields) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hDel(keyToBytes(key), fieldsToBytesArray(fields));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 */
	public boolean hexists(Object key, Object field) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hExists(keyToBytes(key), fieldToBytes(field));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中，所有的域和值。 在返回值里，紧跟每个域名(field
	 * name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
	 */
	@SuppressWarnings("rawtypes")
	public Map hgetAll(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Map<byte[], byte[]> data = redisConnection.hGetAll(keyToBytes(key));
			Map<Object, Object> result = new HashMap<Object, Object>();
			for (Entry<byte[], byte[]> e : data.entrySet())
				result.put(fieldFromBytes(e.getKey()),
						valueFromBytes(e.getValue()));
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中所有域的值。
	 */
	@SuppressWarnings("rawtypes")
	public List hvals(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.hVals(keyToBytes(key));
			return valueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中的所有域。 底层实现此方法取名为 hfields 更为合适，在此仅为与底层保持一致
	 */
	public Set<Object> hkeys(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> fieldSet = redisConnection.hKeys(keyToBytes(key));
			Set<Object> result = new HashSet<Object>();
			fieldSetFromBytesSet(fieldSet, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回哈希表 key 中域的数量。
	 */
	public Long hlen(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hLen(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。 增量也可以为负数，相当于对给定域进行减法操作。 如果 key
	 * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 */
	public Long hincrBy(Object key, Object field, long value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hIncrBy(keyToBytes(key), fieldToBytes(field), value);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 为哈希表 key 中的域 field 加上浮点数增量 increment 。 如果哈希表中没有域 field ，那么 HINCRBYFLOAT
	 * 会先将域 field 的值设为 0 ，然后再执行加法操作。 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域
	 * field ，最后再执行加法操作。 当以下任意一个条件发生时，返回一个错误： 1:域 field 的值不是字符串类型(因为 redis
	 * 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型） 2:域 field 当前的值或给定的增量 increment
	 * 不能解释(parse)为双精度浮点数(double precision floating point number) HINCRBYFLOAT
	 * 命令的详细功能和 INCRBYFLOAT 命令类似，请查看 INCRBYFLOAT 命令获取更多相关信息。
	 */
	public Double hincrByFloat(Object key, Object field, double value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.hIncrBy(keyToBytes(key), fieldToBytes(field),
					value);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回列表 key 中，下标为 index 的元素。 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0
	 * 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
	 * 表示列表的倒数第二个元素，以此类推。 如果 key 不是列表类型，返回一个错误。
	 */
	@SuppressWarnings("unchecked")
	/**
	 * 返回列表 key 中，下标为 index 的元素。
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，
	 * 以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 如果 key 不是列表类型，返回一个错误。
	 */
	public <T> T lindex(Object key, long index) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.lIndex(keyToBytes(key), index));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 获取记数器的值
	 */
	public Long getCounter(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			String ret = valueFromBytes(redisConnection.get(keyToBytes(key))).toString();
			return ret != null ? Long.parseLong(ret) : null;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回列表 key 的长度。 如果 key 不存在，则 key 被解释为一个空列表，返回 0 . 如果 key 不是列表类型，返回一个错误。
	 */
	public Long llen(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.lLen(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除并返回列表 key 的头元素。
	 */
	@SuppressWarnings("unchecked")
	public <T> T lpop(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.lPop(keyToBytes(key)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
	 * 比如说， 对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ， 这等同于原子性地执行 LPUSH
	 * mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。 如果 key 不存在，一个空列表会被创建并执行
	 * LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
	 */
	public Long lpush(Object key, Object... values) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.lPush(keyToBytes(key), valuesToBytesArray(values));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value 。 当 index 参数超出范围，或对一个空列表( key 不存在)进行
	 * LSET 时，返回一个错误。 关于列表下标的更多信息，请参考 LINDEX 命令。
	 */
	public void lset(Object key, long index, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.lSet(keyToBytes(key), index, valueToBytes(value));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。 count 的值可以是以下几种： count > 0 :
	 * 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 count < 0 : 从表尾开始向表头搜索，移除与 value
	 * 相等的元素，数量为 count 的绝对值。 count = 0 : 移除表中所有与 value 相等的值。
	 */
	public Long lrem(Object key, long count, Object value) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.lRem(keyToBytes(key), count, valueToBytes(value));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 下标(index)参数 start 和 stop 都以 0
	 * 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素，
	 * -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * <pre>
	 * 例子：
	 * 获取 list 中所有数据：cache.lrange(listKey, 0, -1);
	 * 获取 list 中下标 1 到 3 的数据： cache.lrange(listKey, 1, 3);
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")
	public List lrange(Object key, long start, long end) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.lRange(keyToBytes(key), start, end);
			if (data != null) {
				return valueListFromBytesList(data);
			} else {
				return new ArrayList<byte[]>(0);
			}
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 举个例子，执行命令 LTRIM list
	 * 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。 下标(index)参数 start 和 stop 都以 0
	 * 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素，
	 * -2 表示列表的倒数第二个元素，以此类推。 当 key 不是列表类型时，返回一个错误。
	 */
	public void ltrim(Object key, long start, long end) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.lTrim(keyToBytes(key), start, end);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除并返回列表 key 的尾元素。
	 */
	@SuppressWarnings("unchecked")
	public <T> T rpop(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.rPop(keyToBytes(key)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作： 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。 将
	 * source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
	 */
	@SuppressWarnings("unchecked")
	public <T> T rpoplpush(Object srcKey, Object dstKey) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.rPopLPush(keyToBytes(srcKey),
					keyToBytes(dstKey)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 如果有多个 value 值，那么各个 value
	 * 值按从左到右的顺序依次插入到表尾：比如 对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c ，
	 * 等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。 如果 key
	 * 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
	 */
	public Long rpush(Object key, Object... values) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.rPush(keyToBytes(key), valuesToBytesArray(values));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * BLPOP 是列表的阻塞式(blocking)弹出原语。 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被
	 * BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 当给定多个 key 参数时，按参数 key
	 * 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
	 * 
	 * 参考：http://redisdoc.com/list/blpop.html 命令行：BLPOP key [key ...] timeout
	 */
	@SuppressWarnings("rawtypes")
	public List blpop(int timeout, Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.bLPop(timeout, keysToBytesArray(keys));
			return keyValueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * BRPOP 是列表的阻塞式(blocking)弹出原语。 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被
	 * BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 当给定多个 key 参数时，按参数 key
	 * 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。 关于阻塞操作的更多信息，请查看 BLPOP 命令， BRPOP 除了弹出元素的位置和
	 * BLPOP 不同之外，其他表现一致。
	 * 
	 * 参考：http://redisdoc.com/list/brpop.html 命令行：BRPOP key [key ...] timeout
	 */
	@SuppressWarnings("rawtypes")
	public List brpop(int timeout, Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.bRPop(timeout, keysToBytesArray(keys));
			return keyValueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 使用客户端向 Redis 服务器发送一个 PING ，如果服务器运作正常的话，会返回一个 PONG 。
	 * 通常用于测试与服务器的连接是否仍然生效，或者用于测量延迟值。
	 */
	public String ping() {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.ping();
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 当 key 不是集合类型时，返回一个错误。
	 */
	public Long sadd(Object key, Object... members) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.sAdd(keyToBytes(key), valuesToBytesArray(members));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回集合 key 的基数(集合中元素的数量)。
	 */
	public Long scard(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.sCard(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除并返回集合中的一个随机元素。 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
	 */
	@SuppressWarnings("unchecked")
	public <T> T spop(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.sPop(keyToBytes(key)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回集合 key 中的所有成员。 不存在的 key 被视为空集合。
	 */
	@SuppressWarnings("rawtypes")
	public Set smembers(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.sMembers(keyToBytes(key));
			Set<Object> result = new HashSet<Object>();
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 判断 member 元素是否集合 key 的成员。
	 */
	public boolean sismember(Object key, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.sIsMember(keyToBytes(key), valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回多个集合的交集，多个集合由 keys 指定
	 */
	@SuppressWarnings("rawtypes")
	public Set sinter(Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.sInter(keysToBytesArray(keys));
			Set<Object> result = new HashSet<Object>();
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回集合中的一个随机元素。
	 */
	@SuppressWarnings("unchecked")
	public <T> T srandmember(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return (T) valueFromBytes(redisConnection.sRandMember(keyToBytes(key)));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回集合中的 count 个随机元素。 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数： 如果
	 * count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。 如果 count
	 * 大于等于集合基数，那么返回整个集合。 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count
	 * 的绝对值。 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER
	 * 则仅仅返回随机元素，而不对集合进行任何改动。
	 */
	@SuppressWarnings("rawtypes")
	public List srandmember(Object key, int count) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			List<byte[]> data = redisConnection.sRandMember(keyToBytes(key), count);
			return valueListFromBytesList(data);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
	 */
	public Long srem(Object key, Object... members) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.sRem(keyToBytes(key), valuesToBytesArray(members));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回多个集合的并集，多个集合由 keys 指定 不存在的 key 被视为空集。
	 */
	@SuppressWarnings("rawtypes")
	public Set sunion(Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.sUnion(keysToBytesArray(keys));
			Set<Object> result = new HashSet<Object>();
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。 不存在的 key 被视为空集。
	 */
	@SuppressWarnings("rawtypes")
	public Set sdiff(Object... keys) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.sDiff(keysToBytesArray(keys));
			Set<Object> result = new HashSet<Object>();
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。 如果某个 member 已经是有序集的成员，那么更新这个
	 * member 的 score 值， 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
	 */
	public boolean zadd(Object key, double score, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zAdd(keyToBytes(key), score, valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	public Long zadd(Object key, Map<Object, Double> scoreMembers) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<RedisZSetCommands.Tuple> tuples = new HashSet<RedisZSetCommands.Tuple>();
			Map<byte[], Double> para = new HashMap<byte[], Double>();
			scoreMembers.entrySet().stream().forEach(e->{
				byte[] bytes =  keyToBytes(e.getKey());
				Double value = e.getValue();
				tuples.add(new RedisZSetCommands.Tuple(){
					@Override
					public byte[] getValue() {
						return bytes;
					}
					@Override
					public Double getScore() {
						return value;
					}
					@Override
					// 升序排序
					public int compareTo(Double o) {
						return o>value?1:0;
					}
				});
			});
			return redisConnection.zAdd(keyToBytes(key),tuples);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 的基数。
	 */
	public Long zcard(Object key) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zCard(keyToBytes(key));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。 关于参数
	 * min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令。
	 */
	public Long zcount(Object key, double min, double max) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zCount(keyToBytes(key), min, max);
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
	 */
	public Double zincrby(Object key, double score, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zIncrBy(keyToBytes(key), score, valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递增(从小到大)来排序。 具有相同 score
	 * 值的成员按字典序(lexicographical order )来排列。 如果你需要成员按 score 值递减(从大到小)来排列，请使用
	 * ZREVRANGE 命令。
	 */
	@SuppressWarnings("rawtypes")
	public Set zrange(Object key, long start, long end) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.zRange(keyToBytes(key), start, end);
			Set<Object> result = new LinkedHashSet<Object>(); // 有序集合必须
																// LinkedHashSet
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递减(从大到小)来排列。 具有相同 score
	 * 值的成员按字典序的逆序(reverse lexicographical order)排列。 除了成员按 score 值递减的次序排列这一点外，
	 * ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
	 */
	@SuppressWarnings("rawtypes")
	public Set zrevrange(Object key, long start, long end) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.zRevRange(keyToBytes(key), start, end);
			Set<Object> result = new LinkedHashSet<Object>(); // 有序集合必须
																// LinkedHashSet
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。 有序集成员按 score
	 * 值递增(从小到大)次序排列。
	 */
	@SuppressWarnings("rawtypes")
	public Set zrangeByScore(Object key, double min, double max) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			Set<byte[]> data = redisConnection.zRangeByScore(keyToBytes(key), min, max);
			Set<Object> result = new LinkedHashSet<Object>(); // 有序集合必须
																// LinkedHashSet
			valueSetFromBytesSet(data, result);
			return result;
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。 排名以 0 为底，也就是说，
	 * score 值最小的成员排名为 0 。 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
	 */
	public Long zrank(Object key, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zRank(keyToBytes(key), valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。 排名以 0 为底，也就是说， score
	 * 值最大的成员排名为 0 。 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
	 */
	public Long zrevrank(Object key, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zRevRank(keyToBytes(key), valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。 当 key 存在但不是有序集类型时，返回一个错误。
	 */
	public Long zrem(Object key, Object... members) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zRem(keyToBytes(key), valuesToBytesArray(members));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 返回有序集 key 中，成员 member 的 score 值。 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回
	 * nil 。
	 */
	public Double zscore(Object key, Object member) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			return redisConnection.zScore(keyToBytes(key), valueToBytes(member));
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 删除当前 db 所有数据
	 */
	public void flushDB() {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.flushDb();
		} finally {
			redisConnection.close();
		}
	}

	/**
	 * 删除所有 db 的所有数据
	 */
	public void flushAll() {
		RedisConnection redisConnection = getRedisConnection();
		try {
			redisConnection.flushAll();
		} finally {
			redisConnection.close();
		}
	}
}
