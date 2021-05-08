/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthTokenParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthTokenParam extends OauthClientParam {
	private String grantType;

	private String code;
	private String refreshToken;
	private String redirectUri;

	private String username;
	private String password;

	private Long userId;

	private String deviceId;
	private String validateCode;
	private Boolean boolIsRememberMe;
}
