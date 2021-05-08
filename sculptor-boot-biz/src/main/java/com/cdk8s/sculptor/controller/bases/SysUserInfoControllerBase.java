/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysUserInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysuserinfo.SysUserInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import com.cdk8s.sculptor.service.SysUserInfoService;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SysUserInfoControllerBase {

	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Autowired
	private SysUserInfoMapStruct sysUserInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysUserInfoResponseDTO dto = sysUserInfoMapStruct.toResponseDTO(sysUserInfoService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysUserInfoMapStruct.toResponseDTOList(sysUserInfoService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysUserInfoPageQueryParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByServiceBO(sysUserInfoMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysUserInfoMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByWeixinUnionid", method = RequestMethod.POST)
	public ResponseEntity<?> listByWeixinUnionid(@Valid @RequestBody SysUserInfoWeixinUnionidRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByWeixinUnionid(sysUserInfoMapStruct.weixinUnionidRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByWeixinOpenid", method = RequestMethod.POST)
	public ResponseEntity<?> listByWeixinOpenid(@Valid @RequestBody SysUserInfoWeixinOpenidRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByWeixinOpenid(sysUserInfoMapStruct.weixinOpenidRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByIdCard", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdCard(@Valid @RequestBody SysUserInfoIdCardRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByIdCard(sysUserInfoMapStruct.idCardRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByWeixinUnionidList", method = RequestMethod.POST)
	public ResponseEntity<?> listByWeixinUnionidList(@Valid @RequestBody SysUserInfoWeixinUnionidListRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByWeixinUnionidList(sysUserInfoMapStruct.weixinUnionidListRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByWeixinOpenidList", method = RequestMethod.POST)
	public ResponseEntity<?> listByWeixinOpenidList(@Valid @RequestBody SysUserInfoWeixinOpenidListRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByWeixinOpenidList(sysUserInfoMapStruct.weixinOpenidListRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/listByIdCardList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdCardList(@Valid @RequestBody SysUserInfoIdCardListRequestParam param) {
		List<SysUserInfo> result = sysUserInfoService.findListByIdCardList(sysUserInfoMapStruct.idCardListRequestParamToServiceBO(param));
		List<SysUserInfoResponseDTO> dtoList = sysUserInfoMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysUserInfoPageQueryParam param) {
		PageInfo result = sysUserInfoService.findPageByServiceBO(sysUserInfoMapStruct.pageQueryParamToServiceBO(param));
		List<SysUserInfoResponseDTO> list = sysUserInfoMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysUserInfoResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_user_info")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysUserInfoService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysUserInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_user_info_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysUserInfoCreateRequestParam param) {
		sysUserInfoService.create(sysUserInfoMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysUserInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_info_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysUserInfoUpdateRequestParam param) {
		sysUserInfoService.update(sysUserInfoMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 sysUserInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_user_info_create")
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody SysUserInfoBatchCreateRequestParam param) {
		List<SysUserInfoCreateRequestParam> paramList = param.getParamList();

		List<SysUserInfoCreateServiceBO> serviceBOList = new ArrayList<>();
		for (SysUserInfoCreateRequestParam createParam : paramList) {
			SysUserInfoCreateServiceBO serviceBO = sysUserInfoMapStruct.createRequestParamToServiceBO(createParam);
			Long currentUserId = UserInfoContext.getCurrentUserId();
			long currentEpochMilli = DatetimeUtil.currentEpochMilli();
			serviceBO.setCreateDate(currentEpochMilli);
			serviceBO.setCreateUserId(currentUserId);
			serviceBO.setUpdateDate(currentEpochMilli);
			serviceBO.setUpdateUserId(currentUserId);
			serviceBO.setTenantId(UserInfoContext.getCurrentUserTenantId());
			serviceBOList.add(serviceBO);
		}

		sysUserInfoService.batchCreate(serviceBOList);
		return R.success();
	}

	@EventLog(message = "批量更新 sysUserInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_info_update")
	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdate(@Valid @RequestBody SysUserInfoBatchUpdateRequestParam param) {
		List<SysUserInfoUpdateRequestParam> paramList = param.getParamList();

		List<SysUserInfoUpdateServiceBO> serviceBOList = new ArrayList<>();
		for (SysUserInfoUpdateRequestParam updateParam : paramList) {
			SysUserInfoUpdateServiceBO serviceBO = sysUserInfoMapStruct.updateRequestParamToServiceBO(updateParam);
			serviceBO.setUpdateDate(DatetimeUtil.currentEpochMilli());
			serviceBO.setUpdateUserId(UserInfoContext.getCurrentUserId());
			serviceBO.setTenantId(UserInfoContext.getCurrentUserTenantId());
			serviceBOList.add(serviceBO);
		}

		sysUserInfoService.batchUpdate(serviceBOList);
		return R.success();
	}


	@EventLog(message = "批量删除 sysUserInfo 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_user_info_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysUserInfoService.batchDelete(sysUserInfoMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
