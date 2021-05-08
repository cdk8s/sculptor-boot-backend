/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SmsParamProperties.java
 * 项目名称：sculptor-boot-starter-sms
 * 项目描述：sculptor-boot-starter-sms
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.sms.properties;

import com.cdk8s.sculptor.enums.SmsTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sculptor.sms")
public class SmsParamProperties {


	private SmsTypeEnum smsTypeEnum;

	private String aliyunLoginSignName;
	private String aliyunAccessKeyId;
	private String aliyunAccessSecret;
	private String aliyunSmsDomain = "dysmsapi.aliyuncs.com";
	private String aliyunSmsVersion = "2017-05-25";
	private String aliyunRegionId = "cn-hangzhou";
}
