/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.mapper.SysUserInfoMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysUserInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuserinfo.SysUserInfoUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
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

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysUserInfoService")
public class SysUserInfoServiceBase {

	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;

	@Autowired
	private SysUserInfoMapStruct sysUserInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUserInfo findOneById(IdServiceBO serviceBO) {
		return sysUserInfoMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUserInfo findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysUserInfoMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUserInfo findOneByUserId(SysUserInfoUserIdServiceBO serviceBO) {
		return sysUserInfoMapper.selectOneByUserId(sysUserInfoMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUserInfo findOneByUserIdNotEnum(SysUserInfoUserIdServiceBO serviceBO) {
		SysUserInfoUserIdMapperBO mapperBO = sysUserInfoMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return sysUserInfoMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByUserId(SysUserInfoUserIdServiceBO serviceBO) {
		return sysUserInfoMapper.selectByUserId(sysUserInfoMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByUserIdList(SysUserInfoUserIdListServiceBO serviceBO) {
		return sysUserInfoMapper.selectByUserIdList(sysUserInfoMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByWeixinUnionid(SysUserInfoWeixinUnionidServiceBO serviceBO) {
		return sysUserInfoMapper.selectByWeixinUnionid(sysUserInfoMapStruct.weixinUnionidServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByWeixinOpenid(SysUserInfoWeixinOpenidServiceBO serviceBO) {
		return sysUserInfoMapper.selectByWeixinOpenid(sysUserInfoMapStruct.weixinOpenidServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByIdCard(SysUserInfoIdCardServiceBO serviceBO) {
		return sysUserInfoMapper.selectByIdCard(sysUserInfoMapStruct.idCardServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByWeixinUnionidList(SysUserInfoWeixinUnionidListServiceBO serviceBO) {
		return sysUserInfoMapper.selectByWeixinUnionidList(sysUserInfoMapStruct.weixinUnionidListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByWeixinOpenidList(SysUserInfoWeixinOpenidListServiceBO serviceBO) {
		return sysUserInfoMapper.selectByWeixinOpenidList(sysUserInfoMapStruct.weixinOpenidListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByIdCardList(SysUserInfoIdCardListServiceBO serviceBO) {
		return sysUserInfoMapper.selectByIdCardList(sysUserInfoMapStruct.idCardListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByIdList(IdListServiceBO serviceBO) {
		return sysUserInfoMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUserInfo findOneByServiceBO(SysUserInfoPageQueryServiceBO serviceBO) {
		List<SysUserInfo> list = sysUserInfoMapper.selectByPageQueryMapperBo(sysUserInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUserInfo> findListByServiceBO(SysUserInfoPageQueryServiceBO serviceBO) {
		return sysUserInfoMapper.selectByPageQueryMapperBo(sysUserInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysUserInfoPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysUserInfoMapper.selectByPageQueryMapperBo(sysUserInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysUserInfoCreateServiceBO serviceBO) {
		SysUserInfo entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysUserInfoMapper.insert(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysUserInfoUpdateServiceBO serviceBO) {
		SysUserInfo entity = initUpdateBasicParam(serviceBO);
		return sysUserInfoMapper.updateByPrimaryKeySelective(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysUserInfoMapper.deleteByIdList(sysUserInfoMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(SysUserInfoUserIdListToDeleteServiceBO serviceBO) {
		return sysUserInfoMapper.deleteByUserIdList(sysUserInfoMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchCreate(List<SysUserInfoCreateServiceBO> serviceBOList) {
		int result = 0;
		if (CollectionUtil.isNotEmpty(serviceBOList)) {
			for (SysUserInfoCreateServiceBO serviceBO : serviceBOList) {
				Integer createResult = create(serviceBO);
				result = result + createResult;
			}
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdate(List<SysUserInfoUpdateServiceBO> serviceBOList) {
		int result = 0;
		if (CollectionUtil.isNotEmpty(serviceBOList)) {
			for (SysUserInfoUpdateServiceBO serviceBO : serviceBOList) {
				int executeResult;
				if (null == serviceBO.getId()) {
					// 新增操作
					SysUserInfoCreateServiceBO createServiceBO = sysUserInfoMapStruct.updateServiceBOToCreateServiceBO(serviceBO);
					executeResult = create(createServiceBO);
				} else {
					// 更新操作
					executeResult = update(serviceBO);
				}
				result = result + executeResult;
			}
		}
		return result;
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysUserInfo initCreateBasicParam(SysUserInfoCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
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


		return sysUserInfoMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysUserInfo initUpdateBasicParam(SysUserInfoUpdateServiceBO serviceBO) {
		return sysUserInfoMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

