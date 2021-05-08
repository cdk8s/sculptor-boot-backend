/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.mapper.SysBannerMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysBannerMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbanner.SysBannerJumpObjectIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysbanner.*;
import com.cdk8s.sculptor.pojo.entity.SysBanner;
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

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysBannerService")
public class SysBannerServiceBase {

	@Autowired
	private SysBannerMapper sysBannerMapper;

	@Autowired
	private SysBannerMapStruct sysBannerMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysBanner findOneById(IdServiceBO serviceBO) {
		return sysBannerMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysBanner findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		return sysBannerMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysBanner findOneByJumpObjectId(SysBannerJumpObjectIdServiceBO serviceBO) {
		return sysBannerMapper.selectOneByJumpObjectId(sysBannerMapStruct.jumpObjectIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysBanner findOneByJumpObjectIdNotEnum(SysBannerJumpObjectIdServiceBO serviceBO) {
		SysBannerJumpObjectIdMapperBO mapperBO = sysBannerMapStruct.jumpObjectIdServiceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		return sysBannerMapper.selectOneByJumpObjectId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByJumpObjectId(SysBannerJumpObjectIdServiceBO serviceBO) {
		return sysBannerMapper.selectByJumpObjectId(sysBannerMapStruct.jumpObjectIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByJumpObjectIdList(SysBannerJumpObjectIdListServiceBO serviceBO) {
		return sysBannerMapper.selectByJumpObjectIdList(sysBannerMapStruct.jumpObjectIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByJumpTypeCode(SysBannerJumpTypeCodeServiceBO serviceBO) {
		return sysBannerMapper.selectByJumpTypeCode(sysBannerMapStruct.jumpTypeCodeServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByBannerCode(SysBannerBannerCodeServiceBO serviceBO) {
		return sysBannerMapper.selectByBannerCode(sysBannerMapStruct.bannerCodeServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByBannerCodeList(SysBannerBannerCodeListServiceBO serviceBO) {
		return sysBannerMapper.selectByBannerCodeList(sysBannerMapStruct.bannerCodeListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByIdList(IdListServiceBO serviceBO) {
		return sysBannerMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysBanner findOneByServiceBO(SysBannerPageQueryServiceBO serviceBO) {
		List<SysBanner> list = sysBannerMapper.selectByPageQueryMapperBo(sysBannerMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByBannerTitleWhereLike(SysBannerBannerTitleLikeQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysBannerMapper.selectByBannerTitleWhereLike(sysBannerMapStruct.bannerTitleLikeQueryServiceBOToMapperBO(serviceBO));
		});
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysBanner> findListByServiceBO(SysBannerPageQueryServiceBO serviceBO) {
		return sysBannerMapper.selectByPageQueryMapperBo(sysBannerMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysBannerPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysBannerMapper.selectByPageQueryMapperBo(sysBannerMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysBannerCreateServiceBO serviceBO) {
		SysBanner entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysBannerMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysBannerUpdateServiceBO serviceBO) {
		SysBanner entity = initUpdateBasicParam(serviceBO);
		return sysBannerMapper.updateByPrimaryKeySelective(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysBannerMapper.updateDeleteEnumByIdList(sysBannerMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByJumpObjectIdList(SysBannerJumpObjectIdListToDeleteServiceBO serviceBO) {
		return sysBannerMapper.updateDeleteEnumByJumpObjectIdList(sysBannerMapStruct.jumpObjectIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysBanner initCreateBasicParam(SysBannerCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBannerStandDate()) {
			serviceBO.setBannerStandDate(new BigDecimal(2.50));
		}
		if (null == serviceBO.getBannerJumpTypeEnum()) {
			serviceBO.setBannerJumpTypeEnum(1);
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


		return sysBannerMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysBanner initUpdateBasicParam(SysBannerUpdateServiceBO serviceBO) {
		return sysBannerMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

