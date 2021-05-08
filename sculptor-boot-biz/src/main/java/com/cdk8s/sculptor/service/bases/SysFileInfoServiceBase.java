/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapper.SysFileInfoMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysFileInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfileinfo.SysFileInfoFolderIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
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
@CacheConfig(cacheNames = "SysFileInfoService")
public class SysFileInfoServiceBase {

	@Autowired
	private SysFileInfoMapper sysFileInfoMapper;

	@Autowired
	private SysFileInfoMapStruct sysFileInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFileInfo findOneById(IdServiceBO serviceBO) {
		return sysFileInfoMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFileInfo findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysFileInfoMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFileInfo findOneByFolderId(SysFileInfoFolderIdServiceBO serviceBO) {
		return sysFileInfoMapper.selectOneByFolderId(sysFileInfoMapStruct.folderIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFileInfo findOneByFolderIdNotEnum(SysFileInfoFolderIdServiceBO serviceBO) {
		SysFileInfoFolderIdMapperBO mapperBO = sysFileInfoMapStruct.folderIdServiceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		return sysFileInfoMapper.selectOneByFolderId(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFolderId(SysFileInfoFolderIdServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFolderId(sysFileInfoMapStruct.folderIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFolderIdList(SysFileInfoFolderIdListServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFolderIdList(sysFileInfoMapStruct.folderIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileSuffix(SysFileInfoFileSuffixServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileSuffix(sysFileInfoMapStruct.fileSuffixServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileStorageName(SysFileInfoFileStorageNameServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileStorageName(sysFileInfoMapStruct.fileStorageNameServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileShowName(SysFileInfoFileShowNameServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileShowName(sysFileInfoMapStruct.fileShowNameServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileSuffixList(SysFileInfoFileSuffixListServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileSuffixList(sysFileInfoMapStruct.fileSuffixListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileStorageNameList(SysFileInfoFileStorageNameListServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileStorageNameList(sysFileInfoMapStruct.fileStorageNameListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByFileShowNameList(SysFileInfoFileShowNameListServiceBO serviceBO) {
		return sysFileInfoMapper.selectByFileShowNameList(sysFileInfoMapStruct.fileShowNameListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByIdList(IdListServiceBO serviceBO) {
		return sysFileInfoMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysFileInfo findOneByServiceBO(SysFileInfoPageQueryServiceBO serviceBO) {
		List<SysFileInfo> list = sysFileInfoMapper.selectByPageQueryMapperBo(sysFileInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByServiceBO(SysFileInfoPageQueryServiceBO serviceBO) {
		return sysFileInfoMapper.selectByPageQueryMapperBo(sysFileInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysFileInfoPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysFileInfoMapper.selectByPageQueryMapperBo(sysFileInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysFileInfoCreateServiceBO serviceBO) {
		SysFileInfo entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysFileInfoMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysFileInfoUpdateServiceBO serviceBO) {
		SysFileInfo entity = initUpdateBasicParam(serviceBO);
		return sysFileInfoMapper.updateByPrimaryKeySelective(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysFileInfoMapper.updateStateEnumByIdList(sysFileInfoMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysFileInfoMapper.updateDeleteEnumByIdList(sysFileInfoMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByFolderIdList(SysFileInfoFolderIdListToDeleteServiceBO serviceBO) {
		return sysFileInfoMapper.updateDeleteEnumByFolderIdList(sysFileInfoMapStruct.folderIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysFileInfo initCreateBasicParam(SysFileInfoCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getFileStorageTypeEnum()) {
			serviceBO.setFileStorageTypeEnum(1);
		}
		if (null == serviceBO.getBoolTopEnum()) {
			serviceBO.setBoolTopEnum(2);
		}
		if (null == serviceBO.getBoolOssCompleteEnum()) {
			serviceBO.setBoolOssCompleteEnum(1);
		}
		if (null == serviceBO.getBoolOssDeleteEnum()) {
			serviceBO.setBoolOssDeleteEnum(2);
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


		return sysFileInfoMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysFileInfo initUpdateBasicParam(SysFileInfoUpdateServiceBO serviceBO) {
		return sysFileInfoMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

