/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDictMapper;
import com.cdk8s.sculptor.mapper.ext.SysDictMapperExt;
import com.cdk8s.sculptor.mapstruct.SysDictMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemDictIdListServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.service.bases.SysDictServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CacheConfig(cacheNames = "SysDictService")
@Service
public class SysDictService extends SysDictServiceBase {

	@Autowired
	private SysDictMapper sysDictMapper;

	@Autowired
	private SysDictMapperExt sysDictMapperExt;

	@Autowired
	private SysDictMapStruct sysDictMapStruct;

	@Autowired
	private SysDictItemService sysDictItemService;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		int result = sysDictMapper.updateDeleteEnumByIdList(sysDictMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
		if (result == 0) {
			throw new BusinessException("删除字典失败");
		}

		// 同时软删除其下的子元素
		SysDictItemDictIdListServiceBO sysDictItemDictIdListServiceBO = new SysDictItemDictIdListServiceBO();
		sysDictItemDictIdListServiceBO.setDictIdList(serviceBO.getIdList());
		sysDictItemDictIdListServiceBO.setTenantId(serviceBO.getTenantId());
		sysDictItemDictIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysDictItem> sysDictItemList = sysDictItemService.findListByDictIdList(sysDictItemDictIdListServiceBO);
		if (CollectionUtil.isEmpty(sysDictItemList)) {
			return result;
		}
		List<Long> dictItemIdList = sysDictItemList.stream()
				.map(BaseEntity::getId)
				.collect(Collectors.toList());

		BatchDeleteServiceBO batchDeleteServiceBO = new BatchDeleteServiceBO();
		batchDeleteServiceBO.setIdList(dictItemIdList);
		batchDeleteServiceBO.setDeleteUserId(serviceBO.getUpdateUserId());
		batchDeleteServiceBO.setDeleteDate(serviceBO.getUpdateDate());
		sysDictItemService.batchDelete(batchDeleteServiceBO);
		return result;
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

