/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFolderInfoServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysFolderInfoMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysFolderInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.*;
import com.cdk8s.sculptor.pojo.bo.service.sysfolderinfo.*;
import com.cdk8s.sculptor.pojo.entity.SysFolderInfo;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysFolderInfoService")
public class SysFolderInfoServiceBase {

	@Autowired
	private SysFolderInfoMapper sysFolderInfoMapper;

	@Autowired
	private SysFolderInfoMapStruct sysFolderInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFolderInfo findOneById(IdServiceBO serviceBO) {
		return sysFolderInfoMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFolderInfo findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysFolderInfoMapper.selectById(mapperBO);
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByFolderName(SysFolderInfoFolderNameServiceBO serviceBO) {
		return sysFolderInfoMapper.selectByFolderName(sysFolderInfoMapStruct.folderNameServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByFolderNameList(SysFolderInfoFolderNameListServiceBO serviceBO) {
		return sysFolderInfoMapper.selectByFolderNameList(sysFolderInfoMapStruct.folderNameListServiceBOToMapperBO(serviceBO));
	}


	/**
	 * 级联组件回显、选择功能
	 *
	 * @param serviceBO parentIdList 级联的父 ID，比如传：1,13,1301
	 * @param serviceBO showLevel 显示层级，比如传 2，即使数据是支持 4 级数据，也是只展示 2 级数据出来
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByTreeCascade(TreeCascadeServiceBO serviceBO) {
		Long firstNode = serviceBO.getParentIdList().get(0);

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(firstNode);
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysFolderInfo parentObject = findOneById(idServiceBO);

		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
		parentIdServiceBO.setParentId(firstNode);
		parentIdServiceBO.setTenantId(serviceBO.getTenantId());
		parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysFolderInfo> childrenList = findListByParentId(parentIdServiceBO);
		int flag = 0;
		recursionToListByTreeCascade(flag, parentObject, childrenList, serviceBO);
		return childrenList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findRecursionListByParentId(ParentIdServiceBO serviceBO) {
		if (null == serviceBO.getParentId()) {
			serviceBO.setParentId(GlobalConstant.TOP_PARENT_ID);
		}

		List<SysFolderInfo> result = new ArrayList<>();

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getParentId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysFolderInfo parentObject = findOneById(idServiceBO);
		result.add(parentObject);

		List<SysFolderInfo> childrenList = findListByParentId(serviceBO);
		recursionToList(result, childrenList);
		return result;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findTreeListByParentId(ParentIdServiceBO serviceBO) {
		if (null == serviceBO.getParentId()) {
			serviceBO.setParentId(GlobalConstant.TOP_PARENT_ID);
		}

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getParentId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysFolderInfo parentObject = findOneById(idServiceBO);
		List<SysFolderInfo> childrenList = findListByParentId(serviceBO);
		recursionToTreeList(parentObject, childrenList);
		return childrenList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListAndCheckParentEnumByParentId(ParentIdServiceBO serviceBO) {
		List<SysFolderInfo> entityList = findListByParentId(serviceBO);
		if (CollectionUtil.isEmpty(entityList)) {
			return entityList;
		}

		for (SysFolderInfo entity : entityList) {
			ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
			parentIdServiceBO.setParentId(entity.getId());
			parentIdServiceBO.setTenantId(serviceBO.getTenantId());
			parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			List<SysFolderInfo> listByParentId = findListByParentId(parentIdServiceBO);
			if (CollectionUtil.isNotEmpty(listByParentId)) {
				entity.setBoolParentEnum(BooleanEnum.YES.getCode());
			} else {
				entity.setBoolParentEnum(BooleanEnum.NO.getCode());
			}
		}

		return entityList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByParentId(ParentIdServiceBO serviceBO) {
		ParentIdMapperBO mapperBO = parentIdAndParentIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysFolderInfoMapper.selectByParentId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByParentIdList(ParentIdListServiceBO serviceBO) {
		ParentIdListMapperBO mapperBO = parentIdAndParentIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysFolderInfoMapper.selectByParentIdList(mapperBO);
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByIdList(IdListServiceBO serviceBO) {
		return sysFolderInfoMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFolderInfo findOneByServiceBO(SysFolderInfoPageQueryServiceBO serviceBO) {
		List<SysFolderInfo> list = sysFolderInfoMapper.selectByPageQueryMapperBo(sysFolderInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFolderInfo> findListByServiceBO(SysFolderInfoPageQueryServiceBO serviceBO) {
		return sysFolderInfoMapper.selectByPageQueryMapperBo(sysFolderInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysFolderInfoPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysFolderInfoMapper.selectByPageQueryMapperBo(sysFolderInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysFolderInfoCreateServiceBO serviceBO) {
		SysFolderInfo entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysFolderInfoMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysFolderInfoUpdateServiceBO serviceBO) {
		SysFolderInfo entity = initUpdateBasicParam(serviceBO);
		return sysFolderInfoMapper.updateByPrimaryKeySelective(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysFolderInfoMapper.updateStateEnumByIdList(sysFolderInfoMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysFolderInfoMapper.updateDeleteEnumByIdList(sysFolderInfoMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysFolderInfo initCreateBasicParam(SysFolderInfoCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBoolTopEnum()) {
			serviceBO.setBoolTopEnum(2);
		}

		if (null == serviceBO.getCreateDate()) {
			serviceBO.setCreateDate(DatetimeUtil.currentEpochMilli());
		}
		if (null == serviceBO.getUpdateDate()) {
			serviceBO.setUpdateDate(DatetimeUtil.currentEpochMilli());
		}
		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}
		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}
		if (null == serviceBO.getDeleteEnum()) {
			serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}

		Long parentId = serviceBO.getParentId();
		if (parentId.equals(1L)) {
			serviceBO.setParentIds("1");
		} else {

			IdServiceBO idServiceBO = new IdServiceBO();
			idServiceBO.setId(parentId);
			idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

			SysFolderInfo parentObject = findOneById(idServiceBO);
			if (null == parentObject) {
				throw new BusinessException("该父节点不存在");
			}
			serviceBO.setParentIds(parentObject.getParentIds() + "," + parentId);
		}

		return sysFolderInfoMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysFolderInfo initUpdateBasicParam(SysFolderInfoUpdateServiceBO serviceBO) {
		return sysFolderInfoMapStruct.updateServiceBOToEntity(serviceBO);
	}

	private void recursionToList(List<SysFolderInfo> result, List<SysFolderInfo> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			result.addAll(childrenList);
			for (SysFolderInfo entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entity.getId());
				parentIdServiceBO.setTenantId(entity.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
				List<SysFolderInfo> children = findListByParentId(parentIdServiceBO);
				recursionToList(result, children);
			}
		}
	}

	private void recursionToTreeList(SysFolderInfo parentObject, List<SysFolderInfo> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysFolderInfo entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entity.getId());
				parentIdServiceBO.setTenantId(entity.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

				List<SysFolderInfo> children = findListByParentId(parentIdServiceBO);
				recursionToTreeList(entity, children);
			}
		}
	}

	private void recursionToListByTreeCascade(int flag, SysFolderInfo parentObject, List<SysFolderInfo> childrenList, TreeCascadeServiceBO serviceBO) {
		Integer showLevel = serviceBO.getShowLevel();
		flag = flag + 1;
		if (flag > showLevel) {
			return;
		}
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysFolderInfo entity : childrenList) {
				Long entityId = entity.getId();
				entity.setLabel(entity.getFolderName());
				entity.setValue(entity.getId());
				if (entity.getParentId().equals(entityId)) {
					// 避免错误数据造成的死循环
					continue;
				}

				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entityId);
				parentIdServiceBO.setTenantId(serviceBO.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
				List<SysFolderInfo> children = findListByParentId(parentIdServiceBO);

				if (CollectionUtil.isNotEmpty(children)) {
					entity.setBoolParentEnum(BooleanEnum.YES.getCode());
					entity.setIsLeaf(false);
				} else {
					entity.setBoolParentEnum(BooleanEnum.NO.getCode());
					entity.setIsLeaf(true);
				}

				if (flag == showLevel) {
					// 显示的层级到了，则认为所有都是子节点
					entity.setBoolParentEnum(BooleanEnum.NO.getCode());
					entity.setIsLeaf(true);
				}

				if (serviceBO.getParentIdList().contains(entityId)) {
					recursionToListByTreeCascade(flag, entity, children, serviceBO);
				}
			}
		}
	}
	// =====================================私有方法 end=====================================

}

