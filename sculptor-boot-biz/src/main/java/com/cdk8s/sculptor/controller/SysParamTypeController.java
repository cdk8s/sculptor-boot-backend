/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamTypeController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.controller.bases.SysParamTypeControllerBase;
import com.cdk8s.sculptor.mapstruct.SysParamTypeMapStruct;
import com.cdk8s.sculptor.service.SysParamTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/sysParamType")
public class SysParamTypeController extends SysParamTypeControllerBase {

	@Autowired
	private SysParamTypeService sysParamTypeService;

	@Autowired
	private SysParamTypeMapStruct sysParamTypeMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
