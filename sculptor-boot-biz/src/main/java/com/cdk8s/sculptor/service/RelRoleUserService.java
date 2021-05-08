/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelRoleUserMapper;
import com.cdk8s.sculptor.mapper.ext.RelRoleUserMapperExt;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.mapstruct.UserIdAndUserIdListMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdListToDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserRoleIdAndUserIdToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUserIdListToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.bases.RelRoleUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.service.bases.RelRoleUserServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RelRoleUserService extends RelRoleUserServiceBase {

	@Autowired
	private RelRoleUserMapper relRoleUserMapper;

	@Autowired
	private RelRoleUserMapperExt relRoleUserMapperExt;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	@Autowired
	private UserIdAndUserIdListMapStruct userIdAndUserIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public List<Long> findRoleIdListByUserId(UserIdServiceBO serviceBO) {
		RelRoleUserUserIdServiceBO relRoleUserUserIdServiceBO = new RelRoleUserUserIdServiceBO();
		relRoleUserUserIdServiceBO.setUserId(serviceBO.getUserId());
		relRoleUserUserIdServiceBO.setTenantId(serviceBO.getTenantId());
		relRoleUserUserIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<RelRoleUser> relRoleUserList = findListByUserId(relRoleUserUserIdServiceBO);
		if (CollectionUtil.isEmpty(relRoleUserList)) {
			return CollectionUtil.emptyList();
		}

		return relRoleUserList.stream()
				.map(RelRoleUser::getRoleId)
				.collect(Collectors.toList());
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreate(RelRoleUserBatchCreateServiceBO serviceBO) {

		List<Long> userIdList = serviceBO.getUserIdList();
		List<Long> roleIdList = serviceBO.getRoleIdList();

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个用户");
		}

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个角色");
		}

		// 先删除用户所有旧数据，然后再新增新数据
		RelRoleUserUserIdListToDeleteServiceBO relRoleUserUserIdListToDeleteServiceBO = new RelRoleUserUserIdListToDeleteServiceBO();
		relRoleUserUserIdListToDeleteServiceBO.setUserIdList(userIdList);
		relRoleUserUserIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		relRoleUserUserIdListToDeleteServiceBO.setDeleteDate(serviceBO.getCreateDate());
		relRoleUserUserIdListToDeleteServiceBO.setDeleteUserId(serviceBO.getCreateUserId());
		batchDeleteByUserIdList(relRoleUserUserIdListToDeleteServiceBO);

		List<RelRoleUser> relRoleUserList = new ArrayList<>();
		for (Long userId : userIdList) {
			for (Long roleId : roleIdList) {
				RelRoleUserCreateServiceBO relRoleUserCreateServiceBO = new RelRoleUserCreateServiceBO();
				relRoleUserCreateServiceBO.setRoleId(roleId);
				relRoleUserCreateServiceBO.setUserId(userId);
				relRoleUserList.add(initCreateBasicParam(relRoleUserCreateServiceBO));
			}
		}

		int result = relRoleUserMapper.batchInsertList(relRoleUserList);

		if (result > 0) {
			sysUserService.cacheEvict();
			sysRoleService.cacheEvict();
			sysPermissionService.cacheEvict();
		}

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(RelRoleUserUserIdListToDeleteServiceBO serviceBO) {
		RelRoleUserUserIdListToDeleteMapperBO mapperBO = relRoleUserMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO);
		int result = relRoleUserMapper.deleteByUserIdList(mapperBO);

		if (result > 0) {
			sysUserService.cacheEvict();
			sysRoleService.cacheEvict();
			sysPermissionService.cacheEvict();
		}

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByRoleIdAndUserId(RelRoleUserRoleIdAndUserIdToDeleteServiceBO serviceBO) {
		sysUserService.cacheEvict();
		return relRoleUserMapper.deleteByRoleIdAndUserId(relRoleUserMapStruct.deleteByRoleIdAndUserIdServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================
	private RelRoleUser initCreateBasicParam(RelRoleUserCreateServiceBO serviceBO) {

		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}


		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return relRoleUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

