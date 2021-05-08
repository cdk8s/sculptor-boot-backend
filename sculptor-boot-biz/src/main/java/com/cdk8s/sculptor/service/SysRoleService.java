/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysRoleService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysRoleMapper;
import com.cdk8s.sculptor.mapper.ext.SysRoleMapperExt;
import com.cdk8s.sculptor.mapstruct.SysRoleMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserRoleIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleRoleCodeServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import com.cdk8s.sculptor.service.bases.SysRoleServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DefaultDataUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysRoleService")
@Service
public class SysRoleService extends SysRoleServiceBase {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleMapperExt sysRoleMapperExt;

	@Autowired
	private SysRoleMapStruct sysRoleMapStruct;

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	// =====================================查询业务 start=====================================


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByUserId(UserIdServiceBO serviceBO) {
		List<Long> roleIdList = relRoleUserService.findRoleIdListByUserId(serviceBO);
		if (CollectionUtil.isEmpty(roleIdList)) {
			return CollectionUtil.emptyList();
		}

		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(roleIdList);
		idListServiceBO.setTenantId(serviceBO.getTenantId());
		idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		return findListByIdList(idListServiceBO);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 该方法名不允许修改，被用在反射上
	 */
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void cacheEvict() {
		// 用来主动清除所有缓存数据

	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysRoleCreateServiceBO serviceBO) {
		SysRoleRoleCodeServiceBO sysRoleRoleCodeServiceBO = new SysRoleRoleCodeServiceBO();
		sysRoleRoleCodeServiceBO.setRoleCode(serviceBO.getRoleCode());
		sysRoleRoleCodeServiceBO.setTenantId(serviceBO.getTenantId());
		sysRoleRoleCodeServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysRole> listByRoleCode = findListByRoleCode(sysRoleRoleCodeServiceBO);
		if (CollectionUtil.isNotEmpty(listByRoleCode)) {
			throw new BusinessException("已存在该角色编码");
		}
		return sysRoleMapper.insert(initCreateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultRoleIdList(), serviceBO.getIdList());
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除，建议禁用其状态");
		}

		// 先判断该角色是否有用户在使用
		RelRoleUserRoleIdListServiceBO relRoleUserRoleIdListServiceBO = new RelRoleUserRoleIdListServiceBO();
		relRoleUserRoleIdListServiceBO.setRoleIdList(serviceBO.getIdList());
		relRoleUserRoleIdListServiceBO.setTenantId(serviceBO.getTenantId());
		relRoleUserRoleIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<RelRoleUser> relRoleUserList = relRoleUserService.findListByRoleIdList(relRoleUserRoleIdListServiceBO);
		if (CollectionUtil.isNotEmpty(relRoleUserList)) {
			throw new BusinessException("被删除的角色还有用户在使用，请先解绑角色关联的用户");
		}

		return sysRoleMapper.updateDeleteEnumByIdList(sysRoleMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysRoleUpdateServiceBO serviceBO) {
		return sysRoleMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysRole initCreateBasicParam(SysRoleCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBoolTemplateEnum()) {
			serviceBO.setBoolTemplateEnum(1);
		}

		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}
		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}


		return sysRoleMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysRole initUpdateBasicParam(SysRoleUpdateServiceBO serviceBO) {
		SysRole entity = sysRoleMapStruct.updateServiceBOToEntity(serviceBO);
		// 角色编码不可修改
		entity.setRoleCode(null);
		return entity;
	}

	// =====================================私有方法 end=====================================

}

