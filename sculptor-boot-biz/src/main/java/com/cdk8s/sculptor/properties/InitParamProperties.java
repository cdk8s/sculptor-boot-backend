/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：InitParamProperties.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "custom.properties.init")
public class InitParamProperties {

	private Boolean startRemoveOldAllCacheEnabled = false;
	private Boolean startRemoveOldBizCacheEnabled = false;
	private List<String> bizCacheNames;

	private Boolean startTestDataInitEnabled = false;

	private Boolean startDbHealthEnabled = true;
	private Boolean startRedisHealthEnabled = true;
	private Boolean startMongoHealthEnabled = true;
	private Boolean startHttpHealthEnabled = false;
}
