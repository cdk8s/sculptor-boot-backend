/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QrAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-qr
 * 项目描述：sculptor-boot-starter-qr
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.qr;

import com.cdk8s.sculptor.qr.service.QrGenerateService;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({SimpleQrcodeGenerator.class})
public class QrAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(QrGenerateService.class)
	public QrGenerateService qrGenerateService() {
		return new QrGenerateService();
	}

}
