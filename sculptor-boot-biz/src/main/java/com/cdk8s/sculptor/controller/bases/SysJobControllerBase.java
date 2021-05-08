/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysJobMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysjob.*;
import com.cdk8s.sculptor.pojo.dto.response.sysjob.SysJobResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import com.cdk8s.sculptor.service.SysJobService;
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
public class SysJobControllerBase {

	@Autowired
	private SysJobService sysJobService;

	@Autowired
	private SysJobMapStruct sysJobMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_job")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysJobResponseDTO dto = sysJobMapStruct.toResponseDTO(sysJobService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_job")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysJobMapStruct.toResponseDTOList(sysJobService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_job")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysJobPageQueryParam param) {
		List<SysJob> result = sysJobService.findListByServiceBO(sysJobMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysJobMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_job")
	@RequestMapping(value = "/listByJobName", method = RequestMethod.POST)
	public ResponseEntity<?> listByJobName(@Valid @RequestBody SysJobJobNameRequestParam param) {
		List<SysJob> result = sysJobService.findListByJobName(sysJobMapStruct.jobNameRequestParamToServiceBO(param));
		List<SysJobResponseDTO> dtoList = sysJobMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_job")
	@RequestMapping(value = "/listByJobNameList", method = RequestMethod.POST)
	public ResponseEntity<?> listByJobNameList(@Valid @RequestBody SysJobJobNameListRequestParam param) {
		List<SysJob> result = sysJobService.findListByJobNameList(sysJobMapStruct.jobNameListRequestParamToServiceBO(param));
		List<SysJobResponseDTO> dtoList = sysJobMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_job")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysJobPageQueryParam param) {
		PageInfo result = sysJobService.findPageByServiceBO(sysJobMapStruct.pageQueryParamToServiceBO(param));
		List<SysJobResponseDTO> list = sysJobMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysJobResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_job")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysJobService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysJob 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_job_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysJobCreateRequestParam param) {
		sysJobService.create(sysJobMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysJob 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_job_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysJobUpdateRequestParam param) {
		sysJobService.update(sysJobMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysJob 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_job_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysJobService.batchUpdateState(sysJobMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysJob 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_job_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysJobService.batchDelete(sysJobMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
