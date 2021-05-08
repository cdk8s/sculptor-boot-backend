/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginInterceptor.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.interceptor;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.ResponseErrorEnum;
import com.cdk8s.sculptor.eventlistener.event.LoginTokenRefreshEvent;
import com.cdk8s.sculptor.pojo.bo.event.LoginTokenRefreshBO;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.util.response.biz.ResultObject;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String DEFAULT_LOGIN_ERROR_MESSAGE = "您还未登录，请先登录";

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private ApplicationContext applicationContext;

	//=====================================业务处理 start=====================================

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@SneakyThrows
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {

		if (StringUtil.equalsIgnoreCase(request.getMethod(), "options")) {
			return true;
		}

		String accessToken = request.getHeader(GlobalConstant.HEADER_TOKEN_KEY);
		if (StringUtil.isBlank(accessToken)) {
			responseJson(request, response, DEFAULT_LOGIN_ERROR_MESSAGE);
			return false;
		}

		TkeyToken tkeyToken = tokenRedisService.get(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + accessToken);
		if (null == tkeyToken) {
			responseJson(request, response, DEFAULT_LOGIN_ERROR_MESSAGE);
			return false;
		}

		UserInfoContext.setCurrentUser(tkeyToken.getAttributes());

		// 异步更新 redis 中 token 有效时长
		if (RandomUtil.nextInt(0, 10) < 2) {
			// 为了防止更新过于频繁，我们随机采样，采样率 20%
			LoginTokenRefreshBO bo = new LoginTokenRefreshBO();
			bo.setTkeyToken(tkeyToken);
			applicationContext.publishEvent(new LoginTokenRefreshEvent(this, bo));
		}

		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后，生成视图之前执行，所以一般可以用在改造返回值
	 * preHandle方法的返回值为true的时候，才能被调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	/**
	 * 在业务处理器处理请求执行完成后，生成视图之后执行，所以一般用在清理资源
	 * preHandle方法的返回值为true的时候，才能被调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		UserInfoContext.remove();
	}

	//=====================================业务处理 end=====================================
	//=====================================私有方法 start=====================================

	@SneakyThrows
	private void responseJson(final HttpServletRequest request, final HttpServletResponse response, String msg) {
		if (StringUtil.isBlank(msg)) {
			msg = DEFAULT_LOGIN_ERROR_MESSAGE;
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding(GlobalConstant.UTF_8);
		response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);

		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResponseErrorEnum.NEED_LOGIN.getCode());
		resultObject.setIsSuccess(false);
		resultObject.setMsg(msg);
		resultObject.setTimestamp(DatetimeUtil.currentEpochMilli());
		String json = JsonUtil.toJson(resultObject);

		PrintWriter out = response.getWriter();
		out.print(json);
	}


	//=====================================私有方法  end=====================================


}
