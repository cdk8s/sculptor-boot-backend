package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.RelRoleUserMapper;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdAndRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
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
@CacheConfig(cacheNames = "RelRoleUserService")
@Service
public class RelRoleUserService {

	@Autowired
	private RelRoleUserMapper relRoleUserMapper;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelRoleUser findOneById(Long id) {
		return relRoleUserMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelRoleUser findOneByRoleId(Long roleId) {
		return relRoleUserMapper.selectOneByRoleId(new RelRoleUserRoleIdMapperBO(roleId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByRoleId(Long roleId) {
		return relRoleUserMapper.selectByRoleId(new RelRoleUserRoleIdMapperBO(roleId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByRoleIdList(List<Long> roleIdList) {
		return relRoleUserMapper.selectByRoleIdList(new RelRoleUserRoleIdMapperBO(roleIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public RelRoleUser findOneByUserId(Long userId) {
		return relRoleUserMapper.selectOneByUserId(new RelRoleUserUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByUserId(Long userId) {
		return relRoleUserMapper.selectByUserId(new RelRoleUserUserIdMapperBO(userId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByUserIdList(List<Long> userIdList) {
		return relRoleUserMapper.selectByUserIdList(new RelRoleUserUserIdMapperBO(userIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByUserIdAndRoleId(Long userId, Long roleId) {
		return relRoleUserMapper.selectByUserIdAndRoleId(new RelRoleUserUserIdAndRoleIdMapperBO(userId, roleId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByIdList(List<Long> idList) {
		return relRoleUserMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<RelRoleUser> findListByServiceBO(RelRoleUserPageQueryServiceBO serviceBO) {
		return relRoleUserMapper.selectByPageQueryMapperBo(relRoleUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(RelRoleUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			relRoleUserMapper.selectByPageQueryMapperBo(relRoleUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(RelRoleUserCreateServiceBO serviceBO) {
		return relRoleUserMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchCreate(RelRoleUserBatchCreateServiceBO serviceBO) {

		List<Long> userIdList = serviceBO.getUserIdList();
		List<Long> roleIdList = serviceBO.getRoleIdList();

		if (CollectionUtil.isEmpty(userIdList)) {
			throw new BusinessException("必须至少选择一个用户");
		}

		if (CollectionUtil.isEmpty(roleIdList)) {
			throw new BusinessException("必须至少选择一个角色");
		}

		// 先删除用户所有旧数据，然后再新增新数据
		batchDeleteByUserIdList(userIdList);

		List<RelRoleUser> relRoleUserList = new ArrayList<>();
		for (Long userId : userIdList) {
			for (Long roleId : roleIdList) {
				RelRoleUserCreateServiceBO relRoleUserCreateServiceBO = new RelRoleUserCreateServiceBO();
				relRoleUserCreateServiceBO.setRoleId(roleId);
				relRoleUserCreateServiceBO.setUserId(userId);
				relRoleUserList.add(initCreateBasicParam(relRoleUserCreateServiceBO));
			}
		}

		return relRoleUserMapper.batchInsertList(relRoleUserList);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(RelRoleUserUpdateServiceBO serviceBO) {
		return relRoleUserMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return relRoleUserMapper.deleteByIdList(relRoleUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByRoleIdList(List<Long> roleIdList) {
		return relRoleUserMapper.deleteByRoleIdList(new RelRoleUserRoleIdMapperBO(roleIdList));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByUserIdList(List<Long> userIdList) {
		return relRoleUserMapper.deleteByUserIdList(new RelRoleUserUserIdMapperBO(userIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private RelRoleUser initCreateBasicParam(RelRoleUserCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		return relRoleUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	private RelRoleUser initUpdateBasicParam(RelRoleUserUpdateServiceBO serviceBO) {
		return relRoleUserMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

