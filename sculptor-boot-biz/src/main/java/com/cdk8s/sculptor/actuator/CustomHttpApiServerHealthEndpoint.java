/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CustomHttpApiServerHealthEndpoint.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.actuator;

import com.cdk8s.sculptor.properties.InitParamProperties;
import com.cdk8s.sculptor.util.okhttp.OkHttpResponse;
import com.cdk8s.sculptor.util.okhttp.OkHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 验证对外网络
 */
@Component
public class CustomHttpApiServerHealthEndpoint extends AbstractHealthIndicator {

	@Autowired
	private OkHttpService okHttpService;

	@Autowired
	private InitParamProperties initParamProperties;

	//======================================================

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		if (initParamProperties.getStartHttpHealthEnabled()) {
			OkHttpResponse okHttpResponse = okHttpService.get("https://www.taobao.com");
			if (okHttpResponse.getStatus() == HttpStatus.OK.value()) {
				builder.up();
			} else {
				builder.down();
			}
		}
	}
}
