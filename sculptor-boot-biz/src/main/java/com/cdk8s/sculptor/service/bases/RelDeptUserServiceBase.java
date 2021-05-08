/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelDeptUserMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelDeptUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserDeptIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.*;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.bases.RelDeptUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
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
public class RelDeptUserServiceBase {

	@Autowired
	private RelDeptUserMapper relDeptUserMapper;

	@Autowired
	private RelDeptUserMapStruct relDeptUserMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public RelDeptUser findOneById(IdServiceBO serviceBO) {
		return relDeptUserMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return relDeptUserMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByDeptId(RelDeptUserDeptIdServiceBO serviceBO) {
		return relDeptUserMapper.selectOneByDeptId(relDeptUserMapStruct.deptIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByDeptIdNotEnum(RelDeptUserDeptIdServiceBO serviceBO) {
		RelDeptUserDeptIdMapperBO mapperBO = relDeptUserMapStruct.deptIdServiceBOToMapperBO(serviceBO);
		return relDeptUserMapper.selectOneByDeptId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByDeptId(RelDeptUserDeptIdServiceBO serviceBO) {
		return relDeptUserMapper.selectByDeptId(relDeptUserMapStruct.deptIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByDeptIdList(RelDeptUserDeptIdListServiceBO serviceBO) {
		return relDeptUserMapper.selectByDeptIdList(relDeptUserMapStruct.deptIdListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByUserId(RelDeptUserUserIdServiceBO serviceBO) {
		return relDeptUserMapper.selectOneByUserId(relDeptUserMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByUserIdNotEnum(RelDeptUserUserIdServiceBO serviceBO) {
		RelDeptUserUserIdMapperBO mapperBO = relDeptUserMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return relDeptUserMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByUserId(RelDeptUserUserIdServiceBO serviceBO) {
		return relDeptUserMapper.selectByUserId(relDeptUserMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByUserIdList(RelDeptUserUserIdListServiceBO serviceBO) {
		return relDeptUserMapper.selectByUserIdList(relDeptUserMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByIdList(IdListServiceBO serviceBO) {
		return relDeptUserMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public RelDeptUser findOneByServiceBO(RelDeptUserPageQueryServiceBO serviceBO) {
		List<RelDeptUser> list = relDeptUserMapper.selectByPageQueryMapperBo(relDeptUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<RelDeptUser> findListByServiceBO(RelDeptUserPageQueryServiceBO serviceBO) {
		return relDeptUserMapper.selectByPageQueryMapperBo(relDeptUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(RelDeptUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relDeptUserMapper.selectByPageQueryMapperBo(relDeptUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@Transactional(rollbackFor = Exception.class)
	public Integer create(RelDeptUserCreateServiceBO serviceBO) {
		RelDeptUser entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return relDeptUserMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreate(RelDeptUserBatchCreateServiceBO serviceBO) {

		List<Long> deptIdList = serviceBO.getDeptIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(deptIdList)) {
			throw new BusinessException("必须至少选择一个 deptId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		List<RelDeptUser> entityList = new ArrayList<>();
		for (Long deptId : deptIdList) {
			for (Long userId : userIdList) {
				RelDeptUserPageQueryServiceBO pageQueryServiceBO = new RelDeptUserPageQueryServiceBO();
				pageQueryServiceBO.setDeptId(deptId);
				pageQueryServiceBO.setUserId(userId);
				List<RelDeptUser> listByServiceBO = findListByServiceBO(pageQueryServiceBO);
				if (CollectionUtil.isNotEmpty(listByServiceBO)) {
					continue;
				}

				RelDeptUserCreateServiceBO createServiceBO = new RelDeptUserCreateServiceBO();
				createServiceBO.setDeptId(deptId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelDeptUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relDeptUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByDeptIdListToDelete(RelDeptUserBatchCreateServiceBO serviceBO) {

		List<Long> deptIdList = serviceBO.getDeptIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(deptIdList)) {
			throw new BusinessException("必须至少选择一个 deptId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelDeptUserDeptIdListToDeleteServiceBO deleteServiceBO = new RelDeptUserDeptIdListToDeleteServiceBO();
		deleteServiceBO.setDeptIdList(deptIdList);
		deleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByDeptIdList(deleteServiceBO);

		List<RelDeptUser> entityList = new ArrayList<>();
		for (Long deptId : deptIdList) {
			for (Long userId : userIdList) {
				RelDeptUserCreateServiceBO createServiceBO = new RelDeptUserCreateServiceBO();
				createServiceBO.setDeptId(deptId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelDeptUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relDeptUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchCreateByUserIdListToDelete(RelDeptUserBatchCreateServiceBO serviceBO) {

		List<Long> deptIdList = serviceBO.getDeptIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(deptIdList)) {
			throw new BusinessException("必须至少选择一个 deptId");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个 userId");
		}


		// cdk8stodo 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		RelDeptUserUserIdListToDeleteServiceBO deleteServiceBO = new RelDeptUserUserIdListToDeleteServiceBO();
		deleteServiceBO.setUserIdList(userIdList);
		deleteServiceBO.setTenantId(serviceBO.getTenantId());
		batchDeleteByUserIdList(deleteServiceBO);

		List<RelDeptUser> entityList = new ArrayList<>();
		for (Long userId : userIdList) {
			for (Long deptId : deptIdList) {
				RelDeptUserCreateServiceBO createServiceBO = new RelDeptUserCreateServiceBO();
				createServiceBO.setDeptId(deptId);
				createServiceBO.setUserId(userId);
				createServiceBO.setCreateDate(serviceBO.getCreateDate());
				createServiceBO.setCreateUserId(serviceBO.getCreateUserId());

				RelDeptUser entity = initCreateBasicParam(createServiceBO);
				if (null == entity.getCreateUserId()) {
					entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
				}

				entityList.add(entity);
			}
		}

		return relDeptUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(RelDeptUserUpdateServiceBO serviceBO) {
		RelDeptUser entity = initUpdateBasicParam(serviceBO);
		return relDeptUserMapper.updateByPrimaryKeySelective(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByDeptIdAndUserId(RelDeptUserDeptIdAndUserIdToDeleteServiceBO serviceBO) {
		return relDeptUserMapper.deleteByDeptIdAndUserId(relDeptUserMapStruct.deleteByDeptIdAndUserIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relDeptUserMapper.deleteByIdList(relDeptUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByDeptIdList(RelDeptUserDeptIdListToDeleteServiceBO serviceBO) {
		return relDeptUserMapper.deleteByDeptIdList(relDeptUserMapStruct.deptIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(RelDeptUserUserIdListToDeleteServiceBO serviceBO) {
		return relDeptUserMapper.deleteByUserIdList(relDeptUserMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public RelDeptUser initCreateBasicParam(RelDeptUserCreateServiceBO serviceBO) {
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


		return relDeptUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	public RelDeptUser initUpdateBasicParam(RelDeptUserUpdateServiceBO serviceBO) {
		return relDeptUserMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

