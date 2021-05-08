/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WxpayParamProperties.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sculptor.pay.wxpay")
public class WxpayParamProperties {

	private String appId;
	private String appSecret;
	private String mchId;
	private String partnerKey;
	private String callbackUrl;

	private String wapUrl;
	private String wapName;

}
