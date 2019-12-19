package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysRoleMapper;
import com.cdk8s.sculptor.mapstruct.SysRoleMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRoleRoleCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRolePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DefaultDataUtil;
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
import java.util.stream.Collectors;

@Slf4j
@CacheConfig(cacheNames = "SysRoleService")
@Service
public class SysRoleService {

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleMapStruct sysRoleMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysRole findOneById(Long id) {
		return sysRoleMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<Long> findRoleIdListByUserId(Long userId) {
		List<RelRoleUser> relRoleUserList = relRoleUserService.findListByUserId(userId);
		if (CollectionUtil.isEmpty(relRoleUserList)) {
			return CollectionUtil.emptyList();
		}

		return relRoleUserList.stream()
				.map(RelRoleUser::getRoleId)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByUserId(Long userId) {
		List<Long> roleIdList = findRoleIdListByUserId(userId);
		if (CollectionUtil.isEmpty(roleIdList)) {
			return CollectionUtil.emptyList();
		}

		return findListByIdList(roleIdList);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByRoleCode(String roleCode) {
		return sysRoleMapper.selectByRoleCode(new SysRoleRoleCodeMapperBO(roleCode));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByRoleCodeList(List<String> roleCodeList) {
		return sysRoleMapper.selectByRoleCodeList(new SysRoleRoleCodeMapperBO(roleCodeList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByIdList(List<Long> idList) {
		return sysRoleMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysRole> findListByServiceBO(SysRolePageQueryServiceBO serviceBO) {
		return sysRoleMapper.selectByPageQueryMapperBo(sysRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysRolePageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysRoleMapper.selectByPageQueryMapperBo(sysRoleMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysRoleCreateServiceBO serviceBO) {
		List<SysRole> listByRoleCode = findListByRoleCode(serviceBO.getRoleCode());
		if (CollectionUtil.isNotEmpty(listByRoleCode)) {
			throw new BusinessException("已存在该角色编码");
		}
		return sysRoleMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysRoleUpdateServiceBO serviceBO) {
		return sysRoleMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysRoleMapper.updateStateEnumByIdList(sysRoleMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultRoleIdList(), serviceBO.getIdList());
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除");
		}

		// 先判断该角色是否有用户在使用
		List<RelRoleUser> relRoleUserList = relRoleUserService.findListByRoleIdList(serviceBO.getIdList());
		if (CollectionUtil.isNotEmpty(relRoleUserList)) {
			throw new BusinessException("被删除的角色还有用户在使用，请先解绑角色关联的用户");
		}

		return sysRoleMapper.updateDeleteEnumByIdList(sysRoleMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysRole initCreateBasicParam(SysRoleCreateServiceBO serviceBO) {
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

		return sysRoleMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysRole initUpdateBasicParam(SysRoleUpdateServiceBO serviceBO) {
		SysRole entity = sysRoleMapStruct.updateServiceBOToEntity(serviceBO);
		// 角色编码不可修改
		entity.setRoleCode(null);
		return entity;
	}

	// =====================================私有方法 end=====================================

}

