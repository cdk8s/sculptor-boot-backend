/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginLogListener.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.eventlistener.listener;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.eventlistener.event.LoginLogEvent;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserUsernameServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysLoginLogService;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.pojo.IpRegionBO;
import com.cdk8s.sculptor.util.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginLogListener {

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private IpService ipService;

	// =====================================业务 start=====================================

	@Async
	@EventListener(LoginLogEvent.class)
	public void service(LoginLogEvent event) {
		SysLoginLogCreateServiceBO serviceBO = event.getServiceBO();

		SysUserUsernameServiceBO sysUserUsernameServiceBO = new SysUserUsernameServiceBO();
		sysUserUsernameServiceBO.setUsername(serviceBO.getUsername());
		sysUserUsernameServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysUser sysUser = sysUserService.findOneByUsernameNotEnum(sysUserUsernameServiceBO);
		if (null == sysUser) {
			// 如果查询不到用户则不需要记录
			return;
		}

		serviceBO.setId(GenerateIdUtil.getId());
		serviceBO.setUserId(sysUser.getId());
		serviceBO.setUsername(sysUser.getUsername());
		serviceBO.setBoolNewUserEnum(BooleanEnum.NO.getCode());
		serviceBO.setCreateDate(DatetimeUtil.currentEpochMilli());
		serviceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);

		if ((DatetimeUtil.currentEpochMilli() - sysUser.getCreateDate()) < GlobalConstant.NEW_USER_INTERVAL_TIME_MILLISECOND) {
			serviceBO.setBoolNewUserEnum(BooleanEnum.YES.getCode());
		}

		// 处理 IP 地区信息，如果 db 文件没有，则调用淘宝
		buildIpRegion(serviceBO);
		sysLoginLogService.create(serviceBO);
	}

	// =====================================业务 end=====================================
	// =====================================私有方法 start=====================================

	private void buildIpRegion(SysLoginLogCreateServiceBO serviceBO) {
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
