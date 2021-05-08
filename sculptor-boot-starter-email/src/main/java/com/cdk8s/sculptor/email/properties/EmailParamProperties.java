/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EmailParamProperties.java
 * 项目名称：sculptor-boot-starter-email
 * 项目描述：sculptor-boot-starter-email
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.email.properties;

import com.cdk8s.sculptor.enums.EmailTypeEnum;
import com.cdk8s.sculptor.enums.SmsTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sculptor.email")
public class EmailParamProperties {

	private EmailTypeEnum emailTypeEnum;


	private String aliyunRegionId = "cn-hangzhou";
	private String aliyunAccessKeyId;
	private String aliyunAccessSecret;
	private String aliyunDefaultSendUserName;
	private String aliyunSendDomain;
}
