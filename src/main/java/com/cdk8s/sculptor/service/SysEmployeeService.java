package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysEmployeeMapper;
import com.cdk8s.sculptor.mapstruct.SysEmployeeMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeBatchDeleteByUserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
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
@CacheConfig(cacheNames = "SysEmployeeService")
@Service
public class SysEmployeeService {

	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	@Autowired
	private SysEmployeeMapStruct sysEmployeeMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysEmployee findOneById(Long id) {
		return sysEmployeeMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysEmployee findOneByUserId(Long userId) {
		return sysEmployeeMapper.selectOneByUserId(new SysEmployeeUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEmployee> findListByUserId(Long userId) {
		return sysEmployeeMapper.selectByUserId(new SysEmployeeUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEmployee> findListByUserIdList(List<Long> userIdList) {
		return sysEmployeeMapper.selectByUserIdList(new SysEmployeeUserIdMapperBO(userIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEmployee> findListByIdList(List<Long> idList) {
		return sysEmployeeMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEmployee> findListByServiceBO(SysEmployeePageQueryServiceBO serviceBO) {
		return sysEmployeeMapper.selectByPageQueryMapperBo(sysEmployeeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysEmployeePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysEmployeeMapper.selectByPageQueryMapperBo(sysEmployeeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBOToUser(SysEmployeePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysEmployeeMapper.selectByPageQueryMapperBoToUser(sysEmployeeMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysEmployeeCreateServiceBO serviceBO) {
		return sysEmployeeMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysEmployeeUpdateServiceBO serviceBO) {
		return sysEmployeeMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysEmployeeMapper.updateDeleteEnumByIdList(sysEmployeeMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(SysEmployeeBatchDeleteByUserIdServiceBO serviceBO) {
		return sysEmployeeMapper.updateDeleteEnumByUserIdList(sysEmployeeMapStruct.batchDeleteByUserIdServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysEmployee initCreateBasicParam(SysEmployeeCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return sysEmployeeMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysEmployee initUpdateBasicParam(SysEmployeeUpdateServiceBO serviceBO) {
		return sysEmployeeMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

