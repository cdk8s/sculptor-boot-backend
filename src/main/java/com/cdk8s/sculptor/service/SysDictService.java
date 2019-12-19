package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDictMapper;
import com.cdk8s.sculptor.mapstruct.SysDictMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictDictCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdict.SysDictCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdict.SysDictPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdict.SysDictUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
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
import java.util.stream.Collectors;

@Slf4j
@CacheConfig(cacheNames = "SysDictService")
@Service
public class SysDictService {

	@Autowired
	private SysDictItemService sysDictItemService;

	@Autowired
	private SysDictMapper sysDictMapper;

	@Autowired
	private SysDictMapStruct sysDictMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDict findOneById(Long id) {
		return sysDictMapper.selectById(new IdMapperBO(id));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDict> findListByDictCode(String dictCode) {
		return sysDictMapper.selectByDictCode(new SysDictDictCodeMapperBO(dictCode));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDict> findListByDictCodeList(List<String> dictCodeList) {
		return sysDictMapper.selectByDictCodeList(new SysDictDictCodeMapperBO(dictCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDict> findListByIdList(List<Long> idList) {
		return sysDictMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDict> findListByServiceBO(SysDictPageQueryServiceBO serviceBO) {
		return sysDictMapper.selectByPageQueryMapperBo(sysDictMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysDictPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysDictMapper.selectByPageQueryMapperBo(sysDictMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysDictCreateServiceBO serviceBO) {
		return sysDictMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysDictUpdateServiceBO serviceBO) {
		return sysDictMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysDictMapper.updateStateEnumByIdList(sysDictMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		int result = sysDictMapper.updateDeleteEnumByIdList(sysDictMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
		if (result == 0) {
			throw new BusinessException("删除字典失败");
		}

		// 同时软删除其下的子元素
		List<SysDictItem> sysDictItemList = sysDictItemService.findListByDictIdList(serviceBO.getIdList());
		if (CollectionUtil.isEmpty(sysDictItemList)) {
			return result;
		}
		List<Long> dictItemIdList = sysDictItemList.stream()
				.map(BaseEntity::getId)
				.collect(Collectors.toList());

		BatchDeleteServiceBO batchDeleteServiceBO = new BatchDeleteServiceBO();
		batchDeleteServiceBO.setIdList(dictItemIdList);
		batchDeleteServiceBO.setUpdateUserId(serviceBO.getUpdateUserId());
		batchDeleteServiceBO.setUpdateDate(serviceBO.getUpdateDate());
		batchDeleteServiceBO.setDeleteUserId(serviceBO.getUpdateUserId());
		batchDeleteServiceBO.setDeleteDate(serviceBO.getUpdateDate());
		sysDictItemService.batchDelete(batchDeleteServiceBO);
		return result;
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysDict initCreateBasicParam(SysDictCreateServiceBO serviceBO) {
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

		return sysDictMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysDict initUpdateBasicParam(SysDictUpdateServiceBO serviceBO) {
		return sysDictMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

