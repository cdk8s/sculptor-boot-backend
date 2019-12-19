package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDictItemMapper;
import com.cdk8s.sculptor.mapstruct.SysDictItemMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemDictCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemDictIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemItemCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
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
@CacheConfig(cacheNames = "SysDictItemService")
@Service
public class SysDictItemService {

	@Autowired
	private SysDictService sysDictService;

	@Autowired
	private SysDictItemMapper sysDictItemMapper;

	@Autowired
	private SysDictItemMapStruct sysDictItemMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneById(Long id) {
		return sysDictItemMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysDictItem findOneByDictId(Long dictId) {
		return sysDictItemMapper.selectOneByDictId(new SysDictItemDictIdMapperBO(dictId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictId(Long dictId) {
		return sysDictItemMapper.selectByDictId(new SysDictItemDictIdMapperBO(dictId));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictIdList(List<Long> dictIdList) {
		return sysDictItemMapper.selectByDictIdList(new SysDictItemDictIdMapperBO(dictIdList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictCode(String dictCode) {
		return sysDictItemMapper.selectByDictCode(new SysDictItemDictCodeMapperBO(dictCode));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByItemCode(String itemCode) {
		return sysDictItemMapper.selectByItemCode(new SysDictItemItemCodeMapperBO(itemCode));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByDictCodeList(List<String> dictCodeList) {
		return sysDictItemMapper.selectByDictCodeList(new SysDictItemDictCodeMapperBO(dictCodeList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByItemCodeList(List<String> itemCodeList) {
		return sysDictItemMapper.selectByItemCodeList(new SysDictItemItemCodeMapperBO(itemCodeList));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByIdList(List<Long> idList) {
		return sysDictItemMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysDictItem> findListByServiceBO(SysDictItemPageQueryServiceBO serviceBO) {
		return sysDictItemMapper.selectByPageQueryMapperBo(sysDictItemMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysDictItemPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysDictItemMapper.selectByPageQueryMapperBo(sysDictItemMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
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
	public Integer create(SysDictItemCreateServiceBO serviceBO) {
		return sysDictItemMapper.insert(initCreateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysDictItemUpdateServiceBO serviceBO) {
		return sysDictItemMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysDictItemMapper.updateStateEnumByIdList(sysDictItemMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		return sysDictItemMapper.updateDeleteEnumByIdList(sysDictItemMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDeleteByDictIdList(List<Long> dictIdList) {
		return sysDictItemMapper.updateDeleteEnumByDictIdList(new SysDictItemDictIdMapperBO(dictIdList));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysDictItem initCreateBasicParam(SysDictItemCreateServiceBO serviceBO) {
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

		SysDict sysDict = sysDictService.findOneById(serviceBO.getDictId());
		if (null == sysDict) {
			throw new BusinessException("字典项不存在");
		}
		serviceBO.setDictCode(sysDict.getDictCode());
		return sysDictItemMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysDictItem initUpdateBasicParam(SysDictItemUpdateServiceBO serviceBO) {
		return sysDictItemMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

