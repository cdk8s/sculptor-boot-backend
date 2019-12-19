package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.RelPermissionRoleMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRolePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.relpermissionrole.RelPermissionRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.service.RelPermissionRoleService;
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

@Api(value = "RelPermissionRole API")
@Slf4j
@RestController
@RequestMapping("/api/relPermissionRole")
public class RelPermissionRoleController {

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	@Autowired
	private RelPermissionRoleMapStruct relPermissionRoleMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelPermissionRoleResponseDTO.class)
	})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(relPermissionRoleMapStruct.toResponseDTO(relPermissionRoleService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelPermissionRoleResponseDTO.class)
	})
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relPermissionRoleMapStruct.toResponseDTOList(relPermissionRoleService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelPermissionRoleResponseDTO.class)
	})
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody RelPermissionRolePageQueryParam param) {
		List<RelPermissionRole> result = relPermissionRoleService.findListByServiceBO(relPermissionRoleMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relPermissionRoleMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelPermissionRoleResponseDTO.class)
	})
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelPermissionRolePageQueryParam param) {
		PageInfo result = relPermissionRoleService.findPageByServiceBO(relPermissionRoleMapStruct.pageQueryParamToServiceBO(param));
		result.setList(relPermissionRoleMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		relPermissionRoleService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_permission_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelPermissionRoleCreateRequestParam param) {
		relPermissionRoleService.create(relPermissionRoleMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_permission_create")
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelPermissionRoleBatchCreateRequestParam param) {
		relPermissionRoleService.batchCreate(relPermissionRoleMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_permission_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelPermissionRoleUpdateRequestParam param) {
		relPermissionRoleService.update(relPermissionRoleMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relPermissionRole 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_permission_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relPermissionRoleService.batchDelete(relPermissionRoleMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
