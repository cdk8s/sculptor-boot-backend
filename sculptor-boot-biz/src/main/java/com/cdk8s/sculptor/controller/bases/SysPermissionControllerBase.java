/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysPermissionMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.TreeCascadeServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.*;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.*;
import com.cdk8s.sculptor.pojo.dto.response.syspermission.SysPermissionResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import com.cdk8s.sculptor.service.SysPermissionService;
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
public class SysPermissionControllerBase {

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysPermissionMapStruct sysPermissionMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysPermissionResponseDTO dto = sysPermissionMapStruct.toResponseDTO(sysPermissionService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByTreeCascade", method = RequestMethod.POST)
	public ResponseEntity<?> listByTreeCascade(@Valid @RequestBody TreeCascadeRequestParam param) {
		TreeCascadeServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findListByTreeCascade(serviceBO)));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByParentId(serviceBO)));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findListByParentId(serviceBO)));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listAndCheckParentByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listAndCheckParentByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findListAndCheckParentEnumByParentId(serviceBO)));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysPermissionPageQueryParam param) {
		List<SysPermission> result = sysPermissionService.findListByServiceBO(sysPermissionMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysPermissionMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByPermissionCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByPermissionCode(@Valid @RequestBody SysPermissionPermissionCodeRequestParam param) {
		List<SysPermission> result = sysPermissionService.findListByPermissionCode(sysPermissionMapStruct.permissionCodeRequestParamToServiceBO(param));
		List<SysPermissionResponseDTO> dtoList = sysPermissionMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByPermissionCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByPermissionCodeList(@Valid @RequestBody SysPermissionPermissionCodeListRequestParam param) {
		List<SysPermission> result = sysPermissionService.findListByPermissionCodeList(sysPermissionMapStruct.permissionCodeListRequestParamToServiceBO(param));
		List<SysPermissionResponseDTO> dtoList = sysPermissionMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_permission")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysPermissionPageQueryParam param) {
		PageInfo result = sysPermissionService.findPageByServiceBO(sysPermissionMapStruct.pageQueryParamToServiceBO(param));
		List<SysPermissionResponseDTO> list = sysPermissionMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysPermissionResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysPermissionService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_permission_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysPermissionCreateRequestParam param) {
		sysPermissionService.create(sysPermissionMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_permission_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysPermissionUpdateRequestParam param) {
		sysPermissionService.update(sysPermissionMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysPermission 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_permission_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysPermissionService.batchUpdateState(sysPermissionMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_permission_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysPermissionService.batchDelete(sysPermissionMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
