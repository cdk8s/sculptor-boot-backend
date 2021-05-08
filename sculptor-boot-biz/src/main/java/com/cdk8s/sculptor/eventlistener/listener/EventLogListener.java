/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EventLogListener.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.listener;

import com.cdk8s.sculptor.eventlistener.event.EventLogEvent;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import com.cdk8s.sculptor.service.SysEventLogService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.ip.pojo.IpRegionBO;
import com.cdk8s.sculptor.util.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class EventLogListener {

	@Autowired
	private SysEventLogService sysEventLogService;

	@Autowired
	private IpService ipService;

	// =====================================业务 start=====================================

	@Async
	@EventListener(EventLogEvent.class)
	public void service(EventLogEvent event) {
		SysEventLogCreateServiceBO serviceBO = event.getServiceBO();

		String requestParam = serviceBO.getRequestParam();
		if (requestParam.length() > 500) {
			serviceBO.setRequestParam(StringUtil.substring(requestParam, 0, 500));
		}

		// 处理 IP 地区信息，如果 db 文件没有，则调用淘宝
		buildIpRegion(serviceBO);
		sysEventLogService.create(serviceBO);
	}

	// =====================================业务 end=====================================
	// =====================================私有方法 start=====================================

	private void buildIpRegion(SysEventLogCreateServiceBO serviceBO) {
		String ipAddress = serviceBO.getIpAddress();
		IpRegionBO ipRegionBO = ipService.getIpRegionBO(ipAddress);
		if (null == ipRegionBO) {
			return;
		}

		serviceBO.setIpRegion(ipRegionBO.getIpRegion());
		serviceBO.setIpRegionCountry(ipRegionBO.getIpRegionCountry());
		serviceBO.setIpRegionProvince(ipRegionBO.getIpRegionProvince());
		serviceBO.setIpRegionCity(ipRegionBO.getIpRegionCity());
		serviceBO.setIpRegionIsp(ipRegionBO.getIpRegionIsp());
	}


	// =====================================私有方法 end=====================================
}
