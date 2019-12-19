package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysDeptMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdept.SysDeptCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdept.SysDeptPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdept.SysDeptUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysdept.SysDeptResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import com.cdk8s.sculptor.service.SysDeptService;
import com.cdk8s.sculptor.service.SysUserService;
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

@Api(value = "SysDept API")
@Slf4j
@RestController
@RequestMapping("/api/sysDept")
public class SysDeptController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysDeptMapStruct sysDeptMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDeptResponseDTO.class)
	})
	@RequestPermission("sys_dept")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		SysDeptResponseDTO sysDeptResponseDTO = sysDeptMapStruct.toResponseDTO(sysDeptService.findOneById(id));
		sysDeptResponseDTO.setLeaderRealName(sysUserService.findOneById(sysDeptResponseDTO.getLeaderUserId()).getRealName());
		return R.success(sysDeptResponseDTO);
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDeptResponseDTO.class)
	})
	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDeptResponseDTO.class)
	})
	@RequestPermission("sys_dept")
	@RequestMapping(value = "/listTreeByParentId", method = RequestMethod.GET)
	public ResponseEntity<?> listTreeByParentId(@RequestParam Long parentId) {
		return R.success(sysDeptMapStruct.toResponseDTOList(sysDeptService.findTreeListByParentId(parentId)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDeptResponseDTO.class)
	})
	@RequestPermission("sys_dept")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysDeptPageQueryParam param) {
		List<SysDept> result = sysDeptService.findListByServiceBO(sysDeptMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysDeptMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysDeptResponseDTO.class)
	})
	@RequestPermission("sys_dept")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysDeptPageQueryParam param) {
		PageInfo result = sysDeptService.findPageByServiceBO(sysDeptMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysDeptMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_dept")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysDeptService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dept_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysDeptCreateRequestParam param) {
		sysDeptService.create(sysDeptMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_dept_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysDeptUpdateRequestParam param) {
		sysDeptService.update(sysDeptMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysDept 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_dept_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysDeptService.batchUpdateState(sysDeptMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysDept 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_dept_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysDeptService.batchDelete(sysDeptMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
