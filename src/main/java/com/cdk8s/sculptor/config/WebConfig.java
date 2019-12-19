package com.cdk8s.sculptor.config;


import com.cdk8s.sculptor.pojo.dto.param.oauth.resolve.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

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

}
