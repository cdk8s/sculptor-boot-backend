/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysCityAreaMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.TreeCascadeServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.*;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syscityarea.SysCityAreaResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysCityArea;
import com.cdk8s.sculptor.service.SysCityAreaService;
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
public class SysCityAreaControllerBase {

	@Autowired
	private SysCityAreaService sysCityAreaService;

	@Autowired
	private SysCityAreaMapStruct sysCityAreaMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysCityAreaResponseDTO dto = sysCityAreaMapStruct.toResponseDTO(sysCityAreaService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysCityAreaMapStruct.toResponseDTOList(sysCityAreaService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/listByTreeCascade", method = RequestMethod.POST)
	public ResponseEntity<?> listByTreeCascade(@Valid @RequestBody TreeCascadeRequestParam param) {
		TreeCascadeServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysCityAreaMapStruct.toResponseDTOList(sysCityAreaService.findListByTreeCascade(serviceBO)));
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysCityAreaMapStruct.toResponseDTOList(sysCityAreaService.findTreeListByParentId(serviceBO)));
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/listByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysCityAreaMapStruct.toResponseDTOList(sysCityAreaService.findListByParentId(serviceBO)));
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/listAndCheckParentByParentId", method = RequestMethod.POST)
	public ResponseEntity<?> listAndCheckParentByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysCityAreaMapStruct.toResponseDTOList(sysCityAreaService.findListAndCheckParentEnumByParentId(serviceBO)));
	}

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysCityAreaPageQueryParam param) {
		List<SysCityArea> result = sysCityAreaService.findListByServiceBO(sysCityAreaMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysCityAreaMapStruct.toResponseDTOList(result));
	}


	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysCityAreaPageQueryParam param) {
		PageInfo result = sysCityAreaService.findPageByServiceBO(sysCityAreaMapStruct.pageQueryParamToServiceBO(param));
		List<SysCityAreaResponseDTO> list = sysCityAreaMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysCityAreaResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_city_area")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysCityAreaService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysCityArea 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_city_area_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysCityAreaCreateRequestParam param) {
		sysCityAreaService.create(sysCityAreaMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysCityArea 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_city_area_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysCityAreaUpdateRequestParam param) {
		sysCityAreaService.update(sysCityAreaMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysCityArea 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_city_area_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysCityAreaService.batchDelete(sysCityAreaMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
