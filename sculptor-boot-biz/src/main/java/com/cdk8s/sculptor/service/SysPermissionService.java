/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysPermissionMapper;
import com.cdk8s.sculptor.mapper.ext.SysPermissionMapperExt;
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysPermissionMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdAndIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleRoleIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.RoleIdListServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.service.bases.SysPermissionServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CacheConfig(cacheNames = "SysPermissionService")
@Service
public class SysPermissionService extends SysPermissionServiceBase {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysPermissionMapperExt sysPermissionMapperExt;

	@Autowired
	private SysPermissionMapStruct sysPermissionMapStruct;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<String> findPermissionCodeListByUserId(UserIdServiceBO serviceBO) {
		List<SysPermission> sysPermissionList = findListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(sysPermissionList)) {
			return CollectionUtil.emptyList();
		}

		return sysPermissionList.stream()
				.map(SysPermission::getPermissionCode)
				.collect(Collectors.toList());
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByParentIdNotButton(ParentIdServiceBO serviceBO) {
		ParentIdMapperBO mapperBO = parentIdAndParentIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysPermissionMapperExt.selectByParentIdNotButton(mapperBO);
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findTreeListByParentIdNotButton(ParentIdServiceBO serviceBO) {
		if (null == serviceBO.getParentId()) {
			serviceBO.setParentId(GlobalConstant.TOP_PARENT_ID);
		}

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getParentId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysPermission parentObject = findOneById(idServiceBO);
		List<SysPermission> childrenList = findListByParentIdNotButton(serviceBO);
		recursionToListNotButton(parentObject, childrenList);
		return childrenList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByUserIdNotButton(UserIdServiceBO serviceBO) {
		if (null == serviceBO.getUserId()) {
			throw new BusinessException("获取用户权限树的用户 ID 不能为空");
		}

		// 查询当前用户所有的权限（平级结构）
		List<Long> permissionIdListByUserId = findPermissionIdListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(permissionIdListByUserId)) {
			return CollectionUtil.emptyList();
		}

		IdListMapperBO idListMapperBO = new IdListMapperBO();
		idListMapperBO.setIdList(permissionIdListByUserId);
		idListMapperBO.setTenantId(serviceBO.getTenantId());
		idListMapperBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		idListMapperBO.setStateEnum(StateEnum.ENABLE.getCode());

		return sysPermissionMapperExt.selectByIdListNotButton(idListMapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findTreeListByUserIdNotButton(UserIdServiceBO serviceBO) {
		if (null == serviceBO.getUserId()) {
			throw new BusinessException("获取用户权限树的用户 ID 不能为空");
		}

		// 查询当前用户所有的权限（平级结构）
		List<Long> permissionIdListByUserId = findPermissionIdListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(permissionIdListByUserId)) {
			return CollectionUtil.emptyList();
		}

		// 查询出所有权限的树结构
		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(GlobalConstant.TOP_PARENT_ID);
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysPermission parentObject = findOneById(idServiceBO);
		List<SysPermission> childrenList = sysPermissionMapperExt.selectByParentIdAndIdListNotButton(new ParentIdAndIdListMapperBO(GlobalConstant.TOP_PARENT_ID, permissionIdListByUserId));

		recursionToListByUserPermissionIdListNotButton(parentObject, childrenList, permissionIdListByUserId);

		return childrenList;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<Long> findPermissionIdListByUserId(UserIdServiceBO serviceBO) {
		List<SysPermission> sysPermissionList = findListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(sysPermissionList)) {
			return CollectionUtil.emptyList();
		}

		return sysPermissionList.stream()
				.map(BaseEntity::getId)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByUserId(UserIdServiceBO serviceBO) {
		List<Long> roleIdList = relRoleUserService.findRoleIdListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(roleIdList)) {
			return CollectionUtil.emptyList();
		}

		RoleIdListServiceBO roleIdListServiceBO = new RoleIdListServiceBO();
		roleIdListServiceBO.setRoleIdList(roleIdList);
		roleIdListServiceBO.setTenantId(serviceBO.getTenantId());
		roleIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		return findListByRoleIdList(roleIdListServiceBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByRoleIdList(RoleIdListServiceBO serviceBO) {
		RelPermissionRoleRoleIdListServiceBO relPermissionRoleRoleIdListServiceBO = new RelPermissionRoleRoleIdListServiceBO();
		relPermissionRoleRoleIdListServiceBO.setRoleIdList(serviceBO.getRoleIdList());
		relPermissionRoleRoleIdListServiceBO.setTenantId(serviceBO.getTenantId());
		List<RelPermissionRole> relPermissionRoleList = relPermissionRoleService.findListByRoleIdList(relPermissionRoleRoleIdListServiceBO);
		if (CollectionUtil.isEmpty(relPermissionRoleList)) {
			return CollectionUtil.emptyList();
		}

		// 用 set 去重
		Set<Long> permissionIdSet = relPermissionRoleList.stream()
				.map(RelPermissionRole::getPermissionId)
				.collect(Collectors.toSet());

		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(CollectionUtil.setToList(permissionIdSet));
		idListServiceBO.setTenantId(serviceBO.getTenantId());
		idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		return findListByIdList(idListServiceBO);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private void recursionToListNotButton(SysPermission parentObject, List<SysPermission> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysPermission entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}

				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entity.getId());
				// parentIdServiceBO.setTenantId(entity.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
				List<SysPermission> children = findListByParentIdNotButton(parentIdServiceBO);
				recursionToListNotButton(entity, children);
			}
		}
	}

	private void recursionToListByUserPermissionIdListNotButton(SysPermission parentObject, List<SysPermission> childrenList, List<Long> permissionIdList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysPermission entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysPermission> children = sysPermissionMapperExt.selectByParentIdAndIdListNotButton(new ParentIdAndIdListMapperBO(entity.getId(), permissionIdList));
				recursionToListByUserPermissionIdListNotButton(entity, children, permissionIdList);
			}
		}
	}

	// =====================================私有方法 end=====================================

}

