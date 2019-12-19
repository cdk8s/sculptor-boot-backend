package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysPermissionMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syspermission.SysPermissionResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import com.cdk8s.sculptor.service.SysPermissionService;
import com.cdk8s.sculptor.util.UserInfoContext;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "SysPermission API")
@Slf4j
@RestController
@RequestMapping("/api/sysPermission")
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysPermissionMapStruct sysPermissionMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysPermissionMapStruct.toResponseDTO(sysPermissionService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.GET)
	public ResponseEntity<?> listTreeByParentId(@RequestParam Long parentId) {
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByParentId(parentId)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listTreeByParentIdNotButton", method = RequestMethod.GET)
	public ResponseEntity<?> listTreeByParentIdNotButton(@RequestParam Long parentId) {
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByParentIdNotButton(parentId)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestMapping(value = "/listTreeByCurrentUserPermission", method = RequestMethod.GET)
	public ResponseEntity<?> listTreeByCurrentUserPermission() {
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByUserIdNotButton(UserInfoContext.getCurrentUserId())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysPermissionPageQueryParam param) {
		List<SysPermission> result = sysPermissionService.findListByServiceBO(sysPermissionMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysPermissionMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysPermissionResponseDTO.class)
	})
	@RequestPermission("sys_permission")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysPermissionPageQueryParam param) {
		PageInfo result = sysPermissionService.findPageByServiceBO(sysPermissionMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysPermissionMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysPermissionService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_permission_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysPermissionCreateRequestParam param) {
		sysPermissionService.create(sysPermissionMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_permission_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysPermissionUpdateRequestParam param) {
		sysPermissionService.update(sysPermissionMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysPermission 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_permission_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysPermissionService.batchUpdateState(sysPermissionMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_permission_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysPermissionService.batchDelete(sysPermissionMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
