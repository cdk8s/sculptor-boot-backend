/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthToken.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OauthToken implements Serializable {

	private static final long serialVersionUID = -1L;

	private String accessToken;
	private String tokenType;
	private Integer expiresIn;

	private String refreshToken;

}
