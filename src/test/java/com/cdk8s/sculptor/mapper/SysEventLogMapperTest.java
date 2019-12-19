package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.OperateTypeEnum;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
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
public class SysEventLogMapperTest {

	private static final Long ID = GenerateIdUtil.getId();

	@Autowired
	private SysEventLogMapper sysEventLogMapper;

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
		SysEventLog result = sysEventLogMapper.selectById(new IdMapperBO(ID));

		assertThat(result, Matchers.notNullValue());
	}

	@Test
	public void a_selectByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);
		List<SysEventLog> result = sysEventLogMapper.selectByIdList(new IdListMapperBO(idList));

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectAll() {
		List<SysEventLog> result = sysEventLogMapper.selectAll();

		assertThat(result.size(), Matchers.greaterThan(0));
	}

	@Test
	public void a_selectByPageQueryMapperBo() {
		SysEventLogPageQueryMapperBO mapperBO = new SysEventLogPageQueryMapperBO();
		mapperBO.setCreateDateStartDate(631183520000L);
		mapperBO.setCreateDateEndDate(1893487520000L);

		List<SysEventLog> result = sysEventLogMapper.selectByPageQueryMapperBo(mapperBO);

		assertThat(result.size(), Matchers.greaterThan(0));
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Test
	public void a_batchCreate() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		Long currentUserId = UserInfoContext.getCurrentUserId();

		List<SysEventLog> entityList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			SysEventLog entity = new SysEventLog();
			// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
			entity.setId(ID + i);
			entity.setBoolExecuteSuccessEnum(BooleanEnum.YES.getCode());
			entity.setOperateTypeEnum(OperateTypeEnum.CREATE.getCode());

			entity.setUserId(ID);
			entity.setUsername(RandomUtil.randomAlphabetic(6));
			entity.setMessage(RandomUtil.randomAlphabetic(6));
			entity.setExecuteTime(10L);
			entity.setRequestDate(currentEpochMilli);

			entityList.add(entity);
		}

		int result = sysEventLogMapper.batchInsertList(entityList);

		assertThat(result, Matchers.greaterThan(0));
	}


	@Transactional
	@Test
	public void m_deleteByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		BatchDeleteMapperBO bo = new BatchDeleteMapperBO();
		bo.setIdList(idList);

		int result = sysEventLogMapper.deleteByIdList(bo);

		assertThat(result, Matchers.greaterThan(0));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================


}
