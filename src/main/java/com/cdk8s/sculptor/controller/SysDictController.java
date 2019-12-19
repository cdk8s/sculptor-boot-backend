package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysDictMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdict.SysDictCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdict.SysDictPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdict.SysDictUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysdict.SysDictResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import com.cdk8s.sculptor.service.SysDictService;
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

@Api(value = "SysDict API")
@Slf4j
@RestController
@RequestMapping("/api/sysDict")
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;

	@Autowired
	private SysDictMapStruct sysDictMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDictResponseDTO.class)
	})
	@RequestPermission("sys_dict")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysDictMapStruct.toResponseDTO(sysDictService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDictResponseDTO.class)
	})
	@RequestPermission("sys_dict")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysDictMapStruct.toResponseDTOList(sysDictService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDictResponseDTO.class)
	})
	@RequestPermission("sys_dict")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysDictPageQueryParam param) {
		List<SysDict> result = sysDictService.findListByServiceBO(sysDictMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysDictMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDictResponseDTO.class)
	})
	@RequestPermission("sys_dict")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysDictPageQueryParam param) {
		PageInfo result = sysDictService.findPageByServiceBO(sysDictMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysDictMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_dict")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysDictService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysDict 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dict_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysDictCreateRequestParam param) {
		sysDictService.create(sysDictMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysDict 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_dict_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysDictUpdateRequestParam param) {
		sysDictService.update(sysDictMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysDict 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_dict_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysDictService.batchUpdateState(sysDictMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysDict 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_dict_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysDictService.batchDelete(sysDictMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
