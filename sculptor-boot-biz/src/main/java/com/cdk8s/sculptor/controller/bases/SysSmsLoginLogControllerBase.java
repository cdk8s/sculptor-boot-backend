/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysSmsLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.SysSmsLoginLogCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.SysSmsLoginLogPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.SysSmsLoginLogUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syssmsloginlog.SysSmsLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
import com.cdk8s.sculptor.service.SysSmsLoginLogService;
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
public class SysSmsLoginLogControllerBase {

	@Autowired
	private SysSmsLoginLogService sysSmsLoginLogService;

	@Autowired
	private SysSmsLoginLogMapStruct sysSmsLoginLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_sms_login_log")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysSmsLoginLogResponseDTO dto = sysSmsLoginLogMapStruct.toResponseDTO(sysSmsLoginLogService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_sms_login_log")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysSmsLoginLogMapStruct.toResponseDTOList(sysSmsLoginLogService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_sms_login_log")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysSmsLoginLogPageQueryParam param) {
		List<SysSmsLoginLog> result = sysSmsLoginLogService.findListByServiceBO(sysSmsLoginLogMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysSmsLoginLogMapStruct.toResponseDTOList(result));
	}


	@RequestPermission("sys_sms_login_log")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysSmsLoginLogPageQueryParam param) {
		PageInfo result = sysSmsLoginLogService.findPageByServiceBO(sysSmsLoginLogMapStruct.pageQueryParamToServiceBO(param));
		List<SysSmsLoginLogResponseDTO> list = sysSmsLoginLogMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysSmsLoginLogResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_sms_login_log")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysSmsLoginLogService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysSmsLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_sms_login_log_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysSmsLoginLogCreateRequestParam param) {
		sysSmsLoginLogService.create(sysSmsLoginLogMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysSmsLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_sms_login_log_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysSmsLoginLogUpdateRequestParam param) {
		sysSmsLoginLogService.update(sysSmsLoginLogMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysSmsLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_sms_login_log_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysSmsLoginLogService.batchDelete(sysSmsLoginLogMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
