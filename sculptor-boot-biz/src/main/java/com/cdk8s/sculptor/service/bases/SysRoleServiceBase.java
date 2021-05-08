/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysRoleServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapper.SysRoleMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysRoleMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.*;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysRoleService")
public class SysRoleServiceBase {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleMapStruct sysRoleMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysRole findOneById(IdServiceBO serviceBO) {
		return sysRoleMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysRole findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysRoleMapper.selectById(mapperBO);
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByRoleCode(SysRoleRoleCodeServiceBO serviceBO) {
		return sysRoleMapper.selectByRoleCode(sysRoleMapStruct.roleCodeServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByRoleCodeList(SysRoleRoleCodeListServiceBO serviceBO) {
		return sysRoleMapper.selectByRoleCodeList(sysRoleMapStruct.roleCodeListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByIdList(IdListServiceBO serviceBO) {
		return sysRoleMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysRole findOneByServiceBO(SysRolePageQueryServiceBO serviceBO) {
		List<SysRole> list = sysRoleMapper.selectByPageQueryMapperBo(sysRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByServiceBO(SysRolePageQueryServiceBO serviceBO) {
		return sysRoleMapper.selectByPageQueryMapperBo(sysRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysRolePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysRoleMapper.selectByPageQueryMapperBo(sysRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
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
		SysRole entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysRoleMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysRoleUpdateServiceBO serviceBO) {
		return sysRoleMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysRoleMapper.updateStateEnumByIdList(sysRoleMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysRoleMapper.updateDeleteEnumByIdList(sysRoleMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
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
		return sysRoleMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

