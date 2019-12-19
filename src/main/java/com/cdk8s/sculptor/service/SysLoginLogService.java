package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.SysLoginLogMapper;
import com.cdk8s.sculptor.mapstruct.SysLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogTokenMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogUsernameMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
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
@CacheConfig(cacheNames = "SysLoginLogService")
@Service
public class SysLoginLogService {

	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;

	@Autowired
	private SysLoginLogMapStruct sysLoginLogMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysLoginLog findOneById(Long id) {
		return sysLoginLogMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysLoginLog findOneByUserId(Long userId) {
		return sysLoginLogMapper.selectOneByUserId(new SysLoginLogUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByUserId(Long userId) {
		return sysLoginLogMapper.selectByUserId(new SysLoginLogUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByUserIdList(List<Long> userIdList) {
		return sysLoginLogMapper.selectByUserIdList(new SysLoginLogUserIdMapperBO(userIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByUsername(String username) {
		return sysLoginLogMapper.selectByUsername(new SysLoginLogUsernameMapperBO(username));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysLoginLog findOneByToken(String token) {
		return sysLoginLogMapper.selectOneByToken(new SysLoginLogTokenMapperBO(token));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByUsernameList(List<String> usernameList) {
		return sysLoginLogMapper.selectByUsernameList(new SysLoginLogUsernameMapperBO(usernameList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByIdList(List<Long> idList) {
		return sysLoginLogMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysLoginLog> findListByServiceBO(SysLoginLogPageQueryServiceBO serviceBO) {
		return sysLoginLogMapper.selectByPageQueryMapperBo(sysLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysLoginLogPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysLoginLogMapper.selectByPageQueryMapperBo(sysLoginLogMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysLoginLogCreateServiceBO serviceBO) {
		return sysLoginLogMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysLoginLogUpdateServiceBO serviceBO) {
		return sysLoginLogMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysLoginLogMapper.deleteByIdList(sysLoginLogMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(List<Long> userIdList) {
		return sysLoginLogMapper.deleteByUserIdList(new SysLoginLogUserIdMapperBO(userIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysLoginLog initCreateBasicParam(SysLoginLogCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return sysLoginLogMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysLoginLog initUpdateBasicParam(SysLoginLogUpdateServiceBO serviceBO) {
		return sysLoginLogMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

