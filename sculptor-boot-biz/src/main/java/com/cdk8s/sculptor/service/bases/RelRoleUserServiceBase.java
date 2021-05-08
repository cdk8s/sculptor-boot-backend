/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelRoleUserMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.*;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.bases.RelRoleUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RelRoleUserServiceBase {

	@Autowired
	private RelRoleUserMapper relRoleUserMapper;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public RelRoleUser findOneById(IdServiceBO serviceBO) {
		return relRoleUserMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return relRoleUserMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByRoleId(RelRoleUserRoleIdServiceBO serviceBO) {
		return relRoleUserMapper.selectOneByRoleId(relRoleUserMapStruct.roleIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByRoleIdNotEnum(RelRoleUserRoleIdServiceBO serviceBO) {
		RelRoleUserRoleIdMapperBO mapperBO = relRoleUserMapStruct.roleIdServiceBOToMapperBO(serviceBO);
		return relRoleUserMapper.selectOneByRoleId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByRoleId(RelRoleUserRoleIdServiceBO serviceBO) {
		return relRoleUserMapper.selectByRoleId(relRoleUserMapStruct.roleIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByRoleIdList(RelRoleUserRoleIdListServiceBO serviceBO) {
		return relRoleUserMapper.selectByRoleIdList(relRoleUserMapStruct.roleIdListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByUserId(RelRoleUserUserIdServiceBO serviceBO) {
		return relRoleUserMapper.selectOneByUserId(relRoleUserMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByUserIdNotEnum(RelRoleUserUserIdServiceBO serviceBO) {
		RelRoleUserUserIdMapperBO mapperBO = relRoleUserMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return relRoleUserMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByUserId(RelRoleUserUserIdServiceBO serviceBO) {
		return relRoleUserMapper.selectByUserId(relRoleUserMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByUserIdList(RelRoleUserUserIdListServiceBO serviceBO) {
		return relRoleUserMapper.selectByUserIdList(relRoleUserMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByIdList(IdListServiceBO serviceBO) {
		return relRoleUserMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelRoleUser findOneByServiceBO(RelRoleUserPageQueryServiceBO serviceBO) {
		List<RelRoleUser> list = relRoleUserMapper.selectByPageQueryMapperBo(relRoleUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<RelRoleUser> findListByServiceBO(RelRoleUserPageQueryServiceBO serviceBO) {
		return relRoleUserMapper.selectByPageQueryMapperBo(relRoleUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(RelRoleUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relRoleUserMapper.selectByPageQueryMapperBo(relRoleUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	public Integer create(RelRoleUserCreateServiceBO serviceBO) {
		RelRoleUser entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return relRoleUserMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreate(RelRoleUserBatchCreateServiceBO serviceBO) {

		List<Long> roleIdList = serviceBO.getRoleIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		List<RelRoleUser> entityList = new ArrayList<>();
		for (Long roleId : roleIdList) {
			for (Long userId : userIdList) {
				RelRoleUserPageQueryServiceBO pageQueryServiceBO = new RelRoleUserPageQueryServiceBO();
				pageQueryServiceBO.setRoleId(roleId);
				pageQueryServiceBO.setUserId(userId);
				List<RelRoleUser> listByServiceBO = findListByServiceBO(pageQueryServiceBO);
				if (CollectionUtil.isNotEmpty(listByServiceBO)) {
					continue;
				}

				RelRoleUserCreateServiceBO createServiceBO = new RelRoleUserCreateServiceBO();
				createServiceBO.setRoleId(roleId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelRoleUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relRoleUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByRoleIdListToDelete(RelRoleUserBatchCreateServiceBO serviceBO) {

		List<Long> roleIdList = serviceBO.getRoleIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelRoleUserRoleIdListToDeleteServiceBO relRoleUserRoleIdListToDeleteServiceBO = new RelRoleUserRoleIdListToDeleteServiceBO();
		relRoleUserRoleIdListToDeleteServiceBO.setRoleIdList(roleIdList);
		relRoleUserRoleIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByRoleIdList(relRoleUserRoleIdListToDeleteServiceBO);

		List<RelRoleUser> entityList = new ArrayList<>();
		for (Long roleId : roleIdList) {
			for (Long userId : userIdList) {
				RelRoleUserCreateServiceBO createServiceBO = new RelRoleUserCreateServiceBO();
				createServiceBO.setRoleId(roleId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelRoleUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relRoleUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByUserIdListToDelete(RelRoleUserBatchCreateServiceBO serviceBO) {

		List<Long> roleIdList = serviceBO.getRoleIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个 roleId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelRoleUserUserIdListToDeleteServiceBO relRoleUserUserIdListToDeleteServiceBO = new RelRoleUserUserIdListToDeleteServiceBO();
		relRoleUserUserIdListToDeleteServiceBO.setUserIdList(userIdList);
		relRoleUserUserIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByUserIdList(relRoleUserUserIdListToDeleteServiceBO);

		List<RelRoleUser> entityList = new ArrayList<>();
		for (Long userId : userIdList) {
			for (Long roleId : roleIdList) {
				RelRoleUserCreateServiceBO createServiceBO = new RelRoleUserCreateServiceBO();
				createServiceBO.setRoleId(roleId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelRoleUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relRoleUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(RelRoleUserUpdateServiceBO serviceBO) {
		return relRoleUserMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relRoleUserMapper.deleteByIdList(relRoleUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByRoleIdAndUserId(RelRoleUserRoleIdAndUserIdToDeleteServiceBO serviceBO) {
		return relRoleUserMapper.deleteByRoleIdAndUserId(relRoleUserMapStruct.deleteByRoleIdAndUserIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByRoleIdList(RelRoleUserRoleIdListToDeleteServiceBO serviceBO) {
		return relRoleUserMapper.deleteByRoleIdList(relRoleUserMapStruct.roleIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(RelRoleUserUserIdListToDeleteServiceBO serviceBO) {
		return relRoleUserMapper.deleteByUserIdList(relRoleUserMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
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

		if (null == serviceBO.getDeleteEnum()) {
			serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		}

		return relRoleUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	private RelRoleUser initUpdateBasicParam(RelRoleUserUpdateServiceBO serviceBO) {
		return relRoleUserMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

