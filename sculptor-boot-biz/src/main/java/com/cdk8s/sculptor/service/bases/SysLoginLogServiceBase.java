/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.mapper.SysLoginLogMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.*;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class SysLoginLogServiceBase {

	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public SysLoginLog findOneById(IdServiceBO serviceBO) {
		return sysLoginLogMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysLoginLog findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysLoginLogMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public SysLoginLog findOneByUserId(SysLoginLogUserIdServiceBO serviceBO) {
		return sysLoginLogMapper.selectOneByUserId(sysLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysLoginLog findOneByUserIdNotEnum(SysLoginLogUserIdServiceBO serviceBO) {
		SysLoginLogUserIdMapperBO mapperBO = sysLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return sysLoginLogMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByUserId(SysLoginLogUserIdServiceBO serviceBO) {
		return sysLoginLogMapper.selectByUserId(sysLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByUserIdList(SysLoginLogUserIdListServiceBO serviceBO) {
		return sysLoginLogMapper.selectByUserIdList(sysLoginLogMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByToken(SysLoginLogTokenServiceBO serviceBO) {
		return sysLoginLogMapper.selectByToken(sysLoginLogMapStruct.tokenServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByUsername(SysLoginLogUsernameServiceBO serviceBO) {
		return sysLoginLogMapper.selectByUsername(sysLoginLogMapStruct.usernameServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByUsernameList(SysLoginLogUsernameListServiceBO serviceBO) {
		return sysLoginLogMapper.selectByUsernameList(sysLoginLogMapStruct.usernameListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByIdList(IdListServiceBO serviceBO) {
		return sysLoginLogMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysLoginLog findOneByServiceBO(SysLoginLogPageQueryServiceBO serviceBO) {
		List<SysLoginLog> list = sysLoginLogMapper.selectByPageQueryMapperBo(sysLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<SysLoginLog> findListByServiceBO(SysLoginLogPageQueryServiceBO serviceBO) {
		return sysLoginLogMapper.selectByPageQueryMapperBo(sysLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(SysLoginLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysLoginLogMapper.selectByPageQueryMapperBo(sysLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	public Integer create(SysLoginLogCreateServiceBO serviceBO) {
		SysLoginLog entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysLoginLogMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(SysLoginLogUpdateServiceBO serviceBO) {
		return sysLoginLogMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysLoginLogMapper.deleteByIdList(sysLoginLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(SysLoginLogUserIdListToDeleteServiceBO serviceBO) {
		return sysLoginLogMapper.deleteByUserIdList(sysLoginLogMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysLoginLog initCreateBasicParam(SysLoginLogCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getBoolLoginSuccessEnum()) {
			serviceBO.setBoolLoginSuccessEnum(1);
		}
		if (null == serviceBO.getBoolNowOnlineEnum()) {
			serviceBO.setBoolNowOnlineEnum(1);
		}
		if (null == serviceBO.getBoolNewUserEnum()) {
			serviceBO.setBoolNewUserEnum(1);
		}
		if (null == serviceBO.getLoginOriginEnum()) {
			serviceBO.setLoginOriginEnum(1);
		}

		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return sysLoginLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysLoginLog initUpdateBasicParam(SysLoginLogUpdateServiceBO serviceBO) {
		return sysLoginLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

