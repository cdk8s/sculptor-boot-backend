package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysEventLogMapper;
import com.cdk8s.sculptor.mapstruct.SysEventLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogUsernameMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
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
@CacheConfig(cacheNames = "SysEventLogService")
@Service
public class SysEventLogService {

	@Autowired
	private SysEventLogMapper sysEventLogMapper;

	@Autowired
	private SysEventLogMapStruct sysEventLogMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysEventLog findOneById(Long id) {
		return sysEventLogMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysEventLog findOneByUserId(Long userId) {
		return sysEventLogMapper.selectOneByUserId(new SysEventLogUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByUserId(Long userId) {
		return sysEventLogMapper.selectByUserId(new SysEventLogUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByUserIdList(List<Long> userIdList) {
		return sysEventLogMapper.selectByUserIdList(new SysEventLogUserIdMapperBO(userIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByUsername(String username) {
		return sysEventLogMapper.selectByUsername(new SysEventLogUsernameMapperBO(username));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByUsernameList(List<String> usernameList) {
		return sysEventLogMapper.selectByUsernameList(new SysEventLogUsernameMapperBO(usernameList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByIdList(List<Long> idList) {
		return sysEventLogMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysEventLog> findListByServiceBO(SysEventLogPageQueryServiceBO serviceBO) {
		return sysEventLogMapper.selectByPageQueryMapperBo(sysEventLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysEventLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysEventLogMapper.selectByPageQueryMapperBo(sysEventLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysEventLogCreateServiceBO serviceBO) {
		return sysEventLogMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysEventLogUpdateServiceBO serviceBO) {
		return sysEventLogMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysEventLogMapper.deleteByIdList(sysEventLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(List<Long> userIdList) {
		return sysEventLogMapper.deleteByUserIdList(new SysEventLogUserIdMapperBO(userIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysEventLog initCreateBasicParam(SysEventLogCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return sysEventLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysEventLog initUpdateBasicParam(SysEventLogUpdateServiceBO serviceBO) {
		return sysEventLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

