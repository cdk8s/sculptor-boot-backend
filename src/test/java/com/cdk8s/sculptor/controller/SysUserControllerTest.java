package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.Application;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.GenderEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserUpdateRequestParam;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SysUserControllerTest {

	private static final Long ID = 111111111111111111L;

	private static final String MODULE_NAME = "sysUser";
	private static final String LIST_URL = "/api/" + MODULE_NAME + "/list";
	private static final String PAGE_URL = "/api/" + MODULE_NAME + "/page";
	private static final String DETAIL_URL = "/api/" + MODULE_NAME + "/detail";
	private static final String CREATE_URL = "/api/" + MODULE_NAME + "/create";
	private static final String UPDATE_URL = "/api/" + MODULE_NAME + "/update";
	private static final String BATCH_DELETE_URL = "/api/" + MODULE_NAME + "/batchDelete";
	private static final String LIST_BY_ID_LIST_URL = "/api/" + MODULE_NAME + "/listByIdList";
	private static final String BATCH_UPDATE_STATE_URL = "/api/" + MODULE_NAME + "/batchUpdateState";

	private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

	private static final MediaType GENERAL_MEDIA_TYPE = MediaType.parseMediaType("*/*");
	private static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(), MediaType.APPLICATION_JSON_UTF8.getSubtype(), UTF8_CHARSET);
	private static final MediaType TEXT_MEDIA_TYPE = new MediaType(MediaType.TEXT_HTML.getType(), MediaType.TEXT_HTML.getSubtype(), UTF8_CHARSET);
	private static final MediaType DEFAULT_FORM_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_FORM_URLENCODED.getType(), MediaType.APPLICATION_FORM_URLENCODED.getSubtype(), UTF8_CHARSET);
	private static final MediaType MULTIPART_FORM_MEDIA_TYPE = new MediaType(MediaType.MULTIPART_FORM_DATA.getType(), MediaType.MULTIPART_FORM_DATA.getSubtype(), UTF8_CHARSET);

	@Autowired
	private MockMvc mockMvc;

	// =====================================查询业务 start=====================================

	@SneakyThrows
	@Test
	public void a_detail() {
		RequestBuilder request = MockMvcRequestBuilders
				.get(DETAIL_URL)
				.param("id", String.valueOf(ID))
				.accept(JSON_MEDIA_TYPE)
				.contentType(DEFAULT_FORM_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@SneakyThrows
	@Test
	public void a_listByIdList() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		IdListRequestParam param = new IdListRequestParam();
		param.setIdList(idList);

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(LIST_BY_ID_LIST_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@SneakyThrows
	@Test
	public void a_list() {
		SysUserPageQueryParam param = new SysUserPageQueryParam();
		param.setStateEnum(StateEnum.ENABLE.getCode());
		param.setDeptId(111111111111111111L);

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(LIST_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@SneakyThrows
	@Test
	public void a_page() {
		SysUserPageQueryParam param = new SysUserPageQueryParam();
		param.setStateEnum(StateEnum.ENABLE.getCode());
		param.setPageNum(1);
		param.setPageSize(10);

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(PAGE_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@SneakyThrows
	@Test
	public void a_create() {
		SysUserCreateRequestParam param = new SysUserCreateRequestParam();
		// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		param.setUsername("admin_test_" + RandomUtil.randomAlphabetic(6));
		param.setUserPassword("e10adc3949ba59abbe56e057f20f883e");
		param.setStateEnum(StateEnum.ENABLE.getCode());
		param.setGenderEnum(GenderEnum.MALE.getCode());

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(CREATE_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@Transactional
	@SneakyThrows
	@Test
	public void b_update() {
		SysUserUpdateRequestParam param = new SysUserUpdateRequestParam();
		param.setId(ID);
		// 根据业务补充其他属性：RandomUtil.randomAlphabetic(6)
		param.setUsername("admin_test_" + RandomUtil.randomAlphabetic(6));
		param.setUserPassword("e10adc3949ba59abbe56e057f20f883e");
		param.setGenderEnum(GenderEnum.MALE.getCode());

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(UPDATE_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@Transactional
	@SneakyThrows
	@Test
	public void b_batchUpdateState() {
		ArrayList<Long> idList = Lists.newArrayList(ID, 222222222222222222L);

		BatchUpdateStateRequestParam param = new BatchUpdateStateRequestParam();
		param.setIdList(idList);
		param.setStateEnum(StateEnum.DISABLE.getCode());

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(BATCH_UPDATE_STATE_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	@Transactional
	@SneakyThrows
	@Test
	public void m_batchDelete() {
		ArrayList<Long> idList = Lists.newArrayList(333333333333333331L);

		IdListRequestParam param = new IdListRequestParam();
		param.setIdList(idList);

		String jsonParam = JsonUtil.toJson(param);

		RequestBuilder request = MockMvcRequestBuilders
				.post(BATCH_DELETE_URL)
				.content(jsonParam)
				.accept(JSON_MEDIA_TYPE)
				.contentType(JSON_MEDIA_TYPE)
				.header(GlobalConstant.HEADER_TOKEN_KEY, GlobalConstantToJunit.ACCESS_TOKEN);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	// =====================================操作业务 end=====================================

	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
