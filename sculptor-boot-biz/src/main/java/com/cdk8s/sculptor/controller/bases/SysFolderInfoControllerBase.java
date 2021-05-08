/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFolderInfoControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysFolderInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.TreeCascadeServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.*;
import com.cdk8s.sculptor.pojo.dto.param.sysfolderinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysfolderinfo.SysFolderInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysFolderInfo;
import com.cdk8s.sculptor.service.SysFolderInfoService;
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
public class SysFolderInfoControllerBase {

	@Autowired
	private SysFolderInfoService sysFolderInfoService;

	@Autowired
	private SysFolderInfoMapStruct sysFolderInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysFolderInfoResponseDTO dto = sysFolderInfoMapStruct.toResponseDTO(sysFolderInfoService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listByTreeCascade", method = RequestMethod.POST)
	public ResponseEntity<?> listByTreeCascade(@Valid @RequestBody TreeCascadeRequestParam param) {
		TreeCascadeServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findListByTreeCascade(serviceBO)));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findTreeListByParentId(serviceBO)));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findListByParentId(serviceBO)));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listAndCheckParentByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listAndCheckParentByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findListAndCheckParentEnumByParentId(serviceBO)));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysFolderInfoPageQueryParam param) {
		List<SysFolderInfo> result = sysFolderInfoService.findListByServiceBO(sysFolderInfoMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listByFolderName", method = RequestMethod.POST)
	public ResponseEntity<?> listByFolderName(@Valid @RequestBody SysFolderInfoFolderNameRequestParam param) {
		List<SysFolderInfo> result = sysFolderInfoService.findListByFolderName(sysFolderInfoMapStruct.folderNameRequestParamToServiceBO(param));
		List<SysFolderInfoResponseDTO> dtoList = sysFolderInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/listByFolderNameList", method = RequestMethod.POST)
	public ResponseEntity<?> listByFolderNameList(@Valid @RequestBody SysFolderInfoFolderNameListRequestParam param) {
		List<SysFolderInfo> result = sysFolderInfoService.findListByFolderNameList(sysFolderInfoMapStruct.folderNameListRequestParamToServiceBO(param));
		List<SysFolderInfoResponseDTO> dtoList = sysFolderInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysFolderInfoPageQueryParam param) {
		PageInfo result = sysFolderInfoService.findPageByServiceBO(sysFolderInfoMapStruct.pageQueryParamToServiceBO(param));
		List<SysFolderInfoResponseDTO> list = sysFolderInfoMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysFolderInfoResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_folder_info")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysFolderInfoService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysFolderInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_folder_info_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysFolderInfoCreateRequestParam param) {
		sysFolderInfoService.create(sysFolderInfoMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysFolderInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_folder_info_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysFolderInfoUpdateRequestParam param) {
		sysFolderInfoService.update(sysFolderInfoMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysFolderInfo 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_folder_info_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysFolderInfoService.batchUpdateState(sysFolderInfoMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysFolderInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_folder_info_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysFolderInfoService.batchDelete(sysFolderInfoMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
