package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.UserIdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.relroleuser.RelRoleUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.service.RelRoleUserService;
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

@Api(value = "RelRoleUser API")
@Slf4j
@RestController
@RequestMapping("/api/relRoleUser")
public class RelRoleUserController {

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelRoleUserResponseDTO.class)
	})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(relRoleUserMapStruct.toResponseDTO(relRoleUserService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelRoleUserResponseDTO.class)
	})
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relRoleUserMapStruct.toResponseDTOList(relRoleUserService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelRoleUserResponseDTO.class)
	})
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody RelRoleUserPageQueryParam param) {
		List<RelRoleUser> result = relRoleUserService.findListByServiceBO(relRoleUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relRoleUserMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = RelRoleUserResponseDTO.class)
	})
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelRoleUserPageQueryParam param) {
		PageInfo result = relRoleUserService.findPageByServiceBO(relRoleUserMapStruct.pageQueryParamToServiceBO(param));
		result.setList(relRoleUserMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		relRoleUserService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_role_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelRoleUserCreateRequestParam param) {
		relRoleUserService.create(relRoleUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_role_create")
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelRoleUserBatchCreateRequestParam param) {
		relRoleUserService.batchCreate(relRoleUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_role_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelRoleUserUpdateRequestParam param) {
		relRoleUserService.update(relRoleUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_role_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relRoleUserService.batchDelete(relRoleUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_role_delete")
	@RequestMapping(value = "/batchDeleteByUserIdList", method = RequestMethod.POST)
	public ResponseEntity<?> batchDeleteByUserIdList(@Valid @RequestBody UserIdListRequestParam param) {
		relRoleUserService.batchDeleteByUserIdList(param.getUserIdList());
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
