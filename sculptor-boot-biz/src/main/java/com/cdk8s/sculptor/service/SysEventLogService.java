/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysEventLogMapper;
import com.cdk8s.sculptor.mapper.ext.SysEventLogMapperExt;
import com.cdk8s.sculptor.mapstruct.SysEventLogMapStruct;
import com.cdk8s.sculptor.service.bases.SysEventLogServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysEventLogService extends SysEventLogServiceBase {

	@Autowired
	private SysEventLogMapper sysEventLogMapper;

	@Autowired
	private SysEventLogMapperExt sysEventLogMapperExt;

	@Autowired
	private SysEventLogMapStruct sysEventLogMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

