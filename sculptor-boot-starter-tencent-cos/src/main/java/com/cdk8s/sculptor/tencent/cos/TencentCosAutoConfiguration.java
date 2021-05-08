/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TencentCosAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-tencent-cos
 * 项目描述：sculptor-boot-starter-tencent-cos
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.tencent.cos;

import com.cdk8s.sculptor.tencent.cos.properties.TencentCosParamProperties;
import com.cdk8s.sculptor.tencent.cos.service.TencentCosService;
import com.qcloud.cos.COSClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({COSClient.class})
@EnableConfigurationProperties({TencentCosParamProperties.class})
public class TencentCosAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(TencentCosService.class)
	public TencentCosService tencentCosService() {
		return new TencentCosService();
	}

}
