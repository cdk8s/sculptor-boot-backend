/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TkeyClientController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.login;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.eventlistener.event.LogoutTokenRefreshEvent;
import com.cdk8s.sculptor.pojo.bo.event.LogoutTokenRefreshBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthClientCodeRequestParam;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.util.response.biz.R;
import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.bo.OAuth2AccessToken;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import com.cdk8s.tkey.client.rest.utils.CodecUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class TkeyClientController {

	@Autowired
	private TkeyService tkeyService;

	@Autowired
	private TkeyProperties tkeyProperties;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private ApplicationContext applicationContext;

	//=====================================业务处理 start=====================================

	/**
	 * 接收 code，然后换取 token
	 */
	@SneakyThrows
	@RequestMapping(value = "/codeCallback", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> codeCallback(final HttpServletRequest request, final HttpServletResponse response, @Valid @RequestBody OauthClientCodeRequestParam codeRequestParam) {
		String code = codeRequestParam.getCode();
		String redirectUri = codeRequestParam.getRedirectUri();

		TkeyToken tkeyToken = getAccessToken(code);
		String accessToken = tkeyToken.getAccessToken();

		redirectUri = CodecUtil.decodeURL(redirectUri);

		// 前端拿到这两个返回参数进行重定向与设置 LocalStorage
		Map<String, Object> map = new HashMap<>();
		map.put("redirectUri", redirectUri);
		map.put("token", accessToken);

		tokenRedisService.set(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + accessToken, tkeyToken, oauthClientProperties.getTokenMaxTimeToLiveInSeconds());
		return R.success(map);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "redirect_uri", required = false) String redirectUri, @RequestParam(value = "token", required = false) String token) {
		// 如果登出的时候，前端有传 redirectUri 参数则以该参数优先。
		// 如果没有传，则以 tkeyProperties.clientLogoutRedirectUri 参数为最终跳转地址
		String finalLogoutUri = tkeyProperties.getFinalLogoutUri();
		if (StringUtil.isNotBlank(redirectUri)) {
			finalLogoutUri = redirectUri;
		}

		String accessToken = request.getHeader(GlobalConstant.HEADER_TOKEN_KEY);
		if (StringUtil.isBlank(accessToken)) {
			accessToken = token;
		}

		String multiapiToken = request.getHeader(GlobalConstant.MULTI_HEADER_TOKEN_KEY);
		if (StringUtil.isNotBlank(multiapiToken)) {
			accessToken = multiapiToken;
		}

		if (StringUtil.isNotBlank(accessToken)) {
			Boolean flag = tokenRedisService.delete(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + accessToken);
			if (flag) {
				// 异步更新登录记录表中登出信息
				LogoutTokenRefreshBO bo = new LogoutTokenRefreshBO();
				bo.setAccessToken(accessToken);
				applicationContext.publishEvent(new LogoutTokenRefreshEvent(this, bo));
			}
		}

		return "redirect:" + finalLogoutUri;
	}

	@RequestMapping(value = "/logoutByApi", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<?> logoutByApi(final HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader(GlobalConstant.HEADER_TOKEN_KEY);
		String multiapiToken = request.getHeader(GlobalConstant.MULTI_HEADER_TOKEN_KEY);
		if (StringUtil.isNotBlank(multiapiToken)) {
			accessToken = multiapiToken;
		}

		if (StringUtil.isNotBlank(accessToken)) {
			Boolean flag = tokenRedisService.delete(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + accessToken);
			if (flag) {
				// 异步更新登录记录表中登出信息
				LogoutTokenRefreshBO bo = new LogoutTokenRefreshBO();
				bo.setAccessToken(accessToken);
				applicationContext.publishEvent(new LogoutTokenRefreshEvent(this, bo));
			}
		}

		return R.success();
	}

	@RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
	@ResponseBody
	private String logoutSuccess() {
		return "登出成功";
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private TkeyToken getAccessToken(String code) {
		OAuth2AccessToken oauthToken = tkeyService.getAccessToken(code);
		String accessToken = oauthToken.getAccessToken();

		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(accessToken);
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken));

		return tkeyToken;
	}

	//=====================================私有方法  end=====================================
}
