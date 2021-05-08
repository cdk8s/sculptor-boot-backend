/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.mapper.SysEventLogMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysEventLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.*;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class SysEventLogServiceBase {

	@Autowired
	private SysEventLogMapper sysEventLogMapper;

	@Autowired
	private SysEventLogMapStruct sysEventLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public SysEventLog findOneById(IdServiceBO serviceBO) {
		return sysEventLogMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysEventLog findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysEventLogMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public SysEventLog findOneByUserId(SysEventLogUserIdServiceBO serviceBO) {
		return sysEventLogMapper.selectOneByUserId(sysEventLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysEventLog findOneByUserIdNotEnum(SysEventLogUserIdServiceBO serviceBO) {
		SysEventLogUserIdMapperBO mapperBO = sysEventLogMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return sysEventLogMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<SysEventLog> findListByUserId(SysEventLogUserIdServiceBO serviceBO) {
		return sysEventLogMapper.selectByUserId(sysEventLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysEventLog> findListByUserIdList(SysEventLogUserIdListServiceBO serviceBO) {
		return sysEventLogMapper.selectByUserIdList(sysEventLogMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysEventLog> findListByUsername(SysEventLogUsernameServiceBO serviceBO) {
		return sysEventLogMapper.selectByUsername(sysEventLogMapStruct.usernameServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysEventLog> findListByUsernameList(SysEventLogUsernameListServiceBO serviceBO) {
		return sysEventLogMapper.selectByUsernameList(sysEventLogMapStruct.usernameListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysEventLog> findListByIdList(IdListServiceBO serviceBO) {
		return sysEventLogMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysEventLog findOneByServiceBO(SysEventLogPageQueryServiceBO serviceBO) {
		List<SysEventLog> list = sysEventLogMapper.selectByPageQueryMapperBo(sysEventLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<SysEventLog> findListByServiceBO(SysEventLogPageQueryServiceBO serviceBO) {
		return sysEventLogMapper.selectByPageQueryMapperBo(sysEventLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(SysEventLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysEventLogMapper.selectByPageQueryMapperBo(sysEventLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	public Integer create(SysEventLogCreateServiceBO serviceBO) {
		SysEventLog entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysEventLogMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(SysEventLogUpdateServiceBO serviceBO) {
		return sysEventLogMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysEventLogMapper.deleteByIdList(sysEventLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(SysEventLogUserIdListToDeleteServiceBO serviceBO) {
		return sysEventLogMapper.deleteByUserIdList(sysEventLogMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysEventLog initCreateBasicParam(SysEventLogCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBoolExecuteSuccessEnum()) {
			serviceBO.setBoolExecuteSuccessEnum(1);
		}
		if (null == serviceBO.getOperateTypeEnum()) {
			serviceBO.setOperateTypeEnum(1);
		}

		if (null == serviceBO.getCreateDate()) {
			serviceBO.setCreateDate(DatetimeUtil.currentEpochMilli());
		}
		if (null == serviceBO.getCreateUserId()) {
			serviceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return sysEventLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysEventLog initUpdateBasicParam(SysEventLogUpdateServiceBO serviceBO) {
		return sysEventLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

