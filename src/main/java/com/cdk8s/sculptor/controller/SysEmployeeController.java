package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysEmployeeMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysemployee.SysEmployeeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
import com.cdk8s.sculptor.service.SysEmployeeService;
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

@Api(value = "SysEmployee API")
@Slf4j
@RestController
@RequestMapping("/api/sysEmployee")
public class SysEmployeeController {

	@Autowired
	private SysEmployeeService sysEmployeeService;

	@Autowired
	private SysEmployeeMapStruct sysEmployeeMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEmployeeResponseDTO.class)
	})
	@RequestPermission("sys_employee")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysEmployeeMapStruct.toResponseDTO(sysEmployeeService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEmployeeResponseDTO.class)
	})
	@RequestPermission("sys_employee")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysEmployeeMapStruct.toResponseDTOList(sysEmployeeService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEmployeeResponseDTO.class)
	})
	@RequestPermission("sys_employee")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysEmployeePageQueryParam param) {
		List<SysEmployee> result = sysEmployeeService.findListByServiceBO(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysEmployeeMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEmployeeResponseDTO.class)
	})
	@RequestPermission("sys_employee")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysEmployeePageQueryParam param) {
		PageInfo result = sysEmployeeService.findPageByServiceBO(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysEmployeeMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEmployeeResponseDTO.class)
	})
	@RequestPermission("sys_employee")
	@RequestMapping(value = "/pageToUser", method = RequestMethod.POST)
	public ResponseEntity<?> pageToUser(@Valid @RequestBody SysEmployeePageQueryParam param) {
		PageInfo result = sysEmployeeService.findPageByServiceBOToUser(sysEmployeeMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysEmployeeMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_employee")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysEmployeeService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_employee_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysEmployeeCreateRequestParam param) {
		sysEmployeeService.create(sysEmployeeMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_employee_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysEmployeeUpdateRequestParam param) {
		sysEmployeeService.update(sysEmployeeMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysEmployee 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_employee_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysEmployeeService.batchDelete(sysEmployeeMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
