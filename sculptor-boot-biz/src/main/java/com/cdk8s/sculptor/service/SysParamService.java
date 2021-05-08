/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.ParamValueTypeEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.mapper.SysParamMapper;
import com.cdk8s.sculptor.mapper.ext.SysParamMapperExt;
import com.cdk8s.sculptor.mapstruct.SysParamMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamParamCodeServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import com.cdk8s.sculptor.service.bases.SysParamServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysParamService")
@Service
public class SysParamService extends SysParamServiceBase {

	@Autowired
	private SysParamMapper sysParamMapper;

	@Autowired
	private SysParamMapperExt sysParamMapperExt;

	@Autowired
	private SysParamMapStruct sysParamMapStruct;

	@Autowired
	private SysParamTypeService sysParamTypeService;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParam findOneByParamCode(SysParamParamCodeServiceBO serviceBO) {
		List<SysParam> result = sysParamMapper.selectByParamCode(sysParamMapStruct.paramCodeServiceBOToMapperBO(serviceBO));
		if (CollectionUtil.isNotEmpty(result)) {
			return result.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParam findOneByParamCode(String paramCode) {
		SysParamParamCodeServiceBO sysParamParamCodeServiceBO = new SysParamParamCodeServiceBO();
		sysParamParamCodeServiceBO.setParamCode(paramCode);
		sysParamParamCodeServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysParamParamCodeServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysParam> result = sysParamMapper.selectByParamCode(sysParamMapStruct.paramCodeServiceBOToMapperBO(sysParamParamCodeServiceBO));
		if (CollectionUtil.isNotEmpty(result)) {
			return result.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public Object findOneValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			return convertValueType(result.getParamValueTypeEnum(), result.getParamValue());
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String findOneStringValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			try {
				return String.valueOf(result.getParamValue());
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public Long findOneLongValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			try {
				return Long.valueOf(result.getParamValue());
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public Integer findOneIntegerValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			try {
				return Integer.valueOf(result.getParamValue());
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public Boolean findOneBooleanValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			try {
				return Boolean.valueOf(result.getParamValue());
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public Double findOneDoubleValueByParamCode(String paramCode) {
		SysParam result = findOneByParamCode(paramCode);
		if (null != result) {
			try {
				return Double.valueOf(result.getParamValue());
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysParamCreateServiceBO serviceBO) {
		try {
			convertValueType(serviceBO.getParamValueTypeEnum(), serviceBO.getParamValue());
		} catch (Exception e) {
			throw new SystemException("参数值类型和实际参数不匹配，请重试");
		}

		SysParam entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysParamMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysParamUpdateServiceBO serviceBO) {
		try {
			convertValueType(serviceBO.getParamValueTypeEnum(), serviceBO.getParamValue());
		} catch (Exception e) {
			throw new SystemException("参数值类型和实际参数不匹配，请重试");
		}
		return sysParamMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysParam initCreateBasicParam(SysParamCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getParamValueTypeEnum()) {
			serviceBO.setParamValueTypeEnum(1);
		}

		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}
		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}

		SysParam entity = sysParamMapStruct.createServiceBOToEntity(serviceBO);

		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(serviceBO.getTypeId());
		idServiceBO.setTenantId(serviceBO.getTenantId());
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		SysParamType sysParamType = sysParamTypeService.findOneById(idServiceBO);
		if (null == sysParamType) {
			throw new BusinessException("类型编码不能为空");
		}
		entity.setTypeCode(sysParamType.getTypeCode());
		return entity;
	}

	private SysParam initUpdateBasicParam(SysParamUpdateServiceBO serviceBO) {
		return sysParamMapStruct.updateServiceBOToEntity(serviceBO);
	}

	private Object convertValueType(Integer typeValue, String stringValue) {
		switch (ParamValueTypeEnum.getEnumByCode(typeValue)) {
			case Boolean:
				return Boolean.valueOf(stringValue);
			case Integer:
				return Integer.valueOf(stringValue);
			case Long:
				return Long.valueOf(stringValue);
			case Double:
				return Double.valueOf(stringValue);
			default:
				return stringValue;
		}
	}
	// =====================================私有方法 end=====================================

}

