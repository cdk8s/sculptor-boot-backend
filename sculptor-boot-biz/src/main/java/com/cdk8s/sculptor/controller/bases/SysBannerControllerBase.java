/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysBannerMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysbanner.*;
import com.cdk8s.sculptor.pojo.dto.response.sysbanner.SysBannerResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysBanner;
import com.cdk8s.sculptor.service.SysBannerService;
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
public class SysBannerControllerBase {

	@Autowired
	private SysBannerService sysBannerService;

	@Autowired
	private SysBannerMapStruct sysBannerMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_banner")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysBannerResponseDTO dto = sysBannerMapStruct.toResponseDTO(sysBannerService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_banner")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysBannerMapStruct.toResponseDTOList(sysBannerService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_banner")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysBannerPageQueryParam param) {
		List<SysBanner> result = sysBannerService.findListByServiceBO(sysBannerMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysBannerMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_banner")
	@RequestMapping(value = "/listByJumpTypeCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByJumpTypeCode(@Valid @RequestBody SysBannerJumpTypeCodeRequestParam param) {
		List<SysBanner> result = sysBannerService.findListByJumpTypeCode(sysBannerMapStruct.jumpTypeCodeRequestParamToServiceBO(param));
		List<SysBannerResponseDTO> dtoList = sysBannerMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_banner")
	@RequestMapping(value = "/listByBannerCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByBannerCode(@Valid @RequestBody SysBannerBannerCodeRequestParam param) {
		List<SysBanner> result = sysBannerService.findListByBannerCode(sysBannerMapStruct.bannerCodeRequestParamToServiceBO(param));
		List<SysBannerResponseDTO> dtoList = sysBannerMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_banner")
	@RequestMapping(value = "/listByBannerCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByBannerCodeList(@Valid @RequestBody SysBannerBannerCodeListRequestParam param) {
		List<SysBanner> result = sysBannerService.findListByBannerCodeList(sysBannerMapStruct.bannerCodeListRequestParamToServiceBO(param));
		List<SysBannerResponseDTO> dtoList = sysBannerMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_banner")
	@RequestMapping(value = "/listByBannerTitleWhereLike", method = RequestMethod.POST)
	public ResponseEntity<?> listByBannerTitleWhereLike(@Valid @RequestBody SysBannerBannerTitleLikeQueryParam param) {
		PageInfo result = sysBannerService.findPageByBannerTitleWhereLike(sysBannerMapStruct.bannerTitleLikeQueryParamToServiceBO(param));
		List<SysBannerResponseDTO> list = sysBannerMapStruct.toResponseDTOList(result.getList());
		result.setList(list);
		return R.success(result);
	}


	@RequestPermission("sys_banner")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysBannerPageQueryParam param) {
		PageInfo result = sysBannerService.findPageByServiceBO(sysBannerMapStruct.pageQueryParamToServiceBO(param));
		List<SysBannerResponseDTO> list = sysBannerMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysBannerResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_banner")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysBannerService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysBanner 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_banner_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysBannerCreateRequestParam param) {
		sysBannerService.create(sysBannerMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysBanner 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_banner_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysBannerUpdateRequestParam param) {
		sysBannerService.update(sysBannerMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysBanner 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_banner_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysBannerService.batchDelete(sysBannerMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
