/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinCreateQrResponse.java
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
public class WeixinCreateQrResponse implements Serializable {

	private static final long serialVersionUID = -1L;

	private String ticket;
	private int expire_seconds;
	private String url;
}
