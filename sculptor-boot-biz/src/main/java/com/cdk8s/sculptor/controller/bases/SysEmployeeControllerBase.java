/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEmployeeControllerBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller.bases;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysEmployeeMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysemployee.SysEmployeeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
import com.cdk8s.sculptor.service.SysEmployeeService;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Slf4j
public class SysEmployeeControllerBase {

	@Autowired
	private SysEmployeeService sysEmployeeService;

	@Autowired
	private SysEmployeeMapStruct sysEmployeeMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_employee")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysEmployeeResponseDTO dto = sysEmployeeMapStruct.toResponseDTO(sysEmployeeService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_employee")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysEmployeeMapStruct.toResponseDTOList(sysEmployeeService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_employee")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysEmployeePageQueryParam param) {
		List<SysEmployee> result = sysEmployeeService.findListByServiceBO(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysEmployeeMapStruct.toResponseDTOList(result));
	}


	@RequestPermission("sys_employee")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysEmployeePageQueryParam param) {
		PageInfo result = sysEmployeeService.findPageByServiceBO(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		List<SysEmployeeResponseDTO> list = sysEmployeeMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysEmployeeResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_employee")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysEmployeeService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_employee_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysEmployeeCreateRequestParam param) {
		sysEmployeeService.create(sysEmployeeMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_employee_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysEmployeeUpdateRequestParam param) {
		sysEmployeeService.update(sysEmployeeMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_employee_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysEmployeeService.batchDelete(sysEmployeeMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
