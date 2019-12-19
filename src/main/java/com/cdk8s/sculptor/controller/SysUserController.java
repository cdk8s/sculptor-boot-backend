package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysuser.SysUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.*;
import com.cdk8s.sculptor.service.*;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.StringUtil;
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
import java.util.stream.Collectors;

@Api(value = "SysUser API")
@Slf4j
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private RelDeptUserService relDeptUserService;

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserMapStruct sysUserMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public ResponseEntity<?> userinfo() {
		return R.success(UserInfoContext.getCurrentUser());
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysUserMapStruct.toResponseDTO(sysUserService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysUserMapStruct.toResponseDTOList(sysUserService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysUserPageQueryParam param) {
		List<SysUser> result = sysUserService.findListByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysUserMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysUserPageQueryParam param) {
		PageInfo result = sysUserService.findPageByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysUserMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/pageToDept", method = RequestMethod.POST)
	public ResponseEntity<?> pageToDept(@Valid @RequestBody SysUserPageQueryParam param) {
		PageInfo result = sysUserService.findPageByServiceBOToDept(sysUserMapStruct.pageQueryParamToServiceBO(param));
		List<SysUserResponseDTO> responseDTOList = sysUserMapStruct.toResponseDTOList(result.getList());
		// 查询出对应用户的多个角色名称，多个部门名称
		buildDTO(responseDTOList);
		result.setList(responseDTOList);
		return R.success(result);
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})
	@RequestPermission("sys_user")
	@RequestMapping(value = "/pageToRole", method = RequestMethod.POST)
	public ResponseEntity<?> pageToRole(@Valid @RequestBody SysUserPageQueryParam param) {
		PageInfo result = sysUserService.findPageByServiceBOToRole(sysUserMapStruct.pageQueryParamToServiceBO(param));
		List<SysUserResponseDTO> responseDTOList = sysUserMapStruct.toResponseDTOList(result.getList());
		// 查询出对应用户的多个角色名称，多个部门名称
		buildDTO(responseDTOList);
		result.setList(responseDTOList);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_user")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysUserService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "重置 sysUser 密码", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody IdListRequestParam param) {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysUserService.resetPassword(param.getIdList());
		return R.success();
	}

	@EventLog(message = "创建 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_user_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysUserCreateRequestParam param) {
		sysUserService.create(sysUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysUserUpdateRequestParam param) {
		sysUserService.update(sysUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysUser 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysUserService.batchUpdateState(sysUserMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_user_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysUserService.batchDelete(sysUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================
	private void buildDTO(List<SysUserResponseDTO> responseDTOList) {
		if (CollectionUtil.isNotEmpty(responseDTOList)) {
			for (SysUserResponseDTO dto : responseDTOList) {
				List<RelDeptUser> relDeptUserList = relDeptUserService.findListByUserId(dto.getId());
				if (CollectionUtil.isNotEmpty(relDeptUserList)) {
					List<Long> deptIdList = relDeptUserList.stream()
							.filter(x -> x.getDeptId() != null)
							.map(RelDeptUser::getDeptId)
							.collect(Collectors.toList());

					List<SysDept> sysDeptList = sysDeptService.findListByIdList(deptIdList);
					List<String> deptNameList = sysDeptList.stream()
							.filter(x -> StringUtil.isNotBlank(x.getDeptName()))
							.map(SysDept::getDeptName)
							.collect(Collectors.toList());
					dto.setDeptNameList(deptNameList);
				}

				List<RelRoleUser> relRoleUserList = relRoleUserService.findListByUserId(dto.getId());
				if (CollectionUtil.isNotEmpty(relRoleUserList)) {
					List<Long> roleIdList = relRoleUserList.stream()
							.filter(x -> x.getRoleId() != null)
							.map(RelRoleUser::getRoleId)
							.collect(Collectors.toList());

					List<SysRole> sysRoleList = sysRoleService.findListByIdList(roleIdList);
					List<String> roleNameList = sysRoleList.stream()
							.filter(x -> StringUtil.isNotBlank(x.getRoleName()))
							.map(SysRole::getRoleName)
							.collect(Collectors.toList());
					dto.setRoleNameList(roleNameList);
				}
			}
		}
	}
	// =====================================私有方法 end=====================================

}
