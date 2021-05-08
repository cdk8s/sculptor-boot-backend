/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBigTextControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysBigTextMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysbigtext.*;
import com.cdk8s.sculptor.pojo.dto.response.sysbigtext.SysBigTextResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysBigText;
import com.cdk8s.sculptor.service.SysBigTextService;
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
public class SysBigTextControllerBase {

	@Autowired
	private SysBigTextService sysBigTextService;

	@Autowired
	private SysBigTextMapStruct sysBigTextMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysBigTextResponseDTO dto = sysBigTextMapStruct.toResponseDTO(sysBigTextService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysBigTextMapStruct.toResponseDTOList(sysBigTextService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysBigTextPageQueryParam param) {
		List<SysBigText> result = sysBigTextService.findListByServiceBO(sysBigTextMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysBigTextMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/listByTextCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByTextCode(@Valid @RequestBody SysBigTextTextCodeRequestParam param) {
		List<SysBigText> result = sysBigTextService.findListByTextCode(sysBigTextMapStruct.textCodeRequestParamToServiceBO(param));
		List<SysBigTextResponseDTO> dtoList = sysBigTextMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/listByTextCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByTextCodeList(@Valid @RequestBody SysBigTextTextCodeListRequestParam param) {
		List<SysBigText> result = sysBigTextService.findListByTextCodeList(sysBigTextMapStruct.textCodeListRequestParamToServiceBO(param));
		List<SysBigTextResponseDTO> dtoList = sysBigTextMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/listByTextTitleWhereLike", method = RequestMethod.POST)
	public ResponseEntity<?> listByTextTitleWhereLike(@Valid @RequestBody SysBigTextTextTitleLikeQueryParam param) {
		PageInfo result = sysBigTextService.findPageByTextTitleWhereLike(sysBigTextMapStruct.textTitleLikeQueryParamToServiceBO(param));
		List<SysBigTextResponseDTO> list = sysBigTextMapStruct.toResponseDTOList(result.getList());
		result.setList(list);
		return R.success(result);
	}


	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysBigTextPageQueryParam param) {
		PageInfo result = sysBigTextService.findPageByServiceBO(sysBigTextMapStruct.pageQueryParamToServiceBO(param));
		List<SysBigTextResponseDTO> list = sysBigTextMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysBigTextResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_big_text")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysBigTextService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysBigText 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_big_text_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysBigTextCreateRequestParam param) {
		sysBigTextService.create(sysBigTextMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysBigText 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_big_text_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysBigTextUpdateRequestParam param) {
		sysBigTextService.update(sysBigTextMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysBigText 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_big_text_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysBigTextService.batchUpdateState(sysBigTextMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysBigText 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_big_text_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysBigTextService.batchDelete(sysBigTextMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
