/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaServiceTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.pojo.bo.service.bases.*;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysCityArea;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.response.biz.R;
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
public class SysCityAreaServiceTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysCityAreaService sysCityAreaService;

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

		SysCityArea result = sysCityAreaService.findOneById(serviceBO);

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_findListByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		IdListServiceBO serviceBO = new IdListServiceBO();
		serviceBO.setIdList(idList);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysCityArea> result = sysCityAreaService.findListByIdList(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findListByServiceBO() {
		SysCityAreaPageQueryServiceBO serviceBO = new SysCityAreaPageQueryServiceBO();
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		List<SysCityArea> result = sysCityAreaService.findListByServiceBO(serviceBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_findPageByServiceBO() {
		SysCityAreaPageQueryServiceBO serviceBO = new SysCityAreaPageQueryServiceBO();
		serviceBO.setPageNum(1);
		serviceBO.setPageSize(10);
		serviceBO.setCreateDateStartDate(631183520000L);
		serviceBO.setCreateDateEndDate(1893487520000L);

		PageInfo result = sysCityAreaService.findPageByServiceBO(serviceBO);

		assertThat(result.getList().size(), Matchers.greaterThan(0));
	}

	/**
	 * 显示指定父 ID 的子集，只递归指定的子集（树结构），一般用在级联组件回显，以及商品添加时候菜单选择功能
	 * ShowLevel 是关键，如果是 2 级，则及时你传入的父ID 是三级的 ID 也是只会展示 2 级。所以一般该值是跟传入的父类个数成正相关，如果父ID是传入3个，一般 ShowLevel 就是 3。
	 * 1 是全国，所以查询全国的子集（不递归）
	 * 13 是河北省，所以查询河北所有子集（不递归）
	 * 1301 是石家庄市，所以查询石家庄所有子集（不递归）
	 *
	 * @param serviceBO parentIdList 级联的父 ID，比如传：1,13,1301
	 * @param serviceBO showLevel 显示层级，比如传 2，即使数据是支持 4 级数据，也是只展示 2 级数据出来
	 * @return
	 */
	@Test
	public void a_findListByTreeCascade() {
		TreeCascadeServiceBO parentIdServiceBO = new TreeCascadeServiceBO();
		parentIdServiceBO.setParentIdList(Lists.newArrayList(1L, 13L, 1301L));
		parentIdServiceBO.setShowLevel(3);
		parentIdServiceBO.setTenantId(1L);
		parentIdServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysCityArea> result = sysCityAreaService.findListByTreeCascade(parentIdServiceBO);
		log.info("a_findListByTreeCascade返回格式:");
		log.info(JsonUtil.toJson(R.success(result)));
	}

	/**
	 * 所有子集记录（平级结构）
	 */
	@Test
	public void a_findListByParentId() {
		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO(1L);
		List<SysCityArea> result = sysCityAreaService.findListByParentId(parentIdServiceBO);
		log.info("a_findListByTreeCascade返回格式:");
		log.info(JsonUtil.toJson(R.success(result)));
	}

	/**
	 * 递归自己和所有子集记录（平级结构）（递归所有，数据量大有性能危险）
	 */
	@Test
	public void a_findRecursionListByParentId() {
		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO(4401L);
		List<SysCityArea> result = sysCityAreaService.findRecursionListByParentId(parentIdServiceBO);
		log.info("a_findRecursionListByParentId返回格式:");
		log.info(JsonUtil.toJson(R.success(result)));
	}

	/**
	 * 递归自己和所有子集记录（树结构）（递归所有，数据量大有性能危险）
	 */
	@Test
	public void a_findTreeListByParentId() {
		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO(4401L);
		List<SysCityArea> result = sysCityAreaService.findTreeListByParentId(parentIdServiceBO);
		log.info("a_findTreeListByParentId返回格式:");
		log.info(JsonUtil.toJson(R.success(result)));
	}

	/**
	 * 子集查询（不递归），并包含是否是父类字段
	 */
	@Test
	public void a_findListAndCheckParentEnumByParentId() {
		ParentIdServiceBO parentIdServiceBO = new ParentIdServiceBO(1L);
		List<SysCityArea> result = sysCityAreaService.findListAndCheckParentEnumByParentId(parentIdServiceBO);
		log.info("a_findListAndCheckParentEnumByParentId返回格式:");
		log.info(JsonUtil.toJson(R.success(result)));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_create() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysCityAreaCreateServiceBO serviceBO = new SysCityAreaCreateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setParentId(1L);
		serviceBO.setAreaName(RandomUtil.randomAlphabetic(6));
		serviceBO.setRanking(100);

		serviceBO.setCreateDate(currentEpochMilli);
		serviceBO.setCreateUserId(currentUserId);

		Integer result = sysCityAreaService.create(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_update() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		SysCityAreaUpdateServiceBO serviceBO = new SysCityAreaUpdateServiceBO();
		// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		serviceBO.setId(ID);
		serviceBO.setParentId(1L);
		serviceBO.setParentIds("1");
		serviceBO.setAreaName(RandomUtil.randomAlphabetic(6));
		serviceBO.setRanking(100);
		serviceBO.setTenantId(RandomUtil.nextLong(1, 10));


		Integer result = sysCityAreaService.update(serviceBO);

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


		Integer result = sysCityAreaService.batchDelete(serviceBO);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
