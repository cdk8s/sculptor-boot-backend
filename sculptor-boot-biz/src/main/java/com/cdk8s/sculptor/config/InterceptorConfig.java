/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：InterceptorConfig.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;


import com.cdk8s.sculptor.interceptor.LoginInterceptor;
import com.cdk8s.sculptor.interceptor.MultiapiLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 代码的顺序就是拦截器执行的顺序
 * 按顺序执行所有拦截器的 preHandle，所有 preHandle 执行完再执行全部 postHandle 最后是 postHandle
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Autowired
	private MultiapiLoginInterceptor multiapiLoginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/open/**");

		registry.addInterceptor(multiapiLoginInterceptor)
				.addPathPatterns("/multiapi/**");


		WebMvcConfigurer.super.addInterceptors(registry);
	}


}
