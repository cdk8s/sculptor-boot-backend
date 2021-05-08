/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinProperties.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "sculptor.weixin")
public class WeixinProperties {

	private String appId;
	private String appSecret;

	private String miniProgramAppId;
	private String miniProgramAppSecret;

	private String websiteAppId;
	private String websiteAppSecret;
	private String websiteRedirectUri;



}
