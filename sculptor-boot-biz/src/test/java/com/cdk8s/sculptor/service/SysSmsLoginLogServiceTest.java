/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogServiceTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.SmsProviderTypeEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
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
public class SysSmsLoginLogServiceTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysSmsLoginLogService sysSmsLoginLogService;

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

		SysSmsLoginLog result = sysSmsLoginLogService.findOneById(serviceBO);

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_findListByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		IdListServiceBO serviceBO = new IdListServiceBO();
		serviceBO.setIdList(idList);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysSmsLoginLog> result = sysSmsLoginLogService.findListByIdList(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findListByServiceBO() {
		SysSmsLoginLogPageQueryServiceBO serviceBO = new SysSmsLoginLogPageQueryServiceBO();
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		List<SysSmsLoginLog> result = sysSmsLoginLogService.findListByServiceBO(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findPageByServiceBO() {
		SysSmsLoginLogPageQueryServiceBO serviceBO = new SysSmsLoginLogPageQueryServiceBO();
		serviceBO.setPageNum(1);
		serviceBO.setPageSize(10);
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		PageInfo result = sysSmsLoginLogService.findPageByServiceBO(serviceBO);

		assertThat(result.getList().size(), Matchers.greaterThan(0));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_create() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysSmsLoginLogCreateServiceBO serviceBO = new SysSmsLoginLogCreateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setUserId(RandomUtil.nextLong(1, 10));
		serviceBO.setUserMobilePhone(RandomUtil.randomAlphabetic(6));
		serviceBO.setVerificationCode(RandomUtil.randomAlphabetic(6));
		serviceBO.setSmsProviderTypeEnum(SmsProviderTypeEnum.getRandomEnum().getCode());
		serviceBO.setBoolServiceStateEnum(BooleanEnum.getRandomEnum().getCode());
		serviceBO.setMessageContent(RandomUtil.randomAlphabetic(6));
		serviceBO.setBoolUseEnum(BooleanEnum.getRandomEnum().getCode());

		serviceBO.setCreateDate(currentEpochMilli);
		serviceBO.setCreateUserId(currentUserId);

		Integer result = sysSmsLoginLogService.create(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_update() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysSmsLoginLogUpdateServiceBO serviceBO = new SysSmsLoginLogUpdateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setUserId(RandomUtil.nextLong(1, 10));
		serviceBO.setUserMobilePhone(RandomUtil.randomAlphabetic(6));
		serviceBO.setVerificationCode(RandomUtil.randomAlphabetic(6));
		serviceBO.setSmsProviderTypeEnum(SmsProviderTypeEnum.getRandomEnum().getCode());
		serviceBO.setBoolServiceStateEnum(BooleanEnum.getRandomEnum().getCode());
		serviceBO.setMessageContent(RandomUtil.randomAlphabetic(6));
		serviceBO.setBoolUseEnum(BooleanEnum.getRandomEnum().getCode());
		serviceBO.setTenantId(RandomUtil.nextLong(1, 10));


		Integer result = sysSmsLoginLogService.update(serviceBO);

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


		Integer result = sysSmsLoginLogService.batchDelete(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
