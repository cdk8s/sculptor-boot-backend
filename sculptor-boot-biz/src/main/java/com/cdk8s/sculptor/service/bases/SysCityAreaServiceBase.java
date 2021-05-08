/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysCityAreaMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysCityAreaMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.*;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysCityArea;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysCityAreaService")
public class SysCityAreaServiceBase {

	@Autowired
	private SysCityAreaMapper sysCityAreaMapper;

	@Autowired
	private SysCityAreaMapStruct sysCityAreaMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;
	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysCityArea findOneById(IdServiceBO serviceBO) {
		return sysCityAreaMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysCityArea findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysCityAreaMapper.selectById(mapperBO);
	}


	/**
	 * 显示指定父 ID 的子集，只递归指定的子集（树结构），一般用在级联组件回显，以及商品添加时候菜单选择功能
	 * ShowLevel 是关键，如果是 2 级，则即使你传入的父ID 是三级的 ID 也是只会展示 2 级。所以一般该值是跟传入的父类个数成正相关，如果父ID是传入3个，一般 ShowLevel 就是 3。
	 * 1 是全国，所以查询全国的子集（不递归）
	 * 13 是河北省，所以查询河北所有子集（不递归）
	 * 1301 是石家庄市，所以查询石家庄所有子集（不递归）
	 *
	 * @param serviceBO parentIdList 级联的父 ID，比如传：1,13,1301
	 * @param serviceBO showLevel 显示层级，比如传 2，即使数据是支持 4 级数据，也是只展示 2 级数据出来
	 * @return
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListByTreeCascade(TreeCascadeServiceBO serviceBO) {
		Long firstNode = serviceBO.getParentIdList().get(0);

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(firstNode);
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysCityArea parentObject = findOneById(idServiceBO);

		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
		parentIdServiceBO.setParentId(firstNode);
		parentIdServiceBO.setTenantId(serviceBO.getTenantId());
		parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysCityArea> childrenList = findListByParentId(parentIdServiceBO);
		int flag = 0;
		recursionToListByTreeCascade(flag, parentObject, childrenList, serviceBO);
		return childrenList;
	}

	/**
	 * 递归自己和所有子集记录（平级结构）（递归所有，数据量大有性能危险）
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findRecursionListByParentId(ParentIdServiceBO serviceBO) {
		if (null == serviceBO.getParentId()) {
			serviceBO.setParentId(GlobalConstant.TOP_PARENT_ID);
		}

		List<SysCityArea> result = new ArrayList<>();

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getParentId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysCityArea parentObject = findOneById(idServiceBO);
		result.add(parentObject);

		List<SysCityArea> childrenList = findListByParentId(serviceBO);
		recursionToList(result, childrenList);
		return result;
	}

	/**
	 * 递归自己和所有子集记录（树结构）（递归所有，数据量大有性能危险）
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findTreeListByParentId(ParentIdServiceBO serviceBO) {
		if (null == serviceBO.getParentId()) {
			serviceBO.setParentId(GlobalConstant.TOP_PARENT_ID);
		}

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getParentId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysCityArea parentObject = findOneById(idServiceBO);
		List<SysCityArea> childrenList = findListByParentId(serviceBO);
		recursionToTreeList(parentObject, childrenList);
		return childrenList;
	}

	/**
	 * 子集查询（不递归），并包含是否是父类字段
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListAndCheckParentEnumByParentId(ParentIdServiceBO serviceBO) {
		List<SysCityArea> entityList = findListByParentId(serviceBO);
		if (CollectionUtil.isEmpty(entityList)) {
			return entityList;
		}

		for (SysCityArea entity : entityList) {
			ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
			parentIdServiceBO.setParentId(entity.getId());
			parentIdServiceBO.setTenantId(serviceBO.getTenantId());
			parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			List<SysCityArea> listByParentId = findListByParentId(parentIdServiceBO);
			if (CollectionUtil.isNotEmpty(listByParentId)) {
				entity.setBoolParentEnum(BooleanEnum.YES.getCode());
			} else {
				entity.setBoolParentEnum(BooleanEnum.NO.getCode());
			}
		}

		return entityList;
	}

	/**
	 * 所有子集记录（平级结构）
	 */
	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListByParentId(ParentIdServiceBO serviceBO) {
		ParentIdMapperBO mapperBO = parentIdAndParentIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysCityAreaMapper.selectByParentId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListByParentIdList(ParentIdListServiceBO serviceBO) {
		ParentIdListMapperBO mapperBO = parentIdAndParentIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysCityAreaMapper.selectByParentIdList(mapperBO);
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListByIdList(IdListServiceBO serviceBO) {
		return sysCityAreaMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysCityArea findOneByServiceBO(SysCityAreaPageQueryServiceBO serviceBO) {
		List<SysCityArea> list = sysCityAreaMapper.selectByPageQueryMapperBo(sysCityAreaMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysCityArea> findListByServiceBO(SysCityAreaPageQueryServiceBO serviceBO) {
		return sysCityAreaMapper.selectByPageQueryMapperBo(sysCityAreaMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysCityAreaPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysCityAreaMapper.selectByPageQueryMapperBo(sysCityAreaMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysCityAreaCreateServiceBO serviceBO) {
		SysCityArea entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysCityAreaMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysCityAreaUpdateServiceBO serviceBO) {
		return sysCityAreaMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysCityAreaMapper.deleteByIdList(sysCityAreaMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysCityArea initCreateBasicParam(SysCityAreaCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}


		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}
		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}

		Long parentId = serviceBO.getParentId();
		if (parentId.equals(1L)) {
			serviceBO.setParentIds("1");
		} else {

			IdServiceBO idServiceBO = new IdServiceBO();
			idServiceBO.setId(parentId);
			idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

			SysCityArea parentObject = findOneById(idServiceBO);
			if (null == parentObject) {
				throw new BusinessException("该父节点不存在");
			}
			serviceBO.setParentIds(parentObject.getParentIds() + "," + parentId);
		}

		return sysCityAreaMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysCityArea initUpdateBasicParam(SysCityAreaUpdateServiceBO serviceBO) {
		return sysCityAreaMapStruct.updateServiceBOToEntity(serviceBO);
	}

	private void recursionToList(List<SysCityArea> result, List<SysCityArea> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			result.addAll(childrenList);
			for (SysCityArea entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entity.getId());
				parentIdServiceBO.setTenantId(entity.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
				List<SysCityArea> children = findListByParentId(parentIdServiceBO);
				recursionToList(result, children);
			}
		}
	}

	private void recursionToTreeList(SysCityArea parentObject, List<SysCityArea> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysCityArea entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entity.getId());
				parentIdServiceBO.setTenantId(entity.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

				List<SysCityArea> children = findListByParentId(parentIdServiceBO);
				recursionToTreeList(entity, children);
			}
		}
	}

	private void recursionToListByTreeCascade(int flag, SysCityArea parentObject, List<SysCityArea> childrenList, TreeCascadeServiceBO serviceBO) {
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

			for (SysCityArea entity : childrenList) {
				Long entityId = entity.getId();
				entity.setLabel(entity.getAreaName());
				entity.setValue(entity.getId());
				if (entity.getParentId().equals(entityId)) {
					// 避免错误数据造成的死循环
					continue;
				}

				ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
				parentIdServiceBO.setParentId(entityId);
				parentIdServiceBO.setTenantId(serviceBO.getTenantId());
				parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
				List<SysCityArea> children = findListByParentId(parentIdServiceBO);

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

