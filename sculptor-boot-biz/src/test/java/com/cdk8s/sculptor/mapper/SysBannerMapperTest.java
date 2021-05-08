/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerMapperTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.BannerJumpTypeEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbanner.SysBannerPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysBanner;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
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
public class SysBannerMapperTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysBannerMapper sysBannerMapper;

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
	public void a_selectById() {
		SysBanner result = sysBannerMapper.selectById(new IdMapperBO(ID));

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_selectByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		List<SysBanner> result = sysBannerMapper.selectByIdList(new IdListMapperBO(idList));

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectAll() {
		List<SysBanner> result = sysBannerMapper.selectAll();

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByPageQueryMapperBo() {
		SysBannerPageQueryMapperBO mapperBO = new SysBannerPageQueryMapperBO();
		mapperBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		mapperBO.setCreateDateStartDate(631183520000L);
		mapperBO.setCreateDateEndDate(1893487520000L);

		List<SysBanner> result = sysBannerMapper.selectByPageQueryMapperBo(mapperBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}


	@Test
	public void a_selectByDeleteEnum() {
		BaseQueryMapperBO bo = new BaseQueryMapperBO();
		bo.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysBanner> result = sysBannerMapper.selectByDeleteEnum(bo);

		assertThat(result.size(), Matchers.greaterThan(0));
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_batchCreate() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		List<SysBanner> entityList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			SysBanner entity = new SysBanner();
			// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
			entity.setId(ID + i);
			entity.setBannerTitle(RandomUtil.randomAlphabetic(6));
			entity.setBannerStandDate(RandomUtil.nextBigDecimal(1, 100));
			entity.setBannerJumpTypeEnum(BannerJumpTypeEnum.getRandomEnum().getCode());
			entity.setRanking(100);
			entity.setTenantId(RandomUtil.nextLong(1, 10));

			entity.setBannerDescription(RandomUtil.randomAlphabetic(6));
			entity.setBannerCode(RandomUtil.randomAlphabetic(6));
			entity.setCoverPcImageUrl(RandomUtil.randomAlphabetic(6));
			entity.setCoverH5ImageUrl(RandomUtil.randomAlphabetic(6));
			entity.setJumpH5Url(RandomUtil.randomAlphabetic(6));
			entity.setJumpPcUrl(RandomUtil.randomAlphabetic(6));
			entity.setJumpTypeCode(RandomUtil.randomAlphabetic(6));
			entity.setJumpObjectId(RandomUtil.nextLong(1, 10));


			entity.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			entity.setCreateDate(currentEpochMilli);
			entity.setCreateUserId(currentUserId);
			entity.setUpdateDate(currentEpochMilli);
			entity.setUpdateUserId(currentUserId);
			entityList.add(entity);
		}

		int result = sysBannerMapper.batchInsertList(entityList);

		assertThat(result, Matchers.greaterThan(0));
	}


	@Transactional
	@Test
	public void m_updateDeleteEnumByIdList() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		BatchDeleteMapperBO bo = new BatchDeleteMapperBO();
		bo.setIdList(idList);
		bo.setDeleteEnum(DeleteEnum.DELETED.getCode());
		bo.setUpdateDate(currentEpochMilli);
		bo.setUpdateUserId(UserInfoContext.getCurrentUserId());
		bo.setDeleteDate(currentEpochMilli);
		bo.setDeleteUserId(UserInfoContext.getCurrentUserId());

		int result = sysBannerMapper.updateDeleteEnumByIdList(bo);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================


}
