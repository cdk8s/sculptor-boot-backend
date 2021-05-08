/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LogoutTokenRefreshListener.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.listener;

import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.OfflineTypeEnum;
import com.cdk8s.sculptor.eventlistener.event.LogoutTokenRefreshEvent;
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.event.LogoutTokenRefreshBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogTokenServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.service.SysLoginLogService;
import com.cdk8s.sculptor.util.DatetimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutTokenRefreshListener {

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	// =====================================业务 start=====================================

	@Async
	@EventListener(LogoutTokenRefreshEvent.class)
	public void service(LogoutTokenRefreshEvent event) {
		LogoutTokenRefreshBO logoutTokenRefreshBO = event.getLogoutTokenRefreshBO();
		SysLoginLogTokenServiceBO sysLoginLogTokenServiceBO = new SysLoginLogTokenServiceBO();
		sysLoginLogTokenServiceBO.setToken(logoutTokenRefreshBO.getAccessToken());
		SysLoginLog entity = sysLoginLogService.findOneByToken(sysLoginLogTokenServiceBO);
		if (null == entity) {
			return;
		}

		SysLoginLogUpdateServiceBO serviceBO = new SysLoginLogUpdateServiceBO();

		serviceBO.setId(entity.getId());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.NO.getCode());
		serviceBO.setOfflineTypeEnum(OfflineTypeEnum.PEOPLE_LOGOUT.getCode());
		serviceBO.setLogoutDate(DatetimeUtil.currentEpochMilli());

		sysLoginLogService.update(serviceBO);
	}

	// =====================================业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
