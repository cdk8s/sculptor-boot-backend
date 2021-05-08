/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：JobLogListener.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.listener;

import com.cdk8s.sculptor.eventlistener.event.JobLogEvent;
import com.cdk8s.sculptor.pojo.bo.service.sysjoblog.SysJobLogCreateServiceBO;
import com.cdk8s.sculptor.service.SysJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JobLogListener {

	@Autowired
	private SysJobLogService sysJobLogService;

	// =====================================业务 start=====================================

	@Async
	@EventListener(JobLogEvent.class)
	public void service(JobLogEvent event) {
		SysJobLogCreateServiceBO serviceBO = event.getServiceBO();
		sysJobLogService.create(serviceBO);
	}

	// =====================================业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
