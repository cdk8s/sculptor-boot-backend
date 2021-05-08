/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysParamMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.*;
import com.cdk8s.sculptor.pojo.dto.response.sysparam.SysParamResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import com.cdk8s.sculptor.service.SysParamService;
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
public class SysParamControllerBase {

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private SysParamMapStruct sysParamMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_param")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysParamResponseDTO dto = sysParamMapStruct.toResponseDTO(sysParamService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysParamMapStruct.toResponseDTOList(sysParamService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_param")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysParamPageQueryParam param) {
		List<SysParam> result = sysParamService.findListByServiceBO(sysParamMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysParamMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByParamCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByParamCode(@Valid @RequestBody SysParamParamCodeRequestParam param) {
		List<SysParam> result = sysParamService.findListByParamCode(sysParamMapStruct.paramCodeRequestParamToServiceBO(param));
		List<SysParamResponseDTO> dtoList = sysParamMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByTypeCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByTypeCode(@Valid @RequestBody SysParamTypeCodeRequestParam param) {
		List<SysParam> result = sysParamService.findListByTypeCode(sysParamMapStruct.typeCodeRequestParamToServiceBO(param));
		List<SysParamResponseDTO> dtoList = sysParamMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByParamCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByParamCodeList(@Valid @RequestBody SysParamParamCodeListRequestParam param) {
		List<SysParam> result = sysParamService.findListByParamCodeList(sysParamMapStruct.paramCodeListRequestParamToServiceBO(param));
		List<SysParamResponseDTO> dtoList = sysParamMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByTypeCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByTypeCodeList(@Valid @RequestBody SysParamTypeCodeListRequestParam param) {
		List<SysParam> result = sysParamService.findListByTypeCodeList(sysParamMapStruct.typeCodeListRequestParamToServiceBO(param));
		List<SysParamResponseDTO> dtoList = sysParamMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_param")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysParamPageQueryParam param) {
		PageInfo result = sysParamService.findPageByServiceBO(sysParamMapStruct.pageQueryParamToServiceBO(param));
		List<SysParamResponseDTO> list = sysParamMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysParamResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_param")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysParamService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_param_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysParamCreateRequestParam param) {
		sysParamService.create(sysParamMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysParamUpdateRequestParam param) {
		sysParamService.update(sysParamMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysParam 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysParamService.batchUpdateState(sysParamMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_param_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysParamService.batchDelete(sysParamMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
