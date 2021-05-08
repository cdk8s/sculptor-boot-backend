/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEmployeeController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.controller.bases.SysEmployeeControllerBase;
import com.cdk8s.sculptor.mapstruct.SysEmployeeMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeePageQueryParam;
import com.cdk8s.sculptor.service.SysEmployeeService;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/sysEmployee")
public class SysEmployeeController extends SysEmployeeControllerBase {

	@Autowired
	private SysEmployeeService sysEmployeeService;

	@Autowired
	private SysEmployeeMapStruct sysEmployeeMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_employee")
	@RequestMapping(value = "/pageToUser", method = RequestMethod.POST)
	public ResponseEntity<?> pageToUser(@Valid @RequestBody SysEmployeePageQueryParam param) {
		PageInfo result = sysEmployeeService.findPageByServiceBOToUser(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysEmployeeMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
