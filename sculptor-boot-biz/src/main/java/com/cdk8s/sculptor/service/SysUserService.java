/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.*;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysUserMapper;
import com.cdk8s.sculptor.mapper.ext.SysUserMapperExt;
import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserUsernameMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserUserIdListToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUserIdListToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeUserIdListToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.*;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.bases.SysUserServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DefaultDataUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
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
@CacheConfig(cacheNames = "SysUserService")
@Service
public class SysUserService extends SysUserServiceBase {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserMapperExt sysUserMapperExt;

	@Autowired
	private SysUserMapStruct sysUserMapStruct;

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelDeptUserService relDeptUserService;

	@Autowired
	private SysEmployeeService sysEmployeeService;

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserInfoService sysUserInfoService;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUser findOneByUsername(SysUserUsernameServiceBO serviceBO) {
		SysUserUsernameMapperBO mapperBO = sysUserMapStruct.usernameServiceBOToMapperBO(serviceBO);
		List<SysUser> sysUserList = sysUserMapper.selectByUsername(mapperBO);
		if (CollectionUtil.isNotEmpty(sysUserList)) {
			return sysUserList.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUser findOneByUsernameNotEnum(SysUserUsernameServiceBO serviceBO) {
		SysUserUsernameMapperBO mapperBO = sysUserMapStruct.usernameServiceBOToMapperBO(serviceBO);
		mapperBO.setDeleteEnum(null);
		mapperBO.setStateEnum(null);
		List<SysUser> sysUserList = sysUserMapper.selectByUsername(mapperBO);
		if (CollectionUtil.isNotEmpty(sysUserList)) {
			return sysUserList.get(0);
		}
		return null;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String findUsernameById(IdServiceBO serviceBO) {
		SysUser result = findOneById(serviceBO);
		if (null != result) {
			return result.getUsername();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String findRealNameById(IdServiceBO serviceBO) {
		SysUser result = findOneById(serviceBO);
		if (null != result) {
			return result.getRealName();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBOToDept(SysUserPageQueryServiceBO serviceBO) {
		SysUserPageQueryMapperBO mapperBO = sysUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO);
		Long deptId = mapperBO.getDeptId();
		if (null != deptId) {
			// 查询出其部门下的所有子部门
			ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO();
			parentIdServiceBO.setParentId(deptId);
			parentIdServiceBO.setTenantId(serviceBO.getTenantId());
			parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			List<SysDept> recursionListByParentId = sysDeptService.findRecursionListByParentId(parentIdServiceBO);
			if (CollectionUtil.isNotEmpty(recursionListByParentId)) {

				List<Long> deptIdList = recursionListByParentId.stream()
						.map(SysDept::getId)
						.collect(Collectors.toList());

				// 必须把这个设置为 null，不然就无法查出其子节点数据
				mapperBO.setDeptId(null);
				mapperBO.setDeptIdList(deptIdList);
			}
		}
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysUserMapperExt.selectByPageQueryMapperBoToDept(mapperBO);
		});
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBOToRole(SysUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysUserMapperExt.selectByPageQueryMapperBoToRole(sysUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void resetPassword(IdListServiceBO serviceBO) {
		List<SysUser> result = findListByIdList(serviceBO);
		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("用户不存在");
		}

		for (SysUser entity : result) {
			SysUserUpdateServiceBO sysUserUpdateServiceBO = new SysUserUpdateServiceBO();
			sysUserUpdateServiceBO.setId(entity.getId());

			String passwordSalt = RandomUtil.randomAlphanumeric(10);
			sysUserUpdateServiceBO.setPasswordSalt(passwordSalt);

			String passwordByMD5 = Md5Util.md5ByGuava(GlobalConstant.DEFAULT_USER_PASSWORD + passwordSalt);
			sysUserUpdateServiceBO.setUserPassword(passwordByMD5);
			update(sysUserUpdateServiceBO);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void updatePassword(IdListServiceBO idListServiceBO, String newPassword) {
		List<SysUser> result = findListByIdList(idListServiceBO);
		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("用户不存在");
		}

		for (SysUser entity : result) {
			SysUserUpdateServiceBO serviceBO = new SysUserUpdateServiceBO();
			serviceBO.setId(entity.getId());

			String passwordSalt = RandomUtil.randomAlphanumeric(10);
			serviceBO.setPasswordSalt(passwordSalt);

			String passwordByMD5 = Md5Util.md5ByGuava(newPassword + passwordSalt);
			serviceBO.setUserPassword(passwordByMD5);
			update(serviceBO);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer createByWeixinLogin(SysUserCreateServiceBO serviceBO, SysUserInfoCreateServiceBO sysUserInfoCreateServiceBO) {
		Integer result = create(serviceBO);
		if (result == 0) {
			throw new BusinessException("创建用户失败");
		}

		Integer integer = sysUserInfoService.create(sysUserInfoCreateServiceBO);
		if (integer == 0) {
			throw new BusinessException("创建用户信息失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysUserCreateServiceBO serviceBO) {
		if (StringUtil.isNotBlank(serviceBO.getMobilePhone())) {
			SysUserMobilePhoneServiceBO sysUserMobilePhoneServiceBO = new SysUserMobilePhoneServiceBO();
			sysUserMobilePhoneServiceBO.setMobilePhone(serviceBO.getMobilePhone());
			sysUserMobilePhoneServiceBO.setTenantId(serviceBO.getTenantId());
			sysUserMobilePhoneServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			List<SysUser> listByMobilePhone = findListByMobilePhone(sysUserMobilePhoneServiceBO);
			if (CollectionUtil.isNotEmpty(listByMobilePhone)) {
				throw new BusinessException("该手机号已经存在，请重新输入");
			}
		}

		SysUserUsernameServiceBO sysUserUsernameServiceBO = new SysUserUsernameServiceBO();
		sysUserUsernameServiceBO.setUsername(serviceBO.getUsername());
		sysUserUsernameServiceBO.setTenantId(serviceBO.getTenantId());
		sysUserUsernameServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysUser> listByUsername = findListByUsername(sysUserUsernameServiceBO);
		if (CollectionUtil.isNotEmpty(listByUsername)) {
			throw new BusinessException("该登录名已存在，无法创建");
		}

		Long userId = GenerateIdUtil.getId();
		if (null != serviceBO.getId()) {
			userId = serviceBO.getId();
		}

		serviceBO.setId(userId);
		int result = sysUserMapper.insert(initCreateBasicParam(serviceBO));

		if (result == 0) {
			throw new BusinessException("创建用户失败");
		}

		// 同步绑定默认角色
		Long roleId = GlobalConstant.CREATE_USER_DEFAULT_ROLE_ID;
		if (null != serviceBO.getRoleId()) {
			roleId = serviceBO.getRoleId();
		}
		RelRoleUserCreateServiceBO relRoleUserCreateServiceBO = new RelRoleUserCreateServiceBO();
		relRoleUserCreateServiceBO.setRoleId(roleId);
		relRoleUserCreateServiceBO.setUserId(userId);
		relRoleUserCreateServiceBO.setCreateDate(serviceBO.getCreateDate());
		relRoleUserCreateServiceBO.setCreateUserId(serviceBO.getCreateUserId());
		relRoleUserCreateServiceBO.setUpdateDate(serviceBO.getCreateDate());
		relRoleUserCreateServiceBO.setUpdateUserId(serviceBO.getCreateUserId());
		relRoleUserService.create(relRoleUserCreateServiceBO);

		// 同步绑定默认部门
		Long deptId = GlobalConstant.CREATE_USER_DEFAULT_DEPT_ID;
		if (null != serviceBO.getDeptId()) {
			deptId = serviceBO.getDeptId();
		}
		RelDeptUserCreateServiceBO relDeptUserCreateServiceBO = new RelDeptUserCreateServiceBO();
		relDeptUserCreateServiceBO.setDeptId(deptId);
		relDeptUserCreateServiceBO.setUserId(userId);
		relDeptUserCreateServiceBO.setCreateDate(serviceBO.getCreateDate());
		relDeptUserCreateServiceBO.setCreateUserId(serviceBO.getCreateUserId());
		relDeptUserCreateServiceBO.setUpdateDate(serviceBO.getCreateDate());
		relDeptUserCreateServiceBO.setUpdateUserId(serviceBO.getCreateUserId());
		relDeptUserService.create(relDeptUserCreateServiceBO);

		// 同步创建员工信息
		SysEmployeeCreateServiceBO sysEmployeeCreateServiceBO = new SysEmployeeCreateServiceBO();
		sysEmployeeCreateServiceBO.setUserId(userId);
		sysEmployeeCreateServiceBO.setWorkCardId(String.valueOf(userId));
		sysEmployeeCreateServiceBO.setCreateDate(serviceBO.getCreateDate());
		sysEmployeeCreateServiceBO.setCreateUserId(serviceBO.getCreateUserId());
		sysEmployeeCreateServiceBO.setUpdateDate(serviceBO.getCreateDate());
		sysEmployeeCreateServiceBO.setUpdateUserId(serviceBO.getCreateUserId());
		sysEmployeeCreateServiceBO.setJobPosition("默认值");
		sysEmployeeService.create(sysEmployeeCreateServiceBO);


		return result;
	}


	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> idList = serviceBO.getIdList();

		if (idList.contains(GlobalConstant.TOP_ADMIN_USER_ID)) {
			throw new BusinessException("超级管理员是无法被删除");
		}

		if (idList.contains(serviceBO.getDeleteUserId())) {
			throw new BusinessException("自己无法删除自己");
		}

		List<Long> intersection = CollectionUtil.intersection(DefaultDataUtil.getDefaultUserIdList(), idList);
		if (CollectionUtil.isNotEmpty(intersection)) {
			throw new BusinessException("预设的数据无法删除，建议禁用其状态");
		}

		int result = sysUserMapper.updateDeleteEnumByIdList(sysUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
		if (result == 0) {
			throw new BusinessException("删除用户失败");
		}

		// 同步删除用户角色中间表
		RelRoleUserUserIdListToDeleteServiceBO relRoleUserUserIdListToDeleteServiceBO = new RelRoleUserUserIdListToDeleteServiceBO();
		relRoleUserUserIdListToDeleteServiceBO.setUserIdList(idList);
		relRoleUserUserIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		relRoleUserUserIdListToDeleteServiceBO.setDeleteDate(serviceBO.getDeleteDate());
		relRoleUserUserIdListToDeleteServiceBO.setDeleteUserId(serviceBO.getDeleteUserId());
		relRoleUserService.batchDeleteByUserIdList(relRoleUserUserIdListToDeleteServiceBO);

		// 同步删除用户部门中间表
		RelDeptUserUserIdListToDeleteServiceBO relDeptUserUserIdListToDeleteServiceBO = new RelDeptUserUserIdListToDeleteServiceBO();
		relDeptUserUserIdListToDeleteServiceBO.setUserIdList(idList);
		relDeptUserUserIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		relDeptUserUserIdListToDeleteServiceBO.setDeleteDate(serviceBO.getDeleteDate());
		relDeptUserUserIdListToDeleteServiceBO.setDeleteUserId(serviceBO.getDeleteUserId());
		relDeptUserService.batchDeleteByUserIdList(relDeptUserUserIdListToDeleteServiceBO);

		// 同步删除员工信息表
		SysEmployeeUserIdListToDeleteServiceBO sysEmployeeUserIdListToDeleteServiceBO = new SysEmployeeUserIdListToDeleteServiceBO();
		sysEmployeeUserIdListToDeleteServiceBO.setUserIdList(idList);
		sysEmployeeUserIdListToDeleteServiceBO.setTenantId(serviceBO.getTenantId());
		sysEmployeeUserIdListToDeleteServiceBO.setDeleteDate(serviceBO.getDeleteDate());
		sysEmployeeUserIdListToDeleteServiceBO.setDeleteUserId(serviceBO.getDeleteUserId());
		sysEmployeeService.batchDeleteByUserIdList(sysEmployeeUserIdListToDeleteServiceBO);

		return result;
	}

	public String md5UserPassword(String inputPassword, String passwordSalt) {
		if (StringUtil.isBlank(passwordSalt)) {
			passwordSalt = RandomUtil.randomAlphanumeric(10);
		}
		// 数据库密码 = MD5(输入密码 + 随机盐)
		return Md5Util.md5ByGuava(inputPassword + passwordSalt);
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	private SysUser initCreateBasicParam(SysUserCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		String passwordSalt = RandomUtil.randomAlphanumeric(10);
		serviceBO.setPasswordSalt(passwordSalt);

		serviceBO.setUserPassword(md5UserPassword(serviceBO.getUserPassword(), passwordSalt));

		// 实际情况要根据业务情况分析注册来源
		if (null == serviceBO.getGenderEnum()) {
			serviceBO.setGenderEnum(GenderEnum.PRIVACY.getCode());
		}
		if (null == serviceBO.getTenantId()) {
			serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		}
		if (null == serviceBO.getDeleteEnum()) {
			serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}
		serviceBO.setRegisterTypeEnum(RegisterTypeEnum.MANAGEMENT_ADD.getCode());
		serviceBO.setRegisterOriginEnum(RegisterOriginEnum.WEB.getCode());

		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}

		return sysUserMapStruct.createServiceBOToEntity(serviceBO);
	}


	// =====================================私有方法 end=====================================

}

