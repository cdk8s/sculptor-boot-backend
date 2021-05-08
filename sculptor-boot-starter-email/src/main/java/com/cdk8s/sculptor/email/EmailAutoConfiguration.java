/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EmailAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-email
 * 项目描述：sculptor-boot-starter-email
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.email;

import com.cdk8s.sculptor.email.properties.EmailParamProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.cdk8s.sculptor.email"})
@EnableConfigurationProperties(EmailParamProperties.class)
public class EmailAutoConfiguration {

	@Autowired
	private EmailParamProperties emailParamProperties;


}
