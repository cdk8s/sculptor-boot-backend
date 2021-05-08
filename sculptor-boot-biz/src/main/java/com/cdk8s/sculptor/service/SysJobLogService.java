/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysJobLogMapper;
import com.cdk8s.sculptor.mapper.ext.SysJobLogMapperExt;
import com.cdk8s.sculptor.mapstruct.SysJobLogMapStruct;
import com.cdk8s.sculptor.service.bases.SysJobLogServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysJobLogService extends SysJobLogServiceBase {

	@Autowired
	private SysJobLogMapper sysJobLogMapper;

	@Autowired
	private SysJobLogMapperExt sysJobLogMapperExt;

	@Autowired
	private SysJobLogMapStruct sysJobLogMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

