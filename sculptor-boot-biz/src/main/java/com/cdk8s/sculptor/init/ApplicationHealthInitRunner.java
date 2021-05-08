/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ApplicationHealthInitRunner.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.actuator.CustomHttpApiServerHealthEndpoint;
import com.cdk8s.sculptor.properties.InitParamProperties;
import com.cdk8s.sculptor.util.DatetimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(5)
@Component
public class ApplicationHealthInitRunner implements ApplicationRunner {

	// 系统上线时间
	public static final String upDateTime = DatetimeUtil.currentDateTime();

	@Autowired
	private CustomHttpApiServerHealthEndpoint customHttpApiServerHealthEndpoint;

	// @Autowired
	// private ReactiveHealthIndicator redisHealthIndicator;
	//
	// @Autowired
	// private HealthIndicator dbHealthIndicator;

	// @Autowired
	// private HealthIndicator mongoHealthIndicator;

	@Autowired
	private InitParamProperties initParamProperties;

	//=====================================业务处理 start=====================================

	@Override
	public void run(ApplicationArguments args) {

		if (initParamProperties.getStartHttpHealthEnabled()) {
			http();
		}
		if (initParamProperties.getStartRedisHealthEnabled()) {
			redis();
		}
		if (initParamProperties.getStartDbHealthEnabled()) {
			db();
		}
		if (initParamProperties.getStartMongoHealthEnabled()) {
			mongo();
		}
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private void http() {
		Health customUPMSHealth = customHttpApiServerHealthEndpoint.health();
		if (customUPMSHealth.getStatus().equals(Status.DOWN)) {
			log.error("启动请求 http 接口失败");
		}
	}

	private void redis() {
		// Mono<Health> redisHealth = redisHealthIndicator.health();
		// redisHealth.subscribe(h -> {
		// 	if (h.getStatus().equals(Status.DOWN)) {
		// 		log.error("启动连接 Redis 失败");
		// 	}
		// });
	}

	private void db() {
		// Health dbHealth = dbHealthIndicator.health();
		// if (dbHealth.getStatus().equals(Status.DOWN)) {
		// 	log.error("启动连接 DB 失败");
		// }
	}

	private void mongo() {
		// Health dbHealth = mongoHealthIndicator.health();
		// if (dbHealth.getStatus().equals(Status.DOWN)) {
		// 	log.error("启动连接 MongoDB 失败");
		// }
	}

	//=====================================私有方法  end=====================================

}
