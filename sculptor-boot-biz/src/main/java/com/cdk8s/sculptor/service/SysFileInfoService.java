/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysFileInfoMapper;
import com.cdk8s.sculptor.mapper.ext.SysFileInfoMapperExt;
import com.cdk8s.sculptor.mapstruct.SysFileInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.SysFileInfoPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.bases.SysFileInfoServiceBase;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysFileInfoService")
@Service
public class SysFileInfoService extends SysFileInfoServiceBase {

	@Autowired
	private SysFileInfoMapper sysFileInfoMapper;

	@Autowired
	private SysFileInfoMapperExt sysFileInfoMapperExt;

	@Autowired
	private SysFileInfoMapStruct sysFileInfoMapStruct;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findListByServiceBO(SysFileInfoPageQueryServiceBO serviceBO) {
		Integer fileStorageTypeEnum = serviceBO.getFileStorageTypeEnum();
		if (null == fileStorageTypeEnum) {
			serviceBO.setFileStorageTypeEnum(uploadParamProperties.getUploadOssTypeEnum().getCode());
		}
		return sysFileInfoMapper.selectByPageQueryMapperBo(sysFileInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysFileInfoPageQueryServiceBO serviceBO) {
		Integer fileStorageTypeEnum = serviceBO.getFileStorageTypeEnum();
		if (null == fileStorageTypeEnum) {
			serviceBO.setFileStorageTypeEnum(uploadParamProperties.getUploadOssTypeEnum().getCode());
		}
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysFileInfoMapper.selectByPageQueryMapperBo(sysFileInfoMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysFileInfo> findDistinctFileSuffixList() {
		return sysFileInfoMapperExt.selectDistinctFileSuffix();
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

