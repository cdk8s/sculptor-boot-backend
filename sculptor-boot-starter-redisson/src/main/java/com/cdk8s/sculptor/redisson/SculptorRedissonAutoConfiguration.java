/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SculptorRedissonAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-redisson
 * 项目描述：sculptor-boot-starter-redisson
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.redisson;

import com.cdk8s.sculptor.redisson.aop.norepeat.NoRepeatSubmitToRedissonAspect;
import com.cdk8s.sculptor.redisson.properties.RedissonParamProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@ComponentScan({"com.cdk8s.sculptor.redisson.aop"})
@ConditionalOnClass({RedissonClient.class})
@EnableConfigurationProperties(RedissonParamProperties.class)
public class SculptorRedissonAutoConfiguration {

	@Autowired
	private RedissonParamProperties redissonParamProperties;

	@Bean
	@ConditionalOnMissingBean(RedissonClient.class)
	public RedissonClient redisson() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson/redisson-single.yml").getURL());
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress("redis://" + redissonParamProperties.getRedisHost() + ":" + redissonParamProperties.getRedisPort());
		singleServerConfig.setPassword(redissonParamProperties.getRedisPassword());
		singleServerConfig.setDatabase(redissonParamProperties.getRedisDatabase());
		return Redisson.create(config);
	}
}
