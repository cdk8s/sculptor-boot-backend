/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamTypeControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysParamTypeMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparamtype.*;
import com.cdk8s.sculptor.pojo.dto.response.sysparamtype.SysParamTypeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import com.cdk8s.sculptor.service.SysParamTypeService;
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
public class SysParamTypeControllerBase {

	@Autowired
	private SysParamTypeService sysParamTypeService;

	@Autowired
	private SysParamTypeMapStruct sysParamTypeMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysParamTypeResponseDTO dto = sysParamTypeMapStruct.toResponseDTO(sysParamTypeService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysParamTypeMapStruct.toResponseDTOList(sysParamTypeService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysParamTypePageQueryParam param) {
		List<SysParamType> result = sysParamTypeService.findListByServiceBO(sysParamTypeMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysParamTypeMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/listByTypeCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByTypeCode(@Valid @RequestBody SysParamTypeTypeCodeRequestParam param) {
		List<SysParamType> result = sysParamTypeService.findListByTypeCode(sysParamTypeMapStruct.typeCodeRequestParamToServiceBO(param));
		List<SysParamTypeResponseDTO> dtoList = sysParamTypeMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/listByTypeCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByTypeCodeList(@Valid @RequestBody SysParamTypeTypeCodeListRequestParam param) {
		List<SysParamType> result = sysParamTypeService.findListByTypeCodeList(sysParamTypeMapStruct.typeCodeListRequestParamToServiceBO(param));
		List<SysParamTypeResponseDTO> dtoList = sysParamTypeMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysParamTypePageQueryParam param) {
		PageInfo result = sysParamTypeService.findPageByServiceBO(sysParamTypeMapStruct.pageQueryParamToServiceBO(param));
		List<SysParamTypeResponseDTO> list = sysParamTypeMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysParamTypeResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_param_type")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysParamTypeService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_param_type_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysParamTypeCreateRequestParam param) {
		sysParamTypeService.create(sysParamTypeMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_param_type_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysParamTypeUpdateRequestParam param) {
		sysParamTypeService.update(sysParamTypeMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysParamType 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_param_type_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysParamTypeService.batchUpdateState(sysParamTypeMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_param_type_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysParamTypeService.batchDelete(sysParamTypeMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
