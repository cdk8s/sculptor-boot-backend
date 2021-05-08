/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WebConfig.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;


import com.cdk8s.sculptor.pojo.dto.param.oauth.resolve.*;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new OauthAuthorizeParamArgumentResolver());
		argumentResolvers.add(new OauthFormParamArgumentResolver());
		argumentResolvers.add(new OauthRefreshTokenParamArgumentResolver());
		argumentResolvers.add(new OauthTokenParamArgumentResolver());
		argumentResolvers.add(new OauthIntrospectTokenParamArgumentResolver());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		builder.serializerByType(Long.class, new CustomLongConverter());
		builder.serializerByType(Long.TYPE, new CustomLongConverter());
		converters.add(0, new MappingJackson2HttpMessageConverter(builder.build()));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 映射本地文件上传存储路径和访问路径的匹配映射
		String localUploadStoragePath = Paths.get(uploadParamProperties.getLocalUploadStoragePath()).normalize().toUri().toASCIIString();
		registry.addResourceHandler(uploadParamProperties.getLocalUploadFileUrlRelativePath() + "/**").addResourceLocations(localUploadStoragePath).setCachePeriod(0);
	}


}
