/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthIntrospectTokenParamArgumentResolver.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.oauth.resolve;


import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthIntrospectTokenParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OauthIntrospectTokenParamArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(OauthIntrospectTokenParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
		OauthIntrospectTokenParam param = new OauthIntrospectTokenParam();
		param.setClientId(nativeWebRequest.getParameter("client_id"));
		param.setClientSecret(nativeWebRequest.getParameter("client_secret"));
		param.setToken(nativeWebRequest.getParameter("token"));
		param.setTokenTypeHint(nativeWebRequest.getParameter("token_type_hint"));
		return param;
	}

}
