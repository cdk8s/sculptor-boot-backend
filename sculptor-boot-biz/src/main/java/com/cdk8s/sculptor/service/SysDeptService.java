/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDeptService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysDeptMapper;
import com.cdk8s.sculptor.mapper.ext.SysDeptMapperExt;
import com.cdk8s.sculptor.mapstruct.SysDeptMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserDeptIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.SysDeptCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.SysDeptDeptCodeServiceBO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import com.cdk8s.sculptor.service.bases.SysDeptServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DefaultDataUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysDeptService")
@Service
public class SysDeptService extends SysDeptServiceBase {

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private SysDeptMapperExt sysDeptMapperExt;

	@Autowired
	private SysDeptMapStruct sysDeptMapStruct;

	@Autowired
	private RelDeptUserService relDeptUserService;

	// =====================================查询业务 start=====================================

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysDeptCreateServiceBO serviceBO) {
		SysDept entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		return sysDeptMapper.insert(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> idList = serviceBO.getIdList();

		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultDeptIdList(), idList);
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除，建议禁用其状态");
		}

		// 如果下面有子节点不允许删除
		ParentIdListServiceBO parentIdListServiceBO = new ParentIdListServiceBO();
		parentIdListServiceBO.setParentIdList(idList);
		parentIdListServiceBO.setTenantId(serviceBO.getTenantId());
		parentIdListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysDept> listByParentIdList = findListByParentIdList(parentIdListServiceBO);
		if (CollectionUtil.isNotEmpty(listByParentIdList)) {
			throw new BusinessException("部门下还有子部门，请先删除子部门");
		}

		// 如果该部门下面有人绑定不允许被删除
		RelDeptUserDeptIdListServiceBO relDeptUserDeptIdListServiceBO = new RelDeptUserDeptIdListServiceBO();
		relDeptUserDeptIdListServiceBO.setDeptIdList(idList);
		relDeptUserDeptIdListServiceBO.setTenantId(serviceBO.getTenantId());
		List<RelDeptUser> listByDeptIdList = relDeptUserService.findListByDeptIdList(relDeptUserDeptIdListServiceBO);
		if (CollectionUtil.isNotEmpty(listByDeptIdList)) {
			throw new BusinessException("部门下还有用户，请先解绑关联关系");
		}

		return sysDeptMapper.updateDeleteEnumByIdList(sysDeptMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	private SysDept initCreateBasicParam(SysDeptCreateServiceBO serviceBO) {
		SysDeptDeptCodeServiceBO sysDeptDeptCodeServiceBO = new SysDeptDeptCodeServiceBO();
		sysDeptDeptCodeServiceBO.setDeptCode(serviceBO.getDeptCode());
		sysDeptDeptCodeServiceBO.setTenantId(serviceBO.getTenantId());
		sysDeptDeptCodeServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysDept> listByDeptCode = findListByDeptCode(sysDeptDeptCodeServiceBO);
		if (CollectionUtil.isNotEmpty(listByDeptCode)) {
			throw new BusinessException("编码已经存在");
		}


		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}


		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
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

			IdServiceBO idServiceBO = new IdServiceBO();
			idServiceBO.setId(parentId);
			idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

			SysDept parentObject = findOneById(idServiceBO);
			if (null == parentObject) {
				throw new BusinessException("该父节点不存在");
			}
			serviceBO.setParentIds(parentObject.getParentIds() + "," + parentId);
		}

		return sysDeptMapStruct.createServiceBOToEntity(serviceBO);
	}


	// =====================================私有方法 end=====================================

}

