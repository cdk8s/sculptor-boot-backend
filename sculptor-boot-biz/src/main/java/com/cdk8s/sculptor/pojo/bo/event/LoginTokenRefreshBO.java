/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginTokenRefreshBO.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.event;

import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class LoginTokenRefreshBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private TkeyToken tkeyToken;

}
