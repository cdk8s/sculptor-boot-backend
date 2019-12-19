package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysloginlog.SysLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.service.SysLoginLogService;
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

@Api(value = "SysLoginLog API")
@Slf4j
@RestController
@RequestMapping("/api/sysLoginLog")
public class SysLoginLogController {

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysLoginLogResponseDTO.class)
	})
	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysLoginLogMapStruct.toResponseDTO(sysLoginLogService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysLoginLogResponseDTO.class)
	})
	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysLoginLogMapStruct.toResponseDTOList(sysLoginLogService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysLoginLogResponseDTO.class)
	})
	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysLoginLogPageQueryParam param) {
		List<SysLoginLog> result = sysLoginLogService.findListByServiceBO(sysLoginLogMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysLoginLogMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysLoginLogResponseDTO.class)
	})
	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysLoginLogPageQueryParam param) {
		PageInfo result = sysLoginLogService.findPageByServiceBO(sysLoginLogMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysLoginLogMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_login_log")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysLoginLogService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_login_log_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysLoginLogCreateRequestParam param) {
		sysLoginLogService.create(sysLoginLogMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_login_log_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysLoginLogUpdateRequestParam param) {
		sysLoginLogService.update(sysLoginLogMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysLoginLog 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_login_log_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysLoginLogService.batchDelete(sysLoginLogMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
