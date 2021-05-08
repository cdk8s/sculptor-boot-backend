/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelPermissionRoleControllerBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller.bases;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelPermissionRoleMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.dto.response.relpermissionrole.RelPermissionRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.service.RelPermissionRoleService;
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
public class RelPermissionRoleControllerBase {

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	@Autowired
	private RelPermissionRoleMapStruct relPermissionRoleMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		RelPermissionRoleResponseDTO dto = relPermissionRoleMapStruct.toResponseDTO(relPermissionRoleService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relPermissionRoleMapStruct.toResponseDTOList(relPermissionRoleService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody RelPermissionRolePageQueryParam param) {
		List<RelPermissionRole> result = relPermissionRoleService.findListByServiceBO(relPermissionRoleMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relPermissionRoleMapStruct.toResponseDTOList(result));
	}


	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelPermissionRolePageQueryParam param) {
		PageInfo result = relPermissionRoleService.findPageByServiceBO(relPermissionRoleMapStruct.pageQueryParamToServiceBO(param));
		List<RelPermissionRoleResponseDTO> list = relPermissionRoleMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (RelPermissionRoleResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@EventLog(message = "创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelPermissionRoleCreateRequestParam param) {
		relPermissionRoleService.create(relPermissionRoleMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelPermissionRoleBatchCreateRequestParam param) {
		relPermissionRoleService.batchCreate(relPermissionRoleMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByPermissionIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByPermissionIdListToDelete(@Valid @RequestBody RelPermissionRoleBatchCreateRequestParam param) {
		relPermissionRoleService.batchCreateByPermissionIdListToDelete(relPermissionRoleMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByRoleIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByRoleIdListToDelete(@Valid @RequestBody RelPermissionRoleBatchCreateRequestParam param) {
		relPermissionRoleService.batchCreateByRoleIdListToDelete(relPermissionRoleMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelPermissionRoleUpdateRequestParam param) {
		relPermissionRoleService.update(relPermissionRoleMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relPermissionRoleService.batchDelete(relPermissionRoleMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "删除 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/deleteByPermissionIdAndRoleId", method = RequestMethod.POST)
	public ResponseEntity<?> deleteByPermissionIdAndRoleId(@Valid @RequestBody RelPermissionRolePermissionIdAndRoleIdToDeleteRequestParam param) {
		relPermissionRoleService.deleteByPermissionIdAndRoleId(relPermissionRoleMapStruct.deleteByPermissionIdAndRoleIdParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
