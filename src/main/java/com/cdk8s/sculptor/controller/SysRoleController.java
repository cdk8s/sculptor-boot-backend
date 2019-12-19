package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysRoleMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRoleCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRolePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRoleUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysrole.SysRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import com.cdk8s.sculptor.service.SysRoleService;
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

@Api(value = "SysRole API")
@Slf4j
@RestController
@RequestMapping("/api/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysRoleMapStruct sysRoleMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysRoleResponseDTO.class)
	})
	@RequestPermission("sys_role")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysRoleMapStruct.toResponseDTO(sysRoleService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysRoleResponseDTO.class)
	})
	@RequestPermission("sys_role")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysRoleMapStruct.toResponseDTOList(sysRoleService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysRoleResponseDTO.class)
	})
	@RequestPermission("sys_role")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysRolePageQueryParam param) {
		List<SysRole> result = sysRoleService.findListByServiceBO(sysRoleMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysRoleMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysRoleResponseDTO.class)
	})
	@RequestPermission("sys_role")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysRolePageQueryParam param) {
		PageInfo result = sysRoleService.findPageByServiceBO(sysRoleMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysRoleMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_role")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysRoleService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysRole 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_role_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysRoleCreateRequestParam param) {
		sysRoleService.create(sysRoleMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysRole 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_role_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysRoleUpdateRequestParam param) {
		sysRoleService.update(sysRoleMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysRole 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_role_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysRoleService.batchUpdateState(sysRoleMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysRole 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_role_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysRoleService.batchDelete(sysRoleMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
