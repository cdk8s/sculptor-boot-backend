/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin;


import com.cdk8s.sculptor.weixin.properties.WeixinProperties;
import com.cdk8s.sculptor.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.cdk8s.sculptor.weixin"})
@EnableConfigurationProperties(WeixinProperties.class)
public class WeixinAutoConfiguration {

	@Autowired
	private WeixinProperties weixinProperties;


}
