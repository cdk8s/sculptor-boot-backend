package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelDeptUserMapper;
import com.cdk8s.sculptor.mapstruct.RelDeptUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserDeptIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import com.cdk8s.sculptor.util.CollectionUtil;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "RelDeptUserService")
@Service
public class RelDeptUserService {

	@Autowired
	private RelDeptUserMapper relDeptUserMapper;

	@Autowired
	private RelDeptUserMapStruct relDeptUserMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelDeptUser findOneById(Long id) {
		return relDeptUserMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelDeptUser findOneByDeptId(Long deptId) {
		return relDeptUserMapper.selectOneByDeptId(new RelDeptUserDeptIdMapperBO(deptId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByDeptId(Long deptId) {
		return relDeptUserMapper.selectByDeptId(new RelDeptUserDeptIdMapperBO(deptId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByDeptIdList(List<Long> deptIdList) {
		return relDeptUserMapper.selectByDeptIdList(new RelDeptUserDeptIdMapperBO(deptIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelDeptUser findOneByUserId(Long userId) {
		return relDeptUserMapper.selectOneByUserId(new RelDeptUserUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByUserId(Long userId) {
		return relDeptUserMapper.selectByUserId(new RelDeptUserUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByUserIdList(List<Long> userIdList) {
		return relDeptUserMapper.selectByUserIdList(new RelDeptUserUserIdMapperBO(userIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByIdList(List<Long> idList) {
		return relDeptUserMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelDeptUser> findListByServiceBO(RelDeptUserPageQueryServiceBO serviceBO) {
		return relDeptUserMapper.selectByPageQueryMapperBo(relDeptUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(RelDeptUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relDeptUserMapper.selectByPageQueryMapperBo(relDeptUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(RelDeptUserCreateServiceBO serviceBO) {
		return relDeptUserMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchCreate(RelDeptUserBatchCreateServiceBO serviceBO) {

		List<Long> deptIdList = serviceBO.getDeptIdList();
		List<Long> userIdList = serviceBO.getUserIdList();

		if (CollectionUtil.isEmpty(deptIdList)) {
			throw new BusinessException("必须至少选择一个部门");
		}

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个用户");
		}

		// 先删除旧数据，然后再新增新数据（自己判断哪个外键是核心）
		batchDeleteByUserIdList(userIdList);

		List<RelDeptUser> entityList = new ArrayList<>();
		for (Long userId : userIdList) {
			for (Long deptId : deptIdList) {
				RelDeptUserCreateServiceBO createServiceBO = new RelDeptUserCreateServiceBO();
				createServiceBO.setUserId(userId);
				createServiceBO.setDeptId(deptId);
				entityList.add(initCreateBasicParam(createServiceBO));
			}
		}

		return relDeptUserMapper.batchInsertList(entityList);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(RelDeptUserUpdateServiceBO serviceBO) {
		return relDeptUserMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relDeptUserMapper.deleteByIdList(relDeptUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByDeptIdList(List<Long> deptIdList) {
		return relDeptUserMapper.deleteByDeptIdList(new RelDeptUserDeptIdMapperBO(deptIdList));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(List<Long> userIdList) {
		return relDeptUserMapper.deleteByUserIdList(new RelDeptUserUserIdMapperBO(userIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private RelDeptUser initCreateBasicParam(RelDeptUserCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return relDeptUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	private RelDeptUser initUpdateBasicParam(RelDeptUserUpdateServiceBO serviceBO) {
		return relDeptUserMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

