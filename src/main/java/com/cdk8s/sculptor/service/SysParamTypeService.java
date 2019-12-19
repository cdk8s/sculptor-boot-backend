package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapper.SysParamTypeMapper;
import com.cdk8s.sculptor.mapstruct.SysParamTypeMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypeTypeCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparamtype.SysParamTypeCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparamtype.SysParamTypePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparamtype.SysParamTypeUpdateServiceBO;
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
@CacheConfig(cacheNames = "SysParamTypeService")
@Service
public class SysParamTypeService {

	@Autowired
	private SysParamTypeMapper sysParamTypeMapper;

	@Autowired
	private SysParamTypeMapStruct sysParamTypeMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysParamType findOneById(Long id) {
		return sysParamTypeMapper.selectById(new IdMapperBO(id));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParamType> findListByTypeCode(String typeCode) {
		return sysParamTypeMapper.selectByTypeCode(new SysParamTypeTypeCodeMapperBO(typeCode));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParamType> findListByTypeCodeList(List<String> typeCodeList) {
		return sysParamTypeMapper.selectByTypeCodeList(new SysParamTypeTypeCodeMapperBO(typeCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParamType> findListByIdList(List<Long> idList) {
		return sysParamTypeMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysParamType> findListByServiceBO(SysParamTypePageQueryServiceBO serviceBO) {
		return sysParamTypeMapper.selectByPageQueryMapperBo(sysParamTypeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysParamTypePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysParamTypeMapper.selectByPageQueryMapperBo(sysParamTypeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysParamTypeCreateServiceBO serviceBO) {
		return sysParamTypeMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysParamTypeUpdateServiceBO serviceBO) {
		return sysParamTypeMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysParamTypeMapper.updateStateEnumByIdList(sysParamTypeMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysParamTypeMapper.updateDeleteEnumByIdList(sysParamTypeMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysParamType initCreateBasicParam(SysParamTypeCreateServiceBO serviceBO) {
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

		return sysParamTypeMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysParamType initUpdateBasicParam(SysParamTypeUpdateServiceBO serviceBO) {
		return sysParamTypeMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

