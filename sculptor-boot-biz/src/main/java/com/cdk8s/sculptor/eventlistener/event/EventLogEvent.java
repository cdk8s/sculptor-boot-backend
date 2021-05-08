/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EventLogEvent.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventLogEvent extends ApplicationEvent {

	private SysEventLogCreateServiceBO serviceBO;

	public EventLogEvent(Object source, SysEventLogCreateServiceBO serviceBO) {
		super(source);
		this.serviceBO = serviceBO;
	}
}
