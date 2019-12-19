package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysParamTypeMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparamtype.SysParamTypeCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparamtype.SysParamTypePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparamtype.SysParamTypeUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysparamtype.SysParamTypeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import com.cdk8s.sculptor.service.SysParamTypeService;
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

@Api(value = "SysParamType API")
@Slf4j
@RestController
@RequestMapping("/api/sysParamType")
public class SysParamTypeController {

	@Autowired
	private SysParamTypeService sysParamTypeService;

	@Autowired
	private SysParamTypeMapStruct sysParamTypeMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamTypeResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysParamTypeMapStruct.toResponseDTO(sysParamTypeService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamTypeResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysParamTypeMapStruct.toResponseDTOList(sysParamTypeService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamTypeResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysParamTypePageQueryParam param) {
		List<SysParamType> result = sysParamTypeService.findListByServiceBO(sysParamTypeMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysParamTypeMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamTypeResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysParamTypePageQueryParam param) {
		PageInfo result = sysParamTypeService.findPageByServiceBO(sysParamTypeMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysParamTypeMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_param")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysParamTypeService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_param_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysParamTypeCreateRequestParam param) {
		sysParamTypeService.create(sysParamTypeMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysParamTypeUpdateRequestParam param) {
		sysParamTypeService.update(sysParamTypeMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysParamType 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysParamTypeService.batchUpdateState(sysParamTypeMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysParamType 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_param_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysParamTypeService.batchDelete(sysParamTypeMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
