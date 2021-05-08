/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：MiniProgramCodeToSessionResponse.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.pojo.response;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MiniProgramCodeToSessionResponse implements Serializable {

	private static final long serialVersionUID = -1L;

	private String openid;
	private String session_key;
	private String unionid;
	private String errcode;
	private String errmsg;
}
