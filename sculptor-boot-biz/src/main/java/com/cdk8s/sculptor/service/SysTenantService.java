/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysTenantService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysTenantMapper;
import com.cdk8s.sculptor.mapper.ext.SysTenantMapperExt;
import com.cdk8s.sculptor.mapstruct.SysTenantMapStruct;
import com.cdk8s.sculptor.service.bases.SysTenantServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Slf4j
@CacheConfig(cacheNames = "SysTenantService")
@Service
public class SysTenantService extends SysTenantServiceBase {

	@Autowired
	private SysTenantMapper sysTenantMapper;

	@Autowired
	private SysTenantMapperExt sysTenantMapperExt;

	@Autowired
	private SysTenantMapStruct sysTenantMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

