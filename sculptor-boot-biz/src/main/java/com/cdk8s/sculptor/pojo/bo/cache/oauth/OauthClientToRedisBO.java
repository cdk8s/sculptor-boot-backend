/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthClientToRedisBO.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.cache.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class OauthClientToRedisBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String clientName;
	private String clientId;
	private String clientSecret;
	private String clientUrl;
	private String clientDesc;
	private String logoUrl;

}
