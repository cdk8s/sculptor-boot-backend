/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginLogEvent.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginLogEvent extends ApplicationEvent {

	private SysLoginLogCreateServiceBO serviceBO;

	public LoginLogEvent(Object source, SysLoginLogCreateServiceBO serviceBO) {
		super(source);
		this.serviceBO = serviceBO;
	}
}
