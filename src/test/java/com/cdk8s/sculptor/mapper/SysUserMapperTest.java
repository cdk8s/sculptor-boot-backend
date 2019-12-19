package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.*;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.UserInfoContext;
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
import java.util.List;

import static org.junit.Assert.assertThat;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SysUserMapperTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysUserMapper sysUserMapper;

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
	public void a_selectById() {
		SysUser result = sysUserMapper.selectById(new IdMapperBO(ID));

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_selectByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		List<SysUser> result = sysUserMapper.selectByIdList(new IdListMapperBO(idList));

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectAll() {
		List<SysUser> result = sysUserMapper.selectAll();

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByPageQueryMapperBo() {
		SysUserPageQueryMapperBO mapperBO = new SysUserPageQueryMapperBO();
		mapperBO.setStateEnum(StateEnum.ENABLE.getCode());
		mapperBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		mapperBO.setCreateDateStartDate(631183520000L);
		mapperBO.setCreateDateEndDate(1893487520000L);

		List<SysUser> result = sysUserMapper.selectByPageQueryMapperBo(mapperBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByStateEnum() {
		BaseQueryMapperBO bo = new BaseQueryMapperBO();
		bo.setStateEnum(StateEnum.ENABLE.getCode());
		List<SysUser> result = sysUserMapper.selectByStateEnum(bo);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByDeleteEnum() {
		BaseQueryMapperBO bo = new BaseQueryMapperBO();
		bo.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysUser> result = sysUserMapper.selectByDeleteEnum(bo);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByDeleteEnumAndStateEnum() {
		BaseQueryMapperBO bo = new BaseQueryMapperBO();
		bo.setStateEnum(StateEnum.ENABLE.getCode());
		bo.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());

		List<SysUser> result = sysUserMapper.selectByDeleteEnumAndStateEnum(bo);

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_batchCreate() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		List<SysUser> entityList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			SysUser entity = new SysUser();
			// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
			entity.setId(ID + i);
			entity.setUsername("my_test_" + RandomUtil.randomAlphabetic(6));
			entity.setUserPassword("42c224b3c8899047460f5a6d1c041411");
			entity.setPasswordSalt("123456");
			entity.setGenderEnum(GenderEnum.MALE.getCode());
			entity.setRegisterTypeEnum(RegisterTypeEnum.REGISTER.getCode());
			entity.setRegisterOriginEnum(RegisterOriginEnum.WEB.getCode());

			entity.setRealName(RandomUtil.randomAlphabetic(6));
			entity.setUserEmail("1380000000" + i + "@qq.com");
			entity.setTelephone("1380000000" + i);
			entity.setMobilePhone("1380000000" + i);

			entity.setStateEnum(StateEnum.ENABLE.getCode());
			entity.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			entity.setCreateDate(currentEpochMilli);
			entity.setCreateUserId(currentUserId);
			entity.setUpdateDate(currentEpochMilli);
			entity.setUpdateUserId(currentUserId);
			entityList.add(entity);
		}

		int result = sysUserMapper.batchInsertList(entityList);

		assertThat(result, Matchers.greaterThan(0));
	}

	@Transactional
	@Test
	public void b_updateStateEnumByIdList() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		BatchUpdateStateMapperBO bo = new BatchUpdateStateMapperBO();
		bo.setIdList(idList);
		bo.setStateEnum(StateEnum.DISABLE.getCode());
		bo.setUpdateDate(currentEpochMilli);
		bo.setUpdateUserId(UserInfoContext.getCurrentUserId());

		int result = sysUserMapper.updateStateEnumByIdList(bo);

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

		int result = sysUserMapper.updateDeleteEnumByIdList(bo);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================


}
