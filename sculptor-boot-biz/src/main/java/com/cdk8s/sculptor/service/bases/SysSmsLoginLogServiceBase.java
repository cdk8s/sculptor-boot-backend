/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogServiceBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.bases;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.mapper.SysSmsLoginLogMapper;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysSmsLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syssmsloginlog.SysSmsLoginLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syssmsloginlog.SysSmsLoginLogUserMobilePhoneMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.*;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
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
public class SysSmsLoginLogServiceBase {

	@Autowired
	private SysSmsLoginLogMapper sysSmsLoginLogMapper;

	@Autowired
	private SysSmsLoginLogMapStruct sysSmsLoginLogMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneById(IdServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectById(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByIdNotEnum(IdServiceBO serviceBO) {
		IdMapperBO mapperBO = idAndIdListMapStruct.serviceBOToMapperBO(serviceBO);
		return sysSmsLoginLogMapper.selectById(mapperBO);
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByUserId(SysSmsLoginLogUserIdServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectOneByUserId(sysSmsLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByUserIdNotEnum(SysSmsLoginLogUserIdServiceBO serviceBO) {
		SysSmsLoginLogUserIdMapperBO mapperBO = sysSmsLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO);
		return sysSmsLoginLogMapper.selectOneByUserId(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByUserId(SysSmsLoginLogUserIdServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByUserId(sysSmsLoginLogMapStruct.userIdServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByUserIdList(SysSmsLoginLogUserIdListServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByUserIdList(sysSmsLoginLogMapStruct.userIdListServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByUserMobilePhone(SysSmsLoginLogUserMobilePhoneServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectOneByUserMobilePhone(sysSmsLoginLogMapStruct.userMobilePhoneServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByUserMobilePhoneNotEnum(SysSmsLoginLogUserMobilePhoneServiceBO serviceBO) {
		SysSmsLoginLogUserMobilePhoneMapperBO mapperBO = sysSmsLoginLogMapStruct.userMobilePhoneServiceBOToMapperBO(serviceBO);
		return sysSmsLoginLogMapper.selectOneByUserMobilePhone(mapperBO);
	}

	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByUserMobilePhone(SysSmsLoginLogUserMobilePhoneServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByUserMobilePhone(sysSmsLoginLogMapStruct.userMobilePhoneServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByUserMobilePhoneList(SysSmsLoginLogUserMobilePhoneListServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByUserMobilePhoneList(sysSmsLoginLogMapStruct.userMobilePhoneListServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByIdList(IdListServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByIdList(idAndIdListMapStruct.serviceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public SysSmsLoginLog findOneByServiceBO(SysSmsLoginLogPageQueryServiceBO serviceBO) {
		List<SysSmsLoginLog> list = sysSmsLoginLogMapper.selectByPageQueryMapperBo(sysSmsLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	public List<SysSmsLoginLog> findListByServiceBO(SysSmsLoginLogPageQueryServiceBO serviceBO) {
		return sysSmsLoginLogMapper.selectByPageQueryMapperBo(sysSmsLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	public PageInfo findPageByServiceBO(SysSmsLoginLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysSmsLoginLogMapper.selectByPageQueryMapperBo(sysSmsLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 该方法名不允许修改，被用在反射上
	 */
	public void cacheEvict() {
		// 用来主动清除所有缓存数据
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer create(SysSmsLoginLogCreateServiceBO serviceBO) {
		SysSmsLoginLog entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysSmsLoginLogMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(SysSmsLoginLogUpdateServiceBO serviceBO) {
		SysSmsLoginLog entity = initUpdateBasicParam(serviceBO);
		return sysSmsLoginLogMapper.updateByPrimaryKeySelective(entity);
	}


	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysSmsLoginLogMapper.deleteByIdList(sysSmsLoginLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserIdList(SysSmsLoginLogUserIdListToDeleteServiceBO serviceBO) {
		return sysSmsLoginLogMapper.deleteByUserIdList(sysSmsLoginLogMapStruct.userIdListToDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer batchDeleteByUserMobilePhoneList(SysSmsLoginLogUserMobilePhoneListToDeleteServiceBO serviceBO) {
		return sysSmsLoginLogMapper.deleteByUserMobilePhoneList(sysSmsLoginLogMapStruct.userMobilePhoneListToDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	public SysSmsLoginLog initCreateBasicParam(SysSmsLoginLogCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getSmsProviderTypeEnum()) {
			serviceBO.setSmsProviderTypeEnum(1);
		}
		if (null == serviceBO.getBoolServiceStateEnum()) {
			serviceBO.setBoolServiceStateEnum(2);
		}
		if (null == serviceBO.getBoolUseEnum()) {
			serviceBO.setBoolUseEnum(2);
		}

		if (null == serviceBO.getCreateDate()) {
			serviceBO.setCreateDate(DatetimeUtil.currentEpochMilli());
		}
		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}


		return sysSmsLoginLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	public SysSmsLoginLog initUpdateBasicParam(SysSmsLoginLogUpdateServiceBO serviceBO) {
		return sysSmsLoginLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

