/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RedissonParamProperties.java
 * 项目名称：sculptor-boot-starter-redisson
 * 项目描述：sculptor-boot-starter-redisson
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.redisson.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sculptor.redisson")
public class RedissonParamProperties {


	private String redisHost;
	private String redisPort;
	private int redisDatabase;
	private String redisPassword;

}
