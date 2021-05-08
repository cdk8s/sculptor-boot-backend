/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RedisService.java
 * 项目名称：sculptor-boot-common-redis
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	//=====================================common start=====================================


	public void flushDb() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}


	public void flushAll() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
	}


	public Properties info() {
		return redisTemplate.getConnectionFactory().getConnection().info();
	}

	//=====================================common end=====================================


}
