package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysEventLogMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syseventlog.SysEventLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import com.cdk8s.sculptor.service.SysEventLogService;
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

@Api(value = "SysEventLog API")
@Slf4j
@RestController
@RequestMapping("/api/sysEventLog")
public class SysEventLogController {

	@Autowired
	private SysEventLogService sysEventLogService;

	@Autowired
	private SysEventLogMapStruct sysEventLogMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEventLogResponseDTO.class)
	})
	@RequestPermission("sys_event_log")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysEventLogMapStruct.toResponseDTO(sysEventLogService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEventLogResponseDTO.class)
	})
	@RequestPermission("sys_event_log")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysEventLogMapStruct.toResponseDTOList(sysEventLogService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEventLogResponseDTO.class)
	})
	@RequestPermission("sys_event_log")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysEventLogPageQueryParam param) {
		List<SysEventLog> result = sysEventLogService.findListByServiceBO(sysEventLogMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysEventLogMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysEventLogResponseDTO.class)
	})
	@RequestPermission("sys_event_log")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysEventLogPageQueryParam param) {
		PageInfo result = sysEventLogService.findPageByServiceBO(sysEventLogMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysEventLogMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_event_log")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysEventLogService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysEventLog 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_event_log_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysEventLogCreateRequestParam param) {
		sysEventLogService.create(sysEventLogMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysEventLog 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_event_log_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysEventLogUpdateRequestParam param) {
		sysEventLogService.update(sysEventLogMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 sysEventLog 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_event_log_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysEventLogService.batchDelete(sysEventLogMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
