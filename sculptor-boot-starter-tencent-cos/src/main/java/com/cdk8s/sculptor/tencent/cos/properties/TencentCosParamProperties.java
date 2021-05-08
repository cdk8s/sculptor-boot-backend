/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TencentCosParamProperties.java
 * 项目名称：sculptor-boot-starter-tencent-cos
 * 项目描述：sculptor-boot-starter-tencent-cos
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.tencent.cos.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sculptor.tencent.cos")
public class TencentCosParamProperties {

	private String bucketName;
	private String region;

	private String secretId;
	private String secretKey;


}
