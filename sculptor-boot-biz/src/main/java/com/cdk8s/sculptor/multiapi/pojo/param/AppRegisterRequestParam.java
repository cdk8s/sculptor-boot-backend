/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppRegisterRequestParam.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi.pojo.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppRegisterRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "登录名不能为空")
	private String username;

	@NotBlank(message = "密码不能为空")
	private String password;

	@NotBlank(message = "设备ID 不能为空")
	private String deviceId;

	@NotBlank(message = "验证码不能为空")
	private String validateCode;
}
