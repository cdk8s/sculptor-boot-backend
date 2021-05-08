/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：StringRedisService.java
 * 项目名称：sculptor-boot-common-redis
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.redis;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 限制 key 只能为 String
 *
 * @param <String>
 * @param <V>
 */
@Service
public class StringRedisService<String, V> {

	@Autowired
	private RedisTemplate redisTemplate;

	//=====================================common start=====================================


	public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
		//设置过期时间
		return redisTemplate.expire(key, timeout, timeUnit);
	}


	public Boolean expireAt(String key, Date date) {
		return redisTemplate.expireAt(key, date);
	}


	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}


	public void deleteByKeys(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 异步删除，redis 4 之后特性
	 */
	public Boolean unlink(String key) {
		return redisTemplate.unlink(key);
	}

	/**
	 * 异步删除，redis 4 之后特性
	 */
	public void unlink(List<String> keys) {
		redisTemplate.unlink(keys);
	}


	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}


	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}


	public Boolean move(String key, int dbIndex) {
		//将当前数据库的 key 移动到给定的数据库 db 当中
		return redisTemplate.move(key, dbIndex);
	}


	public Boolean persist(String key) {
		//移除 key 的过期时间，key 将持久保持
		return redisTemplate.persist(key);
	}


	public Long getExpire(String key) {
		//返回 key 的剩余的过期时间
		return redisTemplate.getExpire(key);
	}


	public Long getExpire(String key, TimeUnit unit) {
		//返回 key 的剩余的过期时间
		return redisTemplate.getExpire(key, unit);
	}


	public V randomKey() {
		//从当前数据库中随机返回一个 key
		return (V) redisTemplate.randomKey();
	}


	public void rename(String oldKey, String newKey) {
		//修改 key 的名称
		redisTemplate.rename(oldKey, newKey);
	}


	public Boolean renameIfAbsent(String oldKey, String newKey) {
		//仅当 newkey 不存在时，将 oldKey 改名为 newkey
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}


	//=====================================common end=====================================

	//=====================================key value start=====================================


	public boolean setIfAbsent(String key, V value) {
		//只有在 key 不存在时设置 key 的值（之前已经存在返回false,不存在返回true）
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	// 没有过期时间，过期时间是 -1
	public void set(String key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, V value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}


	public void set(String key, V value, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 避免穿透，设置一分钟时间差
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 * @param randomFlag
	 */

	public void set(String key, V value, long timeout, boolean randomFlag) {
		int nextInt = RandomUtils.nextInt(1, 60);
		redisTemplate.opsForValue().set(key, value, timeout + nextInt);
	}


	public void multiSet(Map<String, V> maps) {
		//批量添加
		redisTemplate.opsForValue().multiSet(maps);
	}


	public boolean multiSetIfAbsent(Map<String, V> maps) {
		// 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在（之前已经存在返回false,不存在返回true）
		return redisTemplate.opsForValue().multiSetIfAbsent(maps);
	}


	public V get(String key) {
		ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}


	public List<V> multiGet(Collection<String> keys) {
		ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
		return valueOperations.multiGet(keys);
	}


	//=====================================key value  end=====================================


}
