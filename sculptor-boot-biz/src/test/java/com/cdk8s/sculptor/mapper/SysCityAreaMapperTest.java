/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaMapperTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syscityarea.SysCityAreaPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.util.CityGithubEntity;
import com.cdk8s.sculptor.pojo.entity.SysCityArea;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class SysCityAreaMapperTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysCityAreaMapper sysCityAreaMapper;

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
		SysCityArea result = sysCityAreaMapper.selectById(new IdMapperBO(ID));

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_selectByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		List<SysCityArea> result = sysCityAreaMapper.selectByIdList(new IdListMapperBO(idList));

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectAll() {
		List<SysCityArea> result = sysCityAreaMapper.selectAll();

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByPageQueryMapperBo() {
		SysCityAreaPageQueryMapperBO mapperBO = new SysCityAreaPageQueryMapperBO();
		mapperBO.setCreateDateStartDate(631183520000L);
		mapperBO.setCreateDateEndDate(1893487520000L);

		List<SysCityArea> result = sysCityAreaMapper.selectByPageQueryMapperBo(mapperBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 省市区组件数据格式转换
	 * https://github.com/modood/Administrative-divisions-of-China
	 */
	@Ignore
	@SneakyThrows
	@Test
	public void a_batchCreateByGithubData() {
		// String readFileToString = FileUtil.readFileToString("/Users/youmeek/Downloads/pcas-code.json");
		//
		// List<CityGithubEntity> cityGithubEntityList = JSON.parseObject(readFileToString, new TypeReference<List<CityGithubEntity>>() {
		// });
		//
		// List<SysCityArea> entityList = new ArrayList<>();
		// buildChildren(entityList, cityGithubEntityList, 1L, "1");
		//
		// // 分割 list 成多个批次，然后插入
		// List<List<SysCityArea>> partitionList = CollectionUtil.partitionList(entityList, 1000);
		// for (List<SysCityArea> list : partitionList) {
		// 	TimeUnit.SECONDS.sleep(1);
		// 	sysCityAreaMapper.batchInsertList(list);
		// }

	}

	private void buildChildren(List<SysCityArea> entityList, List<CityGithubEntity> childrenList, Long parentId, String parentIds) {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		if (CollectionUtil.isNotEmpty(childrenList)) {
			for (CityGithubEntity childrenBean : childrenList) {
				SysCityArea entityChildren = new SysCityArea();
				if (parentId.equals(1L)) {
					parentIds = "1";
				} else {
					StringBuilder stringBuilder = new StringBuilder();
					List<String> split = StringUtil.split(parentIds, ",");
					for (String temp : split) {
						stringBuilder.append(temp);
						stringBuilder.append(",");
						if (temp.equalsIgnoreCase(String.valueOf(parentId))) {
							break;
						}
					}
					parentIds = StringUtil.removeEnd(stringBuilder.toString(), ",");
				}
				Long nodeId = Long.valueOf(childrenBean.getCode());
				entityChildren.setId(nodeId);
				entityChildren.setTenantId(GlobalConstant.TOP_TENANT_ID);
				entityChildren.setParentId(parentId);
				entityChildren.setParentIds(parentIds);
				entityChildren.setAreaName(childrenBean.getName());
				entityChildren.setRanking(50);
				entityChildren.setCreateDate(currentEpochMilli);
				entityChildren.setCreateUserId(currentUserId);
				entityList.add(entityChildren);

				List<CityGithubEntity> childrenListObject = childrenBean.getChildren();
				if (CollectionUtil.isNotEmpty(childrenListObject)) {
					// 有子节点则要不断拼接父级ID
					parentIds = parentIds + "," + nodeId;
					buildChildren(entityList, childrenListObject, nodeId, parentIds);
				}
			}
		}

	}


	@Test
	public void a_batchCreate() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		List<SysCityArea> entityList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			SysCityArea entity = new SysCityArea();
			// cdk8stodo 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
			entity.setId(ID + i);
			entity.setParentId(1L);
			entity.setParentIds("1");
			entity.setAreaName(RandomUtil.randomAlphabetic(6));
			entity.setRanking(100);
			entity.setTenantId(RandomUtil.nextLong(1, 10));


			entity.setCreateDate(currentEpochMilli);
			entity.setCreateUserId(currentUserId);
			entityList.add(entity);
		}

		int result = sysCityAreaMapper.batchInsertList(entityList);

		assertThat(result, Matchers.greaterThan(0));
	}


	@Transactional
	@Test
	public void m_deleteByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		BatchDeleteMapperBO bo = new BatchDeleteMapperBO();
		bo.setIdList(idList);

		int result = sysCityAreaMapper.deleteByIdList(bo);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================


}
