package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.LoginOriginEnum;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.UserInfoContext;
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
import java.util.List;

import static org.junit.Assert.assertThat;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SysLoginLogServiceTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysLoginLogService sysLoginLogService;

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
		oauthUserAttribute.setEmail(GlobalConstantToJunit.USER_EMAIL);
		oauthUserAttribute.setUserId(GlobalConstantToJunit.USER_ID);
		oauthUserAttribute.setUsername(GlobalConstantToJunit.USERNAME);
		oauthUserProfile.setUserAttribute(oauthUserAttribute);
		UserInfoContext.setCurrentUser(oauthUserProfile);
	}

	// =====================================预处理 end=====================================
	// =====================================查询业务 start=====================================

	@Test
	public void a_findOneById() {
		SysLoginLog result = sysLoginLogService.findOneById(ID);

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_findListByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		List<SysLoginLog> result = sysLoginLogService.findListByIdList(idList);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findListByServiceBO() {
		SysLoginLogPageQueryServiceBO serviceBO = new SysLoginLogPageQueryServiceBO();
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		List<SysLoginLog> result = sysLoginLogService.findListByServiceBO(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findPageByServiceBO() {
		SysLoginLogPageQueryServiceBO serviceBO = new SysLoginLogPageQueryServiceBO();
		serviceBO.setPageNum(1);
		serviceBO.setPageSize(10);
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		PageInfo result = sysLoginLogService.findPageByServiceBO(serviceBO);

		assertThat(result.getList().size(), Matchers.greaterThan(0));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_create() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysLoginLogCreateServiceBO serviceBO = new SysLoginLogCreateServiceBO();
		// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setUserId(ID);
		serviceBO.setUsername(RandomUtil.randomAlphabetic(6));
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNewUserEnum(BooleanEnum.YES.getCode());
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());


		Integer result = sysLoginLogService.create(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_update() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysLoginLogUpdateServiceBO serviceBO = new SysLoginLogUpdateServiceBO();
		// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setUserId(ID);
		serviceBO.setUsername(RandomUtil.randomAlphabetic(6));
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNewUserEnum(BooleanEnum.YES.getCode());
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());


		Integer result = sysLoginLogService.update(serviceBO);

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


		Integer result = sysLoginLogService.batchDelete(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
