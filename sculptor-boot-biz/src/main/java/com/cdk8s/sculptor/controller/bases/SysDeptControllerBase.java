/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDeptControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysDeptMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.TreeCascadeServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.*;
import com.cdk8s.sculptor.pojo.dto.param.sysdept.*;
import com.cdk8s.sculptor.pojo.dto.response.sysdept.SysDeptResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import com.cdk8s.sculptor.service.SysDeptService;
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
public class SysDeptControllerBase {

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysDeptMapStruct sysDeptMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysDeptResponseDTO dto = sysDeptMapStruct.toResponseDTO(sysDeptService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByTreeCascade", method = RequestMethod.POST)
	public ResponseEntity<?> listByTreeCascade(@Valid @RequestBody TreeCascadeRequestParam param) {
		TreeCascadeServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findListByTreeCascade(serviceBO)));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findTreeListByParentId(serviceBO)));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findListByParentId(serviceBO)));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listAndCheckParentByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listAndCheckParentByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findListAndCheckParentEnumByParentId(serviceBO)));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysDeptPageQueryParam param) {
		List<SysDept> result = sysDeptService.findListByServiceBO(sysDeptMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysDeptMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByDeptCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByDeptCode(@Valid @RequestBody SysDeptDeptCodeRequestParam param) {
		List<SysDept> result = sysDeptService.findListByDeptCode(sysDeptMapStruct.deptCodeRequestParamToServiceBO(param));
		List<SysDeptResponseDTO> dtoList = sysDeptMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByDeptCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByDeptCodeList(@Valid @RequestBody SysDeptDeptCodeListRequestParam param) {
		List<SysDept> result = sysDeptService.findListByDeptCodeList(sysDeptMapStruct.deptCodeListRequestParamToServiceBO(param));
		List<SysDeptResponseDTO> dtoList = sysDeptMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_dept")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysDeptPageQueryParam param) {
		PageInfo result = sysDeptService.findPageByServiceBO(sysDeptMapStruct.pageQueryParamToServiceBO(param));
		List<SysDeptResponseDTO> list = sysDeptMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysDeptResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysDeptService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dept_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysDeptCreateRequestParam param) {
		sysDeptService.create(sysDeptMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_dept_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysDeptUpdateRequestParam param) {
		sysDeptService.update(sysDeptMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysDept 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_dept_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysDeptService.batchUpdateState(sysDeptMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_dept_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysDeptService.batchDelete(sysDeptMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
