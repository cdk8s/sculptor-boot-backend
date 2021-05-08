/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：MeterConfig.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Slf4j
@Component
public class MeterConfig implements MeterRegistryCustomizer {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.datasource.hikari.pool-name:/myHikariPoolName}")
	private String hikariPoolName;

	@Override
	public void customize(MeterRegistry registry) {
		registry.config().commonTags("application", applicationName);
		registry.config().commonTags("hikaricp", hikariPoolName);
		try {
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			log.debug("设置 metrics instance-id 为 ip:" + hostAddress);
			registry.config().commonTags("instance", hostAddress);
		} catch (UnknownHostException e) {
			String uuid = UUID.randomUUID().toString();
			registry.config().commonTags("instance", uuid);
			log.error("设置 metrics instance-id 为 uuid:" + uuid, e);
		}
	}
}
