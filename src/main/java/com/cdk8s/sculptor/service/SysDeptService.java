package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDeptMapper;
import com.cdk8s.sculptor.mapstruct.SysDeptMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptDeptCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.SysDeptCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.SysDeptPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.SysDeptUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import com.cdk8s.sculptor.pojo.entity.SysDept;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysDeptService")
@Service
public class SysDeptService {

	@Autowired
	private RelDeptUserService relDeptUserService;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private SysDeptMapStruct sysDeptMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDept findOneById(Long id) {
		return sysDeptMapper.selectById(new IdMapperBO(id));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByDeptCode(String deptCode) {
		return sysDeptMapper.selectByDeptCode(new SysDeptDeptCodeMapperBO(deptCode));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByDeptCodeList(List<String> deptCodeList) {
		return sysDeptMapper.selectByDeptCodeList(new SysDeptDeptCodeMapperBO(deptCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findRecursionListByParentId(Long parentId) {
		//zchtodo 这个方法还没测试，要看下是否平级输出
		if (null == parentId) {
			parentId = 1L;
		}

		List<SysDept> result = new ArrayList<>();

		SysDept parentObject = findOneById(parentId);
		result.add(parentObject);

		List<SysDept> childrenList = findListByParentId(parentId);
		recursionToList(result, childrenList);
		return result;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findTreeListByParentId(Long parentId) {
		if (null == parentId) {
			parentId = 1L;
		}

		SysDept parentObject = findOneById(parentId);
		List<SysDept> childrenList = findListByParentId(parentId);
		recursionToTreeList(parentObject, childrenList);
		return childrenList;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByParentId(Long parentId) {
		return sysDeptMapper.selectByParentId(new ParentIdMapperBO(parentId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByParentIdList(List<Long> parentIdList) {
		return sysDeptMapper.selectByParentIdList(new ParentIdMapperBO(parentIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByIdList(List<Long> idList) {
		return sysDeptMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDept> findListByServiceBO(SysDeptPageQueryServiceBO serviceBO) {
		return sysDeptMapper.selectByPageQueryMapperBo(sysDeptMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysDeptPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysDeptMapper.selectByPageQueryMapperBo(sysDeptMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysDeptCreateServiceBO serviceBO) {
		return sysDeptMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysDeptUpdateServiceBO serviceBO) {
		return sysDeptMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysDeptMapper.updateStateEnumByIdList(sysDeptMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> idList = serviceBO.getIdList();

		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultDeptIdList(), idList);
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除");
		}

		// 如果下面有子节点不允许删除
		List<SysDept> listByParentIdList = findListByParentIdList(idList);
		if (CollectionUtil.isNotEmpty(listByParentIdList)) {
			throw new BusinessException("部门下还有子部门，请先删除子部门");
		}

		// 如果该部门下面有人绑定不允许被删除
		List<RelDeptUser> listByDeptIdList = relDeptUserService.findListByDeptIdList(idList);
		if (CollectionUtil.isNotEmpty(listByDeptIdList)) {
			throw new BusinessException("部门下还有用户，请先解绑关联关系");
		}

		return sysDeptMapper.updateDeleteEnumByIdList(sysDeptMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysDept initCreateBasicParam(SysDeptCreateServiceBO serviceBO) {
		List<SysDept> listByDeptCode = findListByDeptCode(serviceBO.getDeptCode());
		if (CollectionUtil.isNotEmpty(listByDeptCode)) {
			throw new BusinessException("编码已经存在");
		}

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
			SysDept parentObject = findOneById(parentId);
			if (null == parentObject) {
				throw new BusinessException("该父节点不存在");
			}
			serviceBO.setParentIds(parentObject.getParentIds() + "," + parentId);
		}

		return sysDeptMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysDept initUpdateBasicParam(SysDeptUpdateServiceBO serviceBO) {
		return sysDeptMapStruct.updateServiceBOToEntity(serviceBO);
	}


	private void recursionToTreeList(SysDept parentObject, List<SysDept> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			if (null != parentObject) {
				// 数据库没有设置顶层节点的信息，当 parentId = 1L 的时候 parentObject 为 null
				parentObject.setBoolParentEnum(BooleanEnum.YES.getCode());
				parentObject.setChildren(childrenList);
			}

			for (SysDept entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysDept> children = findListByParentId(entity.getId());
				recursionToTreeList(entity, children);
			}
		}
	}


	private void recursionToList(List<SysDept> result, List<SysDept> childrenList) {
		if (CollectionUtil.isNotEmpty(childrenList)) {
			result.addAll(childrenList);
			for (SysDept entity : childrenList) {
				if (entity.getParentId().equals(entity.getId())) {
					// 避免错误数据造成的死循环
					continue;
				}
				List<SysDept> children = findListByParentId(entity.getId());
				recursionToList(result, children);
			}
		}
	}
	// =====================================私有方法 end=====================================

}

