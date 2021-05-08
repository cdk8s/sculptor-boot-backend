/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.controller.bases.SysUserControllerBase;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.excel.service.ExcelDataService;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserUserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUserIdServiceBO;
import com.cdk8s.sculptor.pojo.dto.excel.SysUserExcelDTO;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserUpdatePasswordRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysuser.SysUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.*;
import com.cdk8s.sculptor.service.*;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController extends SysUserControllerBase {

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

	@Autowired
	private ExcelDataService<SysUserExcelDTO> excelExcelDataService;

	// =====================================查询业务 start=====================================


	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public void excel(@Valid @RequestBody SysUserPageQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		PageInfo page = sysUserService.findPageByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		List<SysUser> sysUserList = page.getList();
		if (CollectionUtil.isEmpty(sysUserList)) {
			throw new BusinessException("无数据可导出");
		}

		List<SysUserExcelDTO> dtoList = new ArrayList<>();
		for (SysUser entity : sysUserList) {
			SysUserExcelDTO excelDTO = new SysUserExcelDTO();
			excelDTO.setUsername(entity.getUsername());
			excelDTO.setRealName(entity.getRealName());
			excelDTO.setUserEmail(entity.getUserEmail());
			excelDTO.setTelephone(entity.getTelephone());
			excelDTO.setMobilePhone(entity.getMobilePhone());
			dtoList.add(excelDTO);
		}

		// width 最大值是：65280
		// 25 表示该列最大字符数，256 是固定值来着，具体可以看源码。
		Map<Integer, Integer> columnWidthMap = new HashMap<>();
		columnWidthMap.put(0, 25 * 256);
		columnWidthMap.put(1, 12 * 256);
		columnWidthMap.put(2, 12 * 256);
		columnWidthMap.put(3, 12 * 256);
		columnWidthMap.put(4, 12 * 256);

		excelExcelDataService.exportList("文件名称", "sheet名称", columnWidthMap, dtoList, SysUserExcelDTO.class, response);
	}

	@RequestMapping(value = "/userinfo", method = RequestMethod.POST)
	public ResponseEntity<?> userinfo() {
		return R.success(UserInfoContext.getCurrentUser());
	}


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

	@EventLog(message = "重置 sysUser 密码", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody IdListRequestParam param) {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(param.getIdList());
		sysUserService.resetPassword(idListServiceBO);
		return R.success();
	}

	@EventLog(message = "修改 sysUser 密码", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_user_update")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public ResponseEntity<?> updatePassword(@Valid @RequestBody SysUserUpdatePasswordRequestParam param) {
		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(param.getIdList());
		idListServiceBO.setTenantId(param.getTenantId());

		List<SysUser> sysUserList = sysUserService.findListByIdList(idListServiceBO);
		if (CollectionUtil.isEmpty(sysUserList)) {
			throw new BusinessException("更新失败，找不到该用户");
		}

		List<Long> userIdList = sysUserList.stream()
				.filter(x -> x.getId() != null)
				.map(key -> key.getId())
				.collect(Collectors.toList());

		idListServiceBO.setIdList(userIdList);
		sysUserService.updatePassword(idListServiceBO, param.getPassword());
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private void buildDTO(List<SysUserResponseDTO> responseDTOList) {
		if (CollectionUtil.isNotEmpty(responseDTOList)) {
			for (SysUserResponseDTO dto : responseDTOList) {
				RelDeptUserUserIdServiceBO relDeptUserUserIdServiceBO = new RelDeptUserUserIdServiceBO();
				relDeptUserUserIdServiceBO.setUserId(dto.getId());
				relDeptUserUserIdServiceBO.setTenantId(dto.getTenantId());
				List<RelDeptUser> relDeptUserList = relDeptUserService.findListByUserId(relDeptUserUserIdServiceBO);
				if (CollectionUtil.isNotEmpty(relDeptUserList)) {
					List<Long> deptIdList = relDeptUserList.stream()
							.filter(x -> x.getDeptId() != null)
							.map(RelDeptUser::getDeptId)
							.collect(Collectors.toList());

					IdListServiceBO idListServiceBO = new IdListServiceBO();
					idListServiceBO.setIdList(deptIdList);
					idListServiceBO.setTenantId(dto.getTenantId());
					idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
					List<SysDept> sysDeptList = sysDeptService.findListByIdList(idListServiceBO);
					List<String> deptNameList = sysDeptList.stream()
							.filter(x -> StringUtil.isNotBlank(x.getDeptName()))
							.map(SysDept::getDeptName)
							.collect(Collectors.toList());
					dto.setDeptNameList(deptNameList);
				}

				RelRoleUserUserIdServiceBO relRoleUserUserIdServiceBO = new RelRoleUserUserIdServiceBO();
				relRoleUserUserIdServiceBO.setUserId(dto.getId());
				relRoleUserUserIdServiceBO.setTenantId(dto.getTenantId());
				List<RelRoleUser> relRoleUserList = relRoleUserService.findListByUserId(relRoleUserUserIdServiceBO);
				if (CollectionUtil.isNotEmpty(relRoleUserList)) {
					List<Long> roleIdList = relRoleUserList.stream()
							.filter(x -> x.getRoleId() != null)
							.map(RelRoleUser::getRoleId)
							.collect(Collectors.toList());

					IdListServiceBO idListServiceBO = new IdListServiceBO();
					idListServiceBO.setIdList(roleIdList);
					idListServiceBO.setTenantId(dto.getTenantId());
					idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
					List<SysRole> sysRoleList = sysRoleService.findListByIdList(idListServiceBO);
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
