/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.mapper.SysJobLogMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysJobLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjoblog.SysJobLogJobIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysjoblog.*;
import com.cdk8s.sculptor.pojo.entity.SysJobLog;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class SysJobLogServiceBase {

	@Autowired
	private SysJobLogMapper sysJobLogMapper;

	@Autowired
	private SysJobLogMapStruct sysJobLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public SysJobLog findOneById(IdServiceBO serviceBO) {
		return sysJobLogMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysJobLog findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysJobLogMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public SysJobLog findOneByJobId(SysJobLogJobIdServiceBO serviceBO) {
		return sysJobLogMapper.selectOneByJobId(sysJobLogMapStruct.jobIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysJobLog findOneByJobIdNotEnum(SysJobLogJobIdServiceBO serviceBO) {
		SysJobLogJobIdMapperBO mapperBO = sysJobLogMapStruct.jobIdServiceBOToMapperBO(serviceBO);
		return sysJobLogMapper.selectOneByJobId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<SysJobLog> findListByJobId(SysJobLogJobIdServiceBO serviceBO) {
		return sysJobLogMapper.selectByJobId(sysJobLogMapStruct.jobIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysJobLog> findListByJobIdList(SysJobLogJobIdListServiceBO serviceBO) {
		return sysJobLogMapper.selectByJobIdList(sysJobLogMapStruct.jobIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysJobLog> findListByJobName(SysJobLogJobNameServiceBO serviceBO) {
		return sysJobLogMapper.selectByJobName(sysJobLogMapStruct.jobNameServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysJobLog> findListByJobNameList(SysJobLogJobNameListServiceBO serviceBO) {
		return sysJobLogMapper.selectByJobNameList(sysJobLogMapStruct.jobNameListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysJobLog> findListByIdList(IdListServiceBO serviceBO) {
		return sysJobLogMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysJobLog findOneByServiceBO(SysJobLogPageQueryServiceBO serviceBO) {
		List<SysJobLog> list = sysJobLogMapper.selectByPageQueryMapperBo(sysJobLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<SysJobLog> findListByServiceBO(SysJobLogPageQueryServiceBO serviceBO) {
		return sysJobLogMapper.selectByPageQueryMapperBo(sysJobLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(SysJobLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysJobLogMapper.selectByPageQueryMapperBo(sysJobLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	public Integer create(SysJobLogCreateServiceBO serviceBO) {
		SysJobLog entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysJobLogMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(SysJobLogUpdateServiceBO serviceBO) {
		return sysJobLogMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysJobLogMapper.deleteByIdList(sysJobLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByJobIdList(SysJobLogJobIdListToDeleteServiceBO serviceBO) {
		return sysJobLogMapper.deleteByJobIdList(sysJobLogMapStruct.jobIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysJobLog initCreateBasicParam(SysJobLogCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBoolExecuteSuccessEnum()) {
			serviceBO.setBoolExecuteSuccessEnum(1);
		}

		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return sysJobLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysJobLog initUpdateBasicParam(SysJobLogUpdateServiceBO serviceBO) {
		return sysJobLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

