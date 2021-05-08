/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictItemService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDictItemMapper;
import com.cdk8s.sculptor.mapper.ext.SysDictItemMapperExt;
import com.cdk8s.sculptor.mapstruct.SysDictItemMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import com.cdk8s.sculptor.service.bases.SysDictItemServiceBase;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@CacheConfig(cacheNames = "SysDictItemService")
@Service
public class SysDictItemService extends SysDictItemServiceBase {

	@Autowired
	private SysDictItemMapper sysDictItemMapper;

	@Autowired
	private SysDictItemMapperExt sysDictItemMapperExt;

	@Autowired
	private SysDictItemMapStruct sysDictItemMapStruct;

	@Autowired
	private SysDictService sysDictService;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

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

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysDictItem initCreateBasicParam(SysDictItemCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getDictId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysDict sysDict = sysDictService.findOneById(idServiceBO);
		if (null == sysDict) {
			throw new BusinessException("字典项不存在");
		}
		serviceBO.setDictCode(sysDict.getDictCode());
		return sysDictItemMapStruct.createServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

