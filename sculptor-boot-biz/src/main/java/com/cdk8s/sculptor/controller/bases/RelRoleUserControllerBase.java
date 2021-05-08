/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserControllerBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller.bases;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.*;
import com.cdk8s.sculptor.pojo.dto.response.relroleuser.RelRoleUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.service.RelRoleUserService;
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
public class RelRoleUserControllerBase {

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		RelRoleUserResponseDTO dto = relRoleUserMapStruct.toResponseDTO(relRoleUserService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relRoleUserMapStruct.toResponseDTOList(relRoleUserService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody RelRoleUserPageQueryParam param) {
		List<RelRoleUser> result = relRoleUserService.findListByServiceBO(relRoleUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relRoleUserMapStruct.toResponseDTOList(result));
	}


	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelRoleUserPageQueryParam param) {
		PageInfo result = relRoleUserService.findPageByServiceBO(relRoleUserMapStruct.pageQueryParamToServiceBO(param));
		List<RelRoleUserResponseDTO> list = relRoleUserMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (RelRoleUserResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		return R.success();
	}

	@EventLog(message = "创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelRoleUserCreateRequestParam param) {
		relRoleUserService.create(relRoleUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelRoleUserBatchCreateRequestParam param) {
		relRoleUserService.batchCreate(relRoleUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByRoleIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByRoleIdListToDelete(@Valid @RequestBody RelRoleUserBatchCreateRequestParam param) {
		relRoleUserService.batchCreateByRoleIdListToDelete(relRoleUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByUserIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByUserIdListToDelete(@Valid @RequestBody RelRoleUserBatchCreateRequestParam param) {
		relRoleUserService.batchCreateByUserIdListToDelete(relRoleUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelRoleUserUpdateRequestParam param) {
		relRoleUserService.update(relRoleUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relRoleUserService.batchDelete(relRoleUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "删除 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/deleteByRoleIdAndUserId", method = RequestMethod.POST)
	public ResponseEntity<?> deleteByRoleIdAndUserId(@Valid @RequestBody RelRoleUserRoleIdAndUserIdToDeleteRequestParam param) {
		relRoleUserService.deleteByRoleIdAndUserId(relRoleUserMapStruct.deleteByRoleIdAndUserIdParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
