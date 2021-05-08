/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFolderInfoService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.util.AssertUtil;
import com.cdk8s.sculptor.mapper.SysFolderInfoMapper;
import com.cdk8s.sculptor.mapper.ext.SysFolderInfoMapperExt;
import com.cdk8s.sculptor.mapstruct.SysFolderInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.SysFileInfoFolderIdListServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import com.cdk8s.sculptor.pojo.entity.SysFolderInfo;
import com.cdk8s.sculptor.service.bases.SysFolderInfoServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysFolderInfoService")
@Service
public class SysFolderInfoService extends SysFolderInfoServiceBase {

	@Autowired
	private SysFolderInfoMapper sysFolderInfoMapper;

	@Autowired
	private SysFolderInfoMapperExt sysFolderInfoMapperExt;

	@Autowired
	private SysFolderInfoMapStruct sysFolderInfoMapStruct;

	@Autowired
	private SysFileInfoService sysFileInfoService;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		// 判断目录下是否有子文件夹、文件，有则不允许删除
		List<Long> idList = serviceBO.getIdList();
		AssertUtil.notBeNull(idList, "idList 不能为空");

		ParentIdListServiceBO parentIdListServiceBO = new ParentIdListServiceBO();
		parentIdListServiceBO.setParentIdList(idList);
		parentIdListServiceBO.setTenantId(serviceBO.getTenantId());
		parentIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysFolderInfo> listByParentIdList = findListByParentIdList(parentIdListServiceBO);
		if (CollectionUtil.isNotEmpty(listByParentIdList)) {
			throw new BusinessException("请先删除子文件夹");
		}

		SysFileInfoFolderIdListServiceBO sysFileInfoFolderIdListServiceBO = new SysFileInfoFolderIdListServiceBO();
		sysFileInfoFolderIdListServiceBO.setFolderIdList(idList);
		sysFileInfoFolderIdListServiceBO.setTenantId(serviceBO.getTenantId());
		sysFileInfoFolderIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysFileInfo> listByFolderIdList = sysFileInfoService.findListByFolderIdList(sysFileInfoFolderIdListServiceBO);
		if (CollectionUtil.isNotEmpty(listByFolderIdList)) {
			throw new BusinessException("请先删除文件夹下的文件");
		}

		return sysFolderInfoMapper.updateDeleteEnumByIdList(sysFolderInfoMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

