package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.RelDeptUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.reldeptuser.RelDeptUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import com.cdk8s.sculptor.service.RelDeptUserService;
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

@Api(value = "RelDeptUser API")
@Slf4j
@RestController
@RequestMapping("/api/relDeptUser")
public class RelDeptUserController {

	@Autowired
	private RelDeptUserService relDeptUserService;

	@Autowired
	private RelDeptUserMapStruct relDeptUserMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelDeptUserResponseDTO.class)
	})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(relDeptUserMapStruct.toResponseDTO(relDeptUserService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelDeptUserResponseDTO.class)
	})
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relDeptUserMapStruct.toResponseDTOList(relDeptUserService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelDeptUserResponseDTO.class)
	})
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody RelDeptUserPageQueryParam param) {
		List<RelDeptUser> result = relDeptUserService.findListByServiceBO(relDeptUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relDeptUserMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelDeptUserResponseDTO.class)
	})
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelDeptUserPageQueryParam param) {
		PageInfo result = relDeptUserService.findPageByServiceBO(relDeptUserMapStruct.pageQueryParamToServiceBO(param));
		result.setList(relDeptUserMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		relDeptUserService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dept_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelDeptUserCreateRequestParam param) {
		relDeptUserService.create(relDeptUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dept_create")
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelDeptUserBatchCreateRequestParam param) {
		relDeptUserService.batchCreate(relDeptUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_dept_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelDeptUserUpdateRequestParam param) {
		relDeptUserService.update(relDeptUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_dept_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relDeptUserService.batchDelete(relDeptUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
