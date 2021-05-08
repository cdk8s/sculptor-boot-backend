/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictItemServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapper.SysDictItemMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysDictItemMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemDictIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.*;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
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
@CacheConfig(cacheNames = "SysDictItemService")
public class SysDictItemServiceBase {

	@Autowired
	private SysDictItemMapper sysDictItemMapper;

	@Autowired
	private SysDictItemMapStruct sysDictItemMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneById(IdServiceBO serviceBO) {
		return sysDictItemMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysDictItemMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneByDictId(SysDictItemDictIdServiceBO serviceBO) {
		return sysDictItemMapper.selectOneByDictId(sysDictItemMapStruct.dictIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneByDictIdNotEnum(SysDictItemDictIdServiceBO serviceBO) {
		SysDictItemDictIdMapperBO mapperBO = sysDictItemMapStruct.dictIdServiceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysDictItemMapper.selectOneByDictId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictId(SysDictItemDictIdServiceBO serviceBO) {
		return sysDictItemMapper.selectByDictId(sysDictItemMapStruct.dictIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictIdList(SysDictItemDictIdListServiceBO serviceBO) {
		return sysDictItemMapper.selectByDictIdList(sysDictItemMapStruct.dictIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByItemCode(SysDictItemItemCodeServiceBO serviceBO) {
		return sysDictItemMapper.selectByItemCode(sysDictItemMapStruct.itemCodeServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictCode(SysDictItemDictCodeServiceBO serviceBO) {
		return sysDictItemMapper.selectByDictCode(sysDictItemMapStruct.dictCodeServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByItemCodeList(SysDictItemItemCodeListServiceBO serviceBO) {
		return sysDictItemMapper.selectByItemCodeList(sysDictItemMapStruct.itemCodeListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictCodeList(SysDictItemDictCodeListServiceBO serviceBO) {
		return sysDictItemMapper.selectByDictCodeList(sysDictItemMapStruct.dictCodeListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByIdList(IdListServiceBO serviceBO) {
		return sysDictItemMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneByServiceBO(SysDictItemPageQueryServiceBO serviceBO) {
		List<SysDictItem> list = sysDictItemMapper.selectByPageQueryMapperBo(sysDictItemMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByServiceBO(SysDictItemPageQueryServiceBO serviceBO) {
		return sysDictItemMapper.selectByPageQueryMapperBo(sysDictItemMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysDictItemPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysDictItemMapper.selectByPageQueryMapperBo(sysDictItemMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysDictItemCreateServiceBO serviceBO) {
		SysDictItem entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysDictItemMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysDictItemUpdateServiceBO serviceBO) {
		return sysDictItemMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysDictItemMapper.updateStateEnumByIdList(sysDictItemMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysDictItemMapper.updateDeleteEnumByIdList(sysDictItemMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByDictIdList(SysDictItemDictIdListToDeleteServiceBO serviceBO) {
		return sysDictItemMapper.updateDeleteEnumByDictIdList(sysDictItemMapStruct.dictIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysDictItem initCreateBasicParam(SysDictItemCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getDictValueTypeEnum()) {
			serviceBO.setDictValueTypeEnum(1);
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


		return sysDictItemMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysDictItem initUpdateBasicParam(SysDictItemUpdateServiceBO serviceBO) {
		return sysDictItemMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

