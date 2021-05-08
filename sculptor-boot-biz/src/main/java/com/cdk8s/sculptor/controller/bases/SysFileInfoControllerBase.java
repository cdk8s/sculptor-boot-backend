/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysFileInfoMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.SysFileInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import com.cdk8s.sculptor.service.SysFileInfoService;
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
public class SysFileInfoControllerBase {

	@Autowired
	private SysFileInfoService sysFileInfoService;

	@Autowired
	private SysFileInfoMapStruct sysFileInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysFileInfoResponseDTO dto = sysFileInfoMapStruct.toResponseDTO(sysFileInfoService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysFileInfoMapStruct.toResponseDTOList(sysFileInfoService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysFileInfoPageQueryParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByServiceBO(sysFileInfoMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysFileInfoMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileSuffix", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileSuffix(@Valid @RequestBody SysFileInfoFileSuffixRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileSuffix(sysFileInfoMapStruct.fileSuffixRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileStorageName", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileStorageName(@Valid @RequestBody SysFileInfoFileStorageNameRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileStorageName(sysFileInfoMapStruct.fileStorageNameRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileShowName", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileShowName(@Valid @RequestBody SysFileInfoFileShowNameRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileShowName(sysFileInfoMapStruct.fileShowNameRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileSuffixList", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileSuffixList(@Valid @RequestBody SysFileInfoFileSuffixListRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileSuffixList(sysFileInfoMapStruct.fileSuffixListRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileStorageNameList", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileStorageNameList(@Valid @RequestBody SysFileInfoFileStorageNameListRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileStorageNameList(sysFileInfoMapStruct.fileStorageNameListRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/listByFileShowNameList", method = RequestMethod.POST)
	public ResponseEntity<?> listByFileShowNameList(@Valid @RequestBody SysFileInfoFileShowNameListRequestParam param) {
		List<SysFileInfo> result = sysFileInfoService.findListByFileShowNameList(sysFileInfoMapStruct.fileShowNameListRequestParamToServiceBO(param));
		List<SysFileInfoResponseDTO> dtoList = sysFileInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysFileInfoPageQueryParam param) {
		PageInfo result = sysFileInfoService.findPageByServiceBO(sysFileInfoMapStruct.pageQueryParamToServiceBO(param));
		List<SysFileInfoResponseDTO> list = sysFileInfoMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysFileInfoResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_file_info")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysFileInfoService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysFileInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_file_info_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysFileInfoCreateRequestParam param) {
		sysFileInfoService.create(sysFileInfoMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysFileInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_file_info_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysFileInfoUpdateRequestParam param) {
		sysFileInfoService.update(sysFileInfoMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysFileInfo 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_file_info_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysFileInfoService.batchUpdateState(sysFileInfoMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysFileInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_file_info_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysFileInfoService.batchDelete(sysFileInfoMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
