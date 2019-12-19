package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysPermissionMapper;
import com.cdk8s.sculptor.mapstruct.SysPermissionMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdAndIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CacheConfig(cacheNames = "SysPermissionService")
@Service
public class SysPermissionService {

	@Autowired
	private RelPermissionRoleService relPermissionRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysPermissionMapStruct sysPermissionMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysPermission findOneById(Long id) {
		return sysPermissionMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByUserId(Long userId) {
		List<Long> roleIdList = sysRoleService.findRoleIdListByUserId(userId);
		if (CollectionUtil.isEmpty(roleIdList)) {
			return CollectionUtil.emptyList();
		}

		return findListByRoleIdList(roleIdList);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<String> findPermissionCodeListByUserId(Long userId) {
		List<SysPermission> sysPermissionList = findListByUserId(userId);
		if (CollectionUtil.isEmpty(sysPermissionList)) {
			return CollectionUtil.emptyList();
		}

		return sysPermissionList.stream()
				.map(SysPermission::getPermissionCode)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<Long> findPermissionIdListByUserId(Long userId) {
		List<SysPermission> sysPermissionList = findListByUserId(userId);
		if (CollectionUtil.isEmpty(sysPermissionList)) {
			return CollectionUtil.emptyList();
		}

		return sysPermissionList.stream()
				.map(BaseEntity::getId)
				.collect(Collectors.toList());
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByRoleIdList(List<Long> roleIdList) {
		List<RelPermissionRole> relPermissionRoleList = relPermissionRoleService.findListByRoleIdList(roleIdList);
		if (CollectionUtil.isEmpty(relPermissionRoleList)) {
			return CollectionUtil.emptyList();
		}

		// 用 set 去重
		Set<Long> permissionIdSet = relPermissionRoleList.stream()
				.map(RelPermissionRole::getPermissionId)
				.collect(Collectors.toSet());

		return findListByIdList(CollectionUtil.setToList(permissionIdSet));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByPermissionCode(String permissionCode) {
		return sysPermissionMapper.selectByPermissionCode(new SysPermissionPermissionCodeMapperBO(permissionCode));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByPermissionCodeList(List<String> permissionCodeList) {
		return sysPermissionMapper.selectByPermissionCodeList(new SysPermissionPermissionCodeMapperBO(permissionCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByParentId(Long parentId) {
		return sysPermissionMapper.selectByParentId(new ParentIdMapperBO(parentId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByParentIdList(List<Long> parentIdList) {
		return sysPermissionMapper.selectByParentIdList(new ParentIdMapperBO(parentIdList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByParentIdNotButton(Long parentId) {
		return sysPermissionMapper.selectByParentIdNotButton(new ParentIdMapperBO(parentId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findTreeListByParentId(Long parentId) {
		if (null == parentId) {
			parentId = GlobalConstant.TOP_PERMISSION_PARENT_ID;
		}

		SysPermission parentObject = findOneById(parentId);
		List<SysPermission> childrenList = findListByParentId(parentId);
		recursionToList(parentObject, childrenList);
		return childrenList;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findTreeListByParentIdNotButton(Long parentId) {
		if (null == parentId) {
			parentId = GlobalConstant.TOP_PERMISSION_PARENT_ID;
		}

		SysPermission parentObject = findOneById(parentId);
		List<SysPermission> childrenList = findListByParentIdNotButton(parentId);
		recursionToListNotButton(parentObject, childrenList);
		return childrenList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findTreeListByUserIdNotButton(Long userId) {
		if (null == userId) {
			throw new BusinessException("获取用户权限树的用户 ID 不能为空");
		}

		// 查询当前用户所有的权限（平级结构）
		List<Long> permissionIdListByUserId = findPermissionIdListByUserId(userId);
		if (CollectionUtil.isEmpty(permissionIdListByUserId)) {
			return CollectionUtil.emptyList();
		}

		// 查询出所有权限的树结构
		SysPermission parentObject = findOneById(GlobalConstant.TOP_PERMISSION_PARENT_ID);
		List<SysPermission> childrenList = sysPermissionMapper.selectByParentIdAndIdListNotButton(new ParentIdAndIdListMapperBO(GlobalConstant.TOP_PERMISSION_PARENT_ID, permissionIdListByUserId));

		recursionToListByUserPermissionIdListNotButton(parentObject, childrenList, permissionIdListByUserId);

		return childrenList;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByIdList(List<Long> idList) {
		return sysPermissionMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysPermission> findListByServiceBO(SysPermissionPageQueryServiceBO serviceBO) {
		return sysPermissionMapper.selectByPageQueryMapperBo(sysPermissionMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysPermissionPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysPermissionMapper.selectByPageQueryMapperBo(sysPermissionMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysPermissionCreateServiceBO serviceBO) {
		return sysPermissionMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysPermissionUpdateServiceBO serviceBO) {
		return sysPermissionMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysPermissionMapper.updateStateEnumByIdList(sysPermissionMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysPermissionMapper.updateDeleteEnumByIdList(sysPermissionMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysPermission initCreateBasicParam(SysPermissionCreateServiceBO serviceBO) {
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

		Long parentId = serviceBO.getParentId();
		if (parentId.equals(1L)) {
			serviceBO.setParentIds("1");
		} else {
			SysPermission parentObject = findOneById(parentId);
			if (null == parentObject) {
				throw new BusinessException("该父节点不存在");
			}
			serviceBO.setParentIds(parentObject.getParentIds() + "," + parentId);
		}

		return sysPermissionMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysPermission initUpdateBasicParam(SysPermissionUpdateServiceBO serviceBO) {
		return sysPermissionMapStruct.updateServiceBOToEntity(serviceBO);
	}


	private void recursionToList(SysPermission parentObject, List<SysPermission> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysPermission entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysPermission> children = findListByParentId(entity.getId());
				recursionToList(entity, children);
			}
		}
	}

	private void recursionToListNotButton(SysPermission parentObject, List<SysPermission> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysPermission entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysPermission> children = findListByParentIdNotButton(entity.getId());
				recursionToListNotButton(entity, children);
			}
		}
	}

	private void recursionToListByUserPermissionIdListNotButton(SysPermission parentObject, List<SysPermission> childrenList, List<Long> permissionIdList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysPermission entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysPermission> children = sysPermissionMapper.selectByParentIdAndIdListNotButton(new ParentIdAndIdListMapperBO(entity.getId(), permissionIdList));
				recursionToListByUserPermissionIdListNotButton(entity, children, permissionIdList);
			}
		}
	}


	// =====================================私有方法 end=====================================

}

