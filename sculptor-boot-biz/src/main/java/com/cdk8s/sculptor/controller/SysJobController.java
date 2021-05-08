/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.controller.bases.SysJobControllerBase;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysJobMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.service.SysJobService;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.SneakyThrows;
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
@RequestMapping("/api/sysJob")
public class SysJobController extends SysJobControllerBase {

	@Autowired
	private SysJobService sysJobService;

	@Autowired
	private SysJobMapStruct sysJobMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@EventLog(message = "批量暂停任务", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@SneakyThrows
	@RequestMapping(value = "/batchPauseJob", method = RequestMethod.POST)
	@RequestPermission("sys_job_pause")
	public ResponseEntity<?> batchPauseJob(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(param.getIdList());
		idListServiceBO.setTenantId(param.getTenantId());
		idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		sysJobService.batchPauseJob(idListServiceBO);
		return R.success();
	}

	@EventLog(message = "批量恢复任务", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@SneakyThrows
	@RequestMapping(value = "/batchResumeJob", method = RequestMethod.POST)
	@RequestPermission("sys_job_resume")
	public ResponseEntity<?> batchResumeJob(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(param.getIdList());
		idListServiceBO.setTenantId(param.getTenantId());
		idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		sysJobService.batchResumeJob(idListServiceBO);
		return R.success();
	}

	/**
	 * 立即执行一次任务调度
	 */
	@SneakyThrows
	@RequestMapping(value = "/runJobNow", method = RequestMethod.POST)
	@RequestPermission("sys_job_run_now")
	public ResponseEntity<?> runJobNow(@Valid @RequestBody IdListRequestParam param) {
		IdListServiceBO serviceBO = idAndIdListMapStruct.requestParamToServiceBO(param);
		sysJobService.runJobNow(serviceBO);
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
