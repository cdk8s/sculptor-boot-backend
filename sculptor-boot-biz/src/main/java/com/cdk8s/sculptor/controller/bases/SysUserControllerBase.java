/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserControllerBase.java
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
import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.*;
import com.cdk8s.sculptor.pojo.dto.response.sysuser.SysUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysUserService;
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
public class SysUserControllerBase {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserMapStruct sysUserMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_user")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysUserResponseDTO dto = sysUserMapStruct.toResponseDTO(sysUserService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysUserMapStruct.toResponseDTOList(sysUserService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_user")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysUserPageQueryParam param) {
		List<SysUser> result = sysUserService.findListByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysUserMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByUsername", method = RequestMethod.POST)
	public ResponseEntity<?> listByUsername(@Valid @RequestBody SysUserUsernameRequestParam param) {
		List<SysUser> result = sysUserService.findListByUsername(sysUserMapStruct.usernameRequestParamToServiceBO(param));
		List<SysUserResponseDTO> dtoList = sysUserMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByUserEmail", method = RequestMethod.POST)
	public ResponseEntity<?> listByUserEmail(@Valid @RequestBody SysUserUserEmailRequestParam param) {
		List<SysUser> result = sysUserService.findListByUserEmail(sysUserMapStruct.userEmailRequestParamToServiceBO(param));
		List<SysUserResponseDTO> dtoList = sysUserMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByNickname", method = RequestMethod.POST)
	public ResponseEntity<?> listByNickname(@Valid @RequestBody SysUserNicknameRequestParam param) {
		List<SysUser> result = sysUserService.findListByNickname(sysUserMapStruct.nicknameRequestParamToServiceBO(param));
		List<SysUserResponseDTO> dtoList = sysUserMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByMobilePhone", method = RequestMethod.POST)
	public ResponseEntity<?> listByMobilePhone(@Valid @RequestBody SysUserMobilePhoneRequestParam param) {
		List<SysUser> result = sysUserService.findListByMobilePhone(sysUserMapStruct.mobilePhoneRequestParamToServiceBO(param));
		List<SysUserResponseDTO> dtoList = sysUserMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByRealNameWhereLike", method = RequestMethod.POST)
	public ResponseEntity<?> listByRealNameWhereLike(@Valid @RequestBody SysUserRealNameLikeQueryParam param) {
		PageInfo result = sysUserService.findPageByRealNameWhereLike(sysUserMapStruct.realNameLikeQueryParamToServiceBO(param));
		List<SysUserResponseDTO> list = sysUserMapStruct.toResponseDTOList(result.getList());
		result.setList(list);
		return R.success(result);
	}


	@RequestPermission("sys_user")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysUserPageQueryParam param) {
		PageInfo result = sysUserService.findPageByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		List<SysUserResponseDTO> list = sysUserMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysUserResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_user")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysUserService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_user_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysUserCreateRequestParam param) {
		sysUserService.create(sysUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysUserUpdateRequestParam param) {
		sysUserService.update(sysUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysUser 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysUserService.batchUpdateState(sysUserMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_user_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysUserService.batchDelete(sysUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
