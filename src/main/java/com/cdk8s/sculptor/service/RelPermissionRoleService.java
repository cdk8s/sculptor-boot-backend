package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelPermissionRoleMapper;
import com.cdk8s.sculptor.mapstruct.RelPermissionRoleMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRolePermissionIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRoleRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRolePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "RelPermissionRoleService")
@Service
public class RelPermissionRoleService {

	@Autowired
	private RelPermissionRoleMapper relPermissionRoleMapper;

	@Autowired
	private RelPermissionRoleMapStruct relPermissionRoleMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelPermissionRole findOneById(Long id) {
		return relPermissionRoleMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelPermissionRole findOneByPermissionId(Long permissionId) {
		return relPermissionRoleMapper.selectOneByPermissionId(new RelPermissionRolePermissionIdMapperBO(permissionId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByPermissionId(Long permissionId) {
		return relPermissionRoleMapper.selectByPermissionId(new RelPermissionRolePermissionIdMapperBO(permissionId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByPermissionIdList(List<Long> permissionIdList) {
		return relPermissionRoleMapper.selectByPermissionIdList(new RelPermissionRolePermissionIdMapperBO(permissionIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelPermissionRole findOneByRoleId(Long roleId) {
		return relPermissionRoleMapper.selectOneByRoleId(new RelPermissionRoleRoleIdMapperBO(roleId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByRoleId(Long roleId) {
		return relPermissionRoleMapper.selectByRoleId(new RelPermissionRoleRoleIdMapperBO(roleId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByRoleIdList(List<Long> roleIdList) {
		return relPermissionRoleMapper.selectByRoleIdList(new RelPermissionRoleRoleIdMapperBO(roleIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByIdList(List<Long> idList) {
		return relPermissionRoleMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelPermissionRole> findListByServiceBO(RelPermissionRolePageQueryServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByPageQueryMapperBo(relPermissionRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(RelPermissionRolePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relPermissionRoleMapper.selectByPageQueryMapperBo(relPermissionRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(RelPermissionRoleCreateServiceBO serviceBO) {
		return relPermissionRoleMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchCreate(RelPermissionRoleBatchCreateServiceBO serviceBO) {

		List<Long> roleIdList = serviceBO.getRoleIdList();
		List<Long> permissionIdList = serviceBO.getPermissionIdList();

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个角色");
		}

		if (CollectionUtil.isEmpty(permissionIdList)) {
			throw new BusinessException("必须至少选择一个权限");
		}

		// 先删除角色所有旧数据，然后再新增新数据
		batchDeleteByRoleIdList(roleIdList);

		List<RelPermissionRole> entityList = new ArrayList<>();
		for (Long roleId : roleIdList) {
			for (Long permissionId : permissionIdList) {
				RelPermissionRoleCreateServiceBO createServiceBO = new RelPermissionRoleCreateServiceBO();
				createServiceBO.setRoleId(roleId);
				createServiceBO.setPermissionId(permissionId);
				entityList.add(initCreateBasicParam(createServiceBO));
			}
		}

		return relPermissionRoleMapper.batchInsertList(entityList);
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(RelPermissionRoleUpdateServiceBO serviceBO) {
		return relPermissionRoleMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relPermissionRoleMapper.deleteByIdList(relPermissionRoleMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByPermissionIdList(List<Long> permissionIdList) {
		return relPermissionRoleMapper.deleteByPermissionIdList(new RelPermissionRolePermissionIdMapperBO(permissionIdList));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByRoleIdList(List<Long> roleIdList) {
		return relPermissionRoleMapper.deleteByRoleIdList(new RelPermissionRoleRoleIdMapperBO(roleIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private RelPermissionRole initCreateBasicParam(RelPermissionRoleCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return relPermissionRoleMapStruct.createServiceBOToEntity(serviceBO);
	}

	private RelPermissionRole initUpdateBasicParam(RelPermissionRoleUpdateServiceBO serviceBO) {
		return relPermissionRoleMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

