/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinControllerTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.Application;
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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class WeixinControllerTest {


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
	public void getQr() {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/open/weixin/getQr")
				.accept(JSON_MEDIA_TYPE)
				.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
	}

	// =====================================查询业务 end=====================================

}
