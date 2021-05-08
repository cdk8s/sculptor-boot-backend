/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginTokenRefreshEvent.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.event.LoginTokenRefreshBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginTokenRefreshEvent extends ApplicationEvent {

	private LoginTokenRefreshBO loginTokenRefreshBO;

	public LoginTokenRefreshEvent(Object source, LoginTokenRefreshBO loginTokenRefreshBO) {
		super(source);
		this.loginTokenRefreshBO = loginTokenRefreshBO;
	}
}
