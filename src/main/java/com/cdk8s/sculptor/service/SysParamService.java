package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.ParamValueTypeEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysParamMapper;
import com.cdk8s.sculptor.mapstruct.SysParamMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamParamCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamTypeCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamTypeIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
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
@CacheConfig(cacheNames = "SysParamService")
@Service
public class SysParamService {

	@Autowired
	private SysParamMapper sysParamMapper;

	@Autowired
	private SysParamTypeService sysParamTypeService;

	@Autowired
	private SysParamMapStruct sysParamMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParam findOneById(Long id) {
		return sysParamMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParam findOneByTypeId(Long typeId) {
		return sysParamMapper.selectOneByTypeId(new SysParamTypeIdMapperBO(typeId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByTypeId(Long typeId) {
		return sysParamMapper.selectByTypeId(new SysParamTypeIdMapperBO(typeId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByTypeIdList(List<Long> typeIdList) {
		return sysParamMapper.selectByTypeIdList(new SysParamTypeIdMapperBO(typeIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByTypeCode(String typeCode) {
		return sysParamMapper.selectByTypeCode(new SysParamTypeCodeMapperBO(typeCode));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParam findOneByParamCode(String paramCode) {
		return sysParamMapper.selectOneByParamCode(new SysParamParamCodeMapperBO(paramCode));
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
	public List<SysParam> findListByTypeCodeList(List<String> typeCodeList) {
		return sysParamMapper.selectByTypeCodeList(new SysParamTypeCodeMapperBO(typeCodeList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByParamCodeList(List<String> paramCodeList) {
		return sysParamMapper.selectByParamCodeList(new SysParamParamCodeMapperBO(paramCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByIdList(List<Long> idList) {
		return sysParamMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParam> findListByServiceBO(SysParamPageQueryServiceBO serviceBO) {
		return sysParamMapper.selectByPageQueryMapperBo(sysParamMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysParamPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysParamMapper.selectByPageQueryMapperBo(sysParamMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 该方法名不允许修改，被用在反射上
	 */
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void cacheEvict() {
		// 用来主动清除所有缓存数据
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysParamCreateServiceBO serviceBO) {
		return sysParamMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysParamUpdateServiceBO serviceBO) {
		return sysParamMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysParamMapper.updateStateEnumByIdList(sysParamMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysParamMapper.updateDeleteEnumByIdList(sysParamMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByTypeIdList(List<Long> typeIdList) {
		return sysParamMapper.updateDeleteEnumByTypeIdList(new SysParamTypeIdMapperBO(typeIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysParam initCreateBasicParam(SysParamCreateServiceBO serviceBO) {
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

		SysParam entity = sysParamMapStruct.createServiceBOToEntity(serviceBO);
		SysParamType sysParamType = sysParamTypeService.findOneById(serviceBO.getTypeId());
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

