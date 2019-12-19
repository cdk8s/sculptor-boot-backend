package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysParamMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysparam.SysParamResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import com.cdk8s.sculptor.service.SysParamService;
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

@Api(value = "SysParam API")
@Slf4j
@RestController
@RequestMapping("/api/sysParam")
public class SysParamController {

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private SysParamMapStruct sysParamMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysParamMapStruct.toResponseDTO(sysParamService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysParamMapStruct.toResponseDTOList(sysParamService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysParamPageQueryParam param) {
		List<SysParam> result = sysParamService.findListByServiceBO(sysParamMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysParamMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysParamResponseDTO.class)
	})
	@RequestPermission("sys_param")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysParamPageQueryParam param) {
		PageInfo result = sysParamService.findPageByServiceBO(sysParamMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysParamMapStruct.toResponseDTOList(result.getList()));
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
		sysParamService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_param_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysParamCreateRequestParam param) {
		sysParamService.create(sysParamMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysParamUpdateRequestParam param) {
		sysParamService.update(sysParamMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysParam 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_param_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysParamService.batchUpdateState(sysParamMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysParam 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_param_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysParamService.batchDelete(sysParamMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
