/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthClientCodeRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthClientCodeRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "code 不能为空")
	private String code;

	@NotBlank(message = "redirectUri 不能为空")
	private String redirectUri;


}
