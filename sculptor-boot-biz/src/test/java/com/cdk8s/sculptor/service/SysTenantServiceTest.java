/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysTenantServiceTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.systenant.SysTenantCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.systenant.SysTenantPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.systenant.SysTenantUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysTenant;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SysTenantServiceTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysTenantService sysTenantService;

	// =====================================预处理 start=====================================

	@SneakyThrows
	@BeforeClass
	public static void a_beforeClass() {
		OauthUserProfile oauthUserProfile = new OauthUserProfile();
		oauthUserProfile.setUsername(GlobalConstantToJunit.USERNAME);
		oauthUserProfile.setName(GlobalConstantToJunit.USERNAME);
		oauthUserProfile.setId(GlobalConstantToJunit.USER_ID);
		oauthUserProfile.setUserId(GlobalConstantToJunit.USER_ID);

		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		Map<String, Object> userAttributeExt = new HashMap<>();
		userAttributeExt.put(GlobalConstant.USER_TYPE_ENUM_MAP_KEY, UserTypeEnum.ADMIN.getCode());
		userAttributeExt.put(GlobalConstant.TENANT_ID_MAP_KEY, GlobalConstant.TOP_TENANT_ID);
		oauthUserAttribute.setUserAttributeExt(userAttributeExt);

		oauthUserAttribute.setRealName(GlobalConstantToJunit.USERNAME);
		oauthUserAttribute.setEmail(GlobalConstantToJunit.USER_EMAIL);
		oauthUserAttribute.setUserId(GlobalConstantToJunit.USER_ID);
		oauthUserAttribute.setUsername(GlobalConstantToJunit.USERNAME);
		oauthUserAttribute.setTenantId(GlobalConstantToJunit.TOP_TENANT_ID);
		oauthUserAttribute.setUserType(String.valueOf(UserTypeEnum.ADMIN.getCode()));

		oauthUserProfile.setUserAttribute(oauthUserAttribute);
		UserInfoContext.setCurrentUser(oauthUserProfile);
	}

	// =====================================预处理 end=====================================
	// =====================================查询业务 start=====================================

	@Test
	public void a_findOneById() {
		IdServiceBO serviceBO = new IdServiceBO();
		serviceBO.setId(ID);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		SysTenant result = sysTenantService.findOneById(serviceBO);

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_findListByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		IdListServiceBO serviceBO = new IdListServiceBO();
		serviceBO.setIdList(idList);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysTenant> result = sysTenantService.findListByIdList(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findListByServiceBO() {
		SysTenantPageQueryServiceBO serviceBO = new SysTenantPageQueryServiceBO();
		serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		List<SysTenant> result = sysTenantService.findListByServiceBO(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findPageByServiceBO() {
		SysTenantPageQueryServiceBO serviceBO = new SysTenantPageQueryServiceBO();
		serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		serviceBO.setPageNum(1);
		serviceBO.setPageSize(10);
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		PageInfo result = sysTenantService.findPageByServiceBO(serviceBO);

		assertThat(result.getList().size(), Matchers.greaterThan(0));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_create() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysTenantCreateServiceBO serviceBO = new SysTenantCreateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setTenantName(RandomUtil.randomAlphabetic(6));
		serviceBO.setTenantCode(RandomUtil.randomAlphabetic(6));
		serviceBO.setStateEnum(StateEnum.ENABLE.getCode());

		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		serviceBO.setCreateDate(currentEpochMilli);
		serviceBO.setCreateUserId(currentUserId);
		serviceBO.setUpdateDate(currentEpochMilli);
		serviceBO.setUpdateUserId(currentUserId);

		Integer result = sysTenantService.create(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_update() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysTenantUpdateServiceBO serviceBO = new SysTenantUpdateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setTenantName(RandomUtil.randomAlphabetic(6));
		serviceBO.setTenantCode(RandomUtil.randomAlphabetic(6));
		serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		serviceBO.setTenantId(RandomUtil.nextLong(1, 10));

		serviceBO.setUpdateDate(currentEpochMilli);
		serviceBO.setUpdateUserId(currentUserId);

		Integer result = sysTenantService.update(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_batchUpdateState() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		BatchUpdateStateServiceBO serviceBO = new BatchUpdateStateServiceBO();
		serviceBO.setIdList(idList);
		serviceBO.setStateEnum(StateEnum.DISABLE.getCode());

		serviceBO.setUpdateDate(currentEpochMilli);
		serviceBO.setUpdateUserId(currentUserId);

		Integer result = sysTenantService.batchUpdateState(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void m_batchDelete() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		BatchDeleteServiceBO serviceBO = new BatchDeleteServiceBO();
		serviceBO.setIdList(idList);

		serviceBO.setDeleteEnum(DeleteEnum.DELETED.getCode());
		serviceBO.setDeleteDate(currentEpochMilli);
		serviceBO.setDeleteUserId(currentUserId);

		serviceBO.setUpdateDate(currentEpochMilli);
		serviceBO.setUpdateUserId(currentUserId);


		Integer result = sysTenantService.batchDelete(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
