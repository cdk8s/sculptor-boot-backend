/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEmployeeService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysEmployeeMapper;
import com.cdk8s.sculptor.mapper.ext.SysEmployeeMapperExt;
import com.cdk8s.sculptor.mapstruct.SysEmployeeMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeePageQueryServiceBO;
import com.cdk8s.sculptor.service.bases.SysEmployeeServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DefaultDataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysEmployeeService")
@Service
public class SysEmployeeService extends SysEmployeeServiceBase {

	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	@Autowired
	private SysEmployeeMapperExt sysEmployeeMapperExt;

	@Autowired
	private SysEmployeeMapStruct sysEmployeeMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBOToUser(SysEmployeePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysEmployeeMapperExt.selectByPageQueryMapperBoToUser(sysEmployeeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {

		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultEmployeeIdList(), serviceBO.getIdList());
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除，建议禁用其状态");
		}

		return sysEmployeeMapper.updateDeleteEnumByIdList(sysEmployeeMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

