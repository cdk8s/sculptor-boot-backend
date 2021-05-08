/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.*;
import com.cdk8s.sculptor.pojo.dto.response.sysloginlog.SysLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.service.SysLoginLogService;
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
public class SysLoginLogControllerBase {

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysLoginLogResponseDTO dto = sysLoginLogMapStruct.toResponseDTO(sysLoginLogService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysLoginLogMapStruct.toResponseDTOList(sysLoginLogService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysLoginLogPageQueryParam param) {
		List<SysLoginLog> result = sysLoginLogService.findListByServiceBO(sysLoginLogMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysLoginLogMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/listByToken", method = RequestMethod.POST)
	public ResponseEntity<?> listByToken(@Valid @RequestBody SysLoginLogTokenRequestParam param) {
		List<SysLoginLog> result = sysLoginLogService.findListByToken(sysLoginLogMapStruct.tokenRequestParamToServiceBO(param));
		List<SysLoginLogResponseDTO> dtoList = sysLoginLogMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/listByUsername", method = RequestMethod.POST)
	public ResponseEntity<?> listByUsername(@Valid @RequestBody SysLoginLogUsernameRequestParam param) {
		List<SysLoginLog> result = sysLoginLogService.findListByUsername(sysLoginLogMapStruct.usernameRequestParamToServiceBO(param));
		List<SysLoginLogResponseDTO> dtoList = sysLoginLogMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/listByUsernameList", method = RequestMethod.POST)
	public ResponseEntity<?> listByUsernameList(@Valid @RequestBody SysLoginLogUsernameListRequestParam param) {
		List<SysLoginLog> result = sysLoginLogService.findListByUsernameList(sysLoginLogMapStruct.usernameListRequestParamToServiceBO(param));
		List<SysLoginLogResponseDTO> dtoList = sysLoginLogMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysLoginLogPageQueryParam param) {
		PageInfo result = sysLoginLogService.findPageByServiceBO(sysLoginLogMapStruct.pageQueryParamToServiceBO(param));
		List<SysLoginLogResponseDTO> list = sysLoginLogMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysLoginLogResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		return R.success();
	}

	@EventLog(message = "创建 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_login_log_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysLoginLogCreateRequestParam param) {
		sysLoginLogService.create(sysLoginLogMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_login_log_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysLoginLogUpdateRequestParam param) {
		sysLoginLogService.update(sysLoginLogMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_login_log_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysLoginLogService.batchDelete(sysLoginLogMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
