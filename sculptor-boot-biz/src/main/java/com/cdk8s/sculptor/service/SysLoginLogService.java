/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysLoginLogMapper;
import com.cdk8s.sculptor.mapper.ext.SysLoginLogMapperExt;
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogTokenServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.service.bases.SysLoginLogServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SysLoginLogService extends SysLoginLogServiceBase {

	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;

	@Autowired
	private SysLoginLogMapperExt sysLoginLogMapperExt;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public SysLoginLog findOneByToken(SysLoginLogTokenServiceBO serviceBO) {
		List<SysLoginLog> listByToken = findListByToken(serviceBO);
		if (CollectionUtil.isNotEmpty(listByToken)) {
			return listByToken.get(0);
		}
		return null;
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

