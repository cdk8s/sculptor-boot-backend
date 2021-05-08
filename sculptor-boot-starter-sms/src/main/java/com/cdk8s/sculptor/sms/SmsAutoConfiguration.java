/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SmsAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-sms
 * 项目描述：sculptor-boot-starter-sms
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.sms;

import com.cdk8s.sculptor.sms.properties.SmsParamProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.cdk8s.sculptor.sms"})
@EnableConfigurationProperties(SmsParamProperties.class)
public class SmsAutoConfiguration {

	@Autowired
	private SmsParamProperties smsParamProperties;


}
