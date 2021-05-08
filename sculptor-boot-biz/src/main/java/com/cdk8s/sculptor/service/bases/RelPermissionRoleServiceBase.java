/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelPermissionRoleServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelPermissionRoleMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelPermissionRoleMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRolePermissionIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRoleRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.bases.RelPermissionRoleBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RelPermissionRoleServiceBase {

	@Autowired
	private RelPermissionRoleMapper relPermissionRoleMapper;

	@Autowired
	private RelPermissionRoleMapStruct relPermissionRoleMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public RelPermissionRole findOneById(IdServiceBO serviceBO) {
		return relPermissionRoleMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return relPermissionRoleMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByPermissionId(RelPermissionRolePermissionIdServiceBO serviceBO) {
		return relPermissionRoleMapper.selectOneByPermissionId(relPermissionRoleMapStruct.permissionIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByPermissionIdNotEnum(RelPermissionRolePermissionIdServiceBO serviceBO) {
		RelPermissionRolePermissionIdMapperBO mapperBO = relPermissionRoleMapStruct.permissionIdServiceBOToMapperBO(serviceBO);
		return relPermissionRoleMapper.selectOneByPermissionId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByPermissionId(RelPermissionRolePermissionIdServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByPermissionId(relPermissionRoleMapStruct.permissionIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByPermissionIdList(RelPermissionRolePermissionIdListServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByPermissionIdList(relPermissionRoleMapStruct.permissionIdListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByRoleId(RelPermissionRoleRoleIdServiceBO serviceBO) {
		return relPermissionRoleMapper.selectOneByRoleId(relPermissionRoleMapStruct.roleIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByRoleIdNotEnum(RelPermissionRoleRoleIdServiceBO serviceBO) {
		RelPermissionRoleRoleIdMapperBO mapperBO = relPermissionRoleMapStruct.roleIdServiceBOToMapperBO(serviceBO);
		return relPermissionRoleMapper.selectOneByRoleId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByRoleId(RelPermissionRoleRoleIdServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByRoleId(relPermissionRoleMapStruct.roleIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByRoleIdList(RelPermissionRoleRoleIdListServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByRoleIdList(relPermissionRoleMapStruct.roleIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByIdList(IdListServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelPermissionRole findOneByServiceBO(RelPermissionRolePageQueryServiceBO serviceBO) {
		List<RelPermissionRole> list = relPermissionRoleMapper.selectByPageQueryMapperBo(relPermissionRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<RelPermissionRole> findListByServiceBO(RelPermissionRolePageQueryServiceBO serviceBO) {
		return relPermissionRoleMapper.selectByPageQueryMapperBo(relPermissionRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(RelPermissionRolePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relPermissionRoleMapper.selectByPageQueryMapperBo(relPermissionRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@Transactional(rollbackFor = Exception.class)
	public Integer create(RelPermissionRoleCreateServiceBO serviceBO) {
		RelPermissionRole entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return relPermissionRoleMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreate(RelPermissionRoleBatchCreateServiceBO serviceBO) {

		List<Long> permissionIdList = serviceBO.getPermissionIdList();
		List<Long> roleIdList = serviceBO.getRoleIdList();

		if (CollectionUtil.isEmpty(permissionIdList)) {
			throw new BusinessException("必须至少选择一个 permissionId");
		}

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}


		List<RelPermissionRole> entityList = new ArrayList<>();
		for (Long permissionId : permissionIdList) {
			for (Long roleId : roleIdList) {
				RelPermissionRolePageQueryServiceBO pageQueryServiceBO = new RelPermissionRolePageQueryServiceBO();
				pageQueryServiceBO.setPermissionId(permissionId);
				pageQueryServiceBO.setRoleId(roleId);
				List<RelPermissionRole> listByServiceBO = findListByServiceBO(pageQueryServiceBO);
				if (CollectionUtil.isNotEmpty(listByServiceBO)) {
					continue;
				}

				RelPermissionRoleCreateServiceBO createServiceBO = new RelPermissionRoleCreateServiceBO();
				createServiceBO.setPermissionId(permissionId);
				createServiceBO.setRoleId(roleId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelPermissionRole entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relPermissionRoleMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByPermissionIdListToDelete(RelPermissionRoleBatchCreateServiceBO serviceBO) {

		List<Long> permissionIdList = serviceBO.getPermissionIdList();
		List<Long> roleIdList = serviceBO.getRoleIdList();

		if (CollectionUtil.isEmpty(permissionIdList)) {
			throw new BusinessException("必须至少选择一个 permissionId");
		}

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelPermissionRolePermissionIdListToDeleteServiceBO deleteServiceBO = new RelPermissionRolePermissionIdListToDeleteServiceBO();
		deleteServiceBO.setPermissionIdList(permissionIdList);
		deleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByPermissionIdList(deleteServiceBO);

		List<RelPermissionRole> entityList = new ArrayList<>();
		for (Long permissionId : permissionIdList) {
			for (Long roleId : roleIdList) {
				RelPermissionRoleCreateServiceBO createServiceBO = new RelPermissionRoleCreateServiceBO();
				createServiceBO.setPermissionId(permissionId);
				createServiceBO.setRoleId(roleId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelPermissionRole entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relPermissionRoleMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByRoleIdListToDelete(RelPermissionRoleBatchCreateServiceBO serviceBO) {

		List<Long> permissionIdList = serviceBO.getPermissionIdList();
		List<Long> roleIdList = serviceBO.getRoleIdList();

		if (CollectionUtil.isEmpty(permissionIdList)) {
			throw new BusinessException("必须至少选择一个 permissionId");
		}

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelPermissionRoleRoleIdListToDeleteServiceBO deleteServiceBO = new RelPermissionRoleRoleIdListToDeleteServiceBO();
		deleteServiceBO.setRoleIdList(roleIdList);
		deleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByRoleIdList(deleteServiceBO);

		List<RelPermissionRole> entityList = new ArrayList<>();
		for (Long roleId : roleIdList) {
			for (Long permissionId : permissionIdList) {
				RelPermissionRoleCreateServiceBO createServiceBO = new RelPermissionRoleCreateServiceBO();
				createServiceBO.setPermissionId(permissionId);
				createServiceBO.setRoleId(roleId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelPermissionRole entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relPermissionRoleMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(RelPermissionRoleUpdateServiceBO serviceBO) {
		RelPermissionRole entity = initUpdateBasicParam(serviceBO);
		return relPermissionRoleMapper.updateByPrimaryKeySelective(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByPermissionIdAndRoleId(RelPermissionRolePermissionIdAndRoleIdToDeleteServiceBO serviceBO) {
		return relPermissionRoleMapper.deleteByPermissionIdAndRoleId(relPermissionRoleMapStruct.deleteByPermissionIdAndRoleIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relPermissionRoleMapper.deleteByIdList(relPermissionRoleMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByPermissionIdList(RelPermissionRolePermissionIdListToDeleteServiceBO serviceBO) {
		return relPermissionRoleMapper.deleteByPermissionIdList(relPermissionRoleMapStruct.permissionIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByRoleIdList(RelPermissionRoleRoleIdListToDeleteServiceBO serviceBO) {
		return relPermissionRoleMapper.deleteByRoleIdList(relPermissionRoleMapStruct.roleIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public RelPermissionRole initCreateBasicParam(RelPermissionRoleCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}


		if (null == serviceBO.getCreateDate()) {
			serviceBO.setCreateDate(DatetimeUtil.currentEpochMilli());
		}
		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return relPermissionRoleMapStruct.createServiceBOToEntity(serviceBO);
	}

	public RelPermissionRole initUpdateBasicParam(RelPermissionRoleUpdateServiceBO serviceBO) {
		return relPermissionRoleMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

