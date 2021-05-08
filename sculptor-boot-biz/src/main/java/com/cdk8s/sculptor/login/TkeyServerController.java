/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TkeyServerController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.login;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.LoginOriginEnum;
import com.cdk8s.sculptor.enums.OfflineTypeEnum;
import com.cdk8s.sculptor.eventlistener.event.LoginLogEvent;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.exception.ValidateCodeException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.*;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.*;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthIntrospect;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserProfile;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.properties.OauthProperties;
import com.cdk8s.sculptor.service.SysLoginLogService;
import com.cdk8s.sculptor.service.SysParamService;
import com.cdk8s.sculptor.service.ValidateCodeService;
import com.cdk8s.sculptor.service.oauth.OauthCheckParamService;
import com.cdk8s.sculptor.service.oauth.OauthGenerateService;
import com.cdk8s.sculptor.service.oauth.OauthLoginApiService;
import com.cdk8s.sculptor.service.oauth.OauthSaveService;
import com.cdk8s.sculptor.strategy.OauthTokenStrategyContext;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.code.CodecUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.util.response.biz.R;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/oauth")
public class TkeyServerController {

	@Autowired
	private StringRedisService<String, OauthTgcToRedisBO> tgcRedisService;

	@Autowired
	private StringRedisService<String, OauthUserInfoToRedisBO> userInfoRedisService;

	@Autowired
	private StringRedisService<String, OauthAccessTokenToRedisBO> accessTokenRedisService;

	@Autowired
	private StringRedisService<String, OauthRefreshTokenToRedisBO> refreshTokenRedisService;

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	@Autowired
	private OauthTokenStrategyContext oauthTokenStrategyContext;

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private OauthLoginApiService oauthLoginApiService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ValidateCodeService validateCodeService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Autowired
	private StringRedisService<String, OauthCodeToRedisBO> codeRedisService;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	@Autowired
	private TkeyService tkeyService;

	//=====================================业务处理 start=====================================

	/**
	 * 登录页面入口
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(final HttpServletRequest request, OauthAuthorizeParam oAuthAuthorizeParam) {
		OauthClientToRedisBO oauthClientToRedisBO = oauthCheckParamService.checkOauthAuthorizeParam(oAuthAuthorizeParam);
		request.setAttribute(GlobalConstant.DEFAULT_LOGIN_PAGE_CLIENT_INFO_KEY, oauthClientToRedisBO);

		if (needImageValidateCode()) {
			// 如果是 0 次则表示永远显示，不管登录用户是谁
			request.setAttribute("deviceId", GenerateIdUtil.getUUID());
		}

		String tgcCookieValue = CookieUtil.getCookie(request, GlobalConstant.OAUTH_SERVER_COOKIE_KEY);
		if (StringUtil.isBlank(tgcCookieValue)) {
			return GlobalConstant.DEFAULT_LOGIN_PAGE_PATH;
		}

		String userInfoRedisKey = oauthCheckParamService.checkCookieTgc(request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT), IPUtil.getIp(request), tgcCookieValue);

		String finalRedirectUrl;
		String redirectUri = oAuthAuthorizeParam.getRedirectUri();
		if (StringUtil.equalsIgnoreCase(oAuthAuthorizeParam.getResponseType(), GlobalConstant.OAUTH_TOKEN_RESPONSE_TYPE)) {
			// 简化模式
			OauthUserInfoToRedisBO oauthUserInfoToRedisBO = userInfoRedisService.get(userInfoRedisKey);

			OauthToken oauthTokenInfoByCodePO = oauthGenerateService.generateOauthTokenInfoBO(true);
			oauthSaveService.saveAccessToken(oauthTokenInfoByCodePO.getAccessToken(), oauthUserInfoToRedisBO.getUserAttribute(), oAuthAuthorizeParam.getClientId(), GlobalConstant.OAUTH_TOKEN_GRANT_TYPE);
			oauthSaveService.saveRefreshToken(oauthTokenInfoByCodePO.getRefreshToken(), oauthUserInfoToRedisBO.getUserAttribute(), oAuthAuthorizeParam.getClientId(), GlobalConstant.OAUTH_TOKEN_GRANT_TYPE);
			finalRedirectUrl = getRedirectUrlWithAccessToken(redirectUri, oauthTokenInfoByCodePO);
		} else {
			// 授权码模式
			String code = oauthGenerateService.generateCode();
			oauthSaveService.saveCodeToRedis(code, tgcCookieValue, userInfoRedisKey, oAuthAuthorizeParam.getClientId(), buildOauthRequestInfoBO(request, redirectUri));
			finalRedirectUrl = getRedirectUrlWithCode(redirectUri, oAuthAuthorizeParam.getState(), code);
		}

		oauthSaveService.updateTgcAndUserInfoRedisKeyExpire(tgcCookieValue, userInfoRedisKey);
		return GlobalConstant.REDIRECT_URI_PREFIX + finalRedirectUrl;
	}

	/**
	 * 表单登录接口：验证用户名和密码
	 */
	@SneakyThrows
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String formLogin(final HttpServletRequest request, final HttpServletResponse response, ModelMap model, OauthFormLoginParam oauthFormLoginParam) {

		// zchtodo 故意设置，让登陆过程更加可见
		// TimeUnit.SECONDS.sleep(RandomUtil.nextInt(1, 3));

		OauthClientToRedisBO oauthClientToRedisBO;
		OauthUserAttribute oauthUserAttribute;
		String userAgent = request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);
		String requestIp = IPUtil.getIp(request);
		String locale = request.getLocale().getLanguage();

		String redirectUri = oauthFormLoginParam.getRedirectUri();
		try {
			oauthClientToRedisBO = oauthCheckParamService.checkClientIdParam(oauthFormLoginParam.getClientId());
			oauthCheckParamService.checkUserAgentAndRequestIpParam(userAgent, requestIp);
			oauthCheckParamService.checkOauthFormLoginParam(oauthFormLoginParam);

			model.put(GlobalConstant.DEFAULT_LOGIN_PAGE_CLIENT_INFO_KEY, oauthClientToRedisBO);

			// 校验验证码
			if (needImageValidateCode()) {
				validateCodeService.validate(oauthFormLoginParam.getDeviceId(), oauthFormLoginParam.getValidateCode());
			}

			// 校验用户名密码
			oauthUserAttribute = oauthLoginApiService.requestLoginApi(oauthFormLoginParam.getUsername(), oauthFormLoginParam.getPassword());

		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			// 异步写入登录事件表数据
			SysLoginLogCreateServiceBO serviceBO = new SysLoginLogCreateServiceBO();
			buildSysLoginLogCreateServiceBOToFailure(serviceBO, e.getMessage(), redirectUri, requestIp, userAgent, locale, oauthFormLoginParam);
			applicationContext.publishEvent(new LoginLogEvent(this, serviceBO));

			if (e instanceof ValidateCodeException || needImageValidateCode()) {
				model.put("deviceId", GenerateIdUtil.getUUID());
			}

			model.put(GlobalConstant.DEFAULT_LOGIN_ERROR_KEY, e.getMessage());
			return GlobalConstant.DEFAULT_LOGIN_PAGE_PATH;
		}

		String userInfoRedisKey = oauthGenerateService.generateUserInfoRedisKey(oauthUserAttribute.getUserId());
		oauthSaveService.saveUserInfoKeyToRedis(userInfoRedisKey, oauthUserAttribute);

		boolean isRememberMe = oauthFormLoginParam.getBoolIsRememberMe();
		String tgc = oauthGenerateService.generateTgc();

		Integer maxTimeToLiveInSeconds = oauthProperties.getTgcAndUserInfoMaxTimeToLiveInSeconds();
		if (isRememberMe) {
			maxTimeToLiveInSeconds = oauthProperties.getRememberMeMaxTimeToLiveInSeconds();
		}
		CookieUtil.setCookie(response, GlobalConstant.OAUTH_SERVER_COOKIE_KEY, tgc, maxTimeToLiveInSeconds, true, oauthProperties.getTgcCookieSecure());

		oauthSaveService.saveTgcToRedisAndCookie(tgc, maxTimeToLiveInSeconds, userInfoRedisKey, userAgent, requestIp, isRememberMe);

		String finalRedirectUrl;
		if (StringUtil.equalsIgnoreCase(oauthFormLoginParam.getResponseType(), GlobalConstant.OAUTH_TOKEN_RESPONSE_TYPE)) {
			// 简化模式
			OauthToken oauthToken = oauthGenerateService.generateOauthTokenInfoBO(true);
			oauthSaveService.saveAccessToken(oauthToken.getAccessToken(), oauthUserAttribute, oauthFormLoginParam.getClientId(), GlobalConstant.OAUTH_TOKEN_GRANT_TYPE);
			oauthSaveService.saveRefreshToken(oauthToken.getRefreshToken(), oauthUserAttribute, oauthFormLoginParam.getClientId(), GlobalConstant.OAUTH_TOKEN_GRANT_TYPE);
			finalRedirectUrl = getRedirectUrlWithAccessToken(redirectUri, oauthToken);
		} else {
			// 授权码模式
			String code = oauthGenerateService.generateCode();
			oauthSaveService.saveCodeToRedis(code, tgc, userInfoRedisKey, oauthFormLoginParam.getClientId(), buildOauthRequestInfoBO(request, redirectUri));
			finalRedirectUrl = getRedirectUrlWithCode(redirectUri, oauthFormLoginParam.getState(), code);
		}

		return GlobalConstant.REDIRECT_URI_PREFIX + finalRedirectUrl;

	}


	/**
	 * 换取 token（授权码模式、客户端模式、密码模式、刷新模式）
	 */
	@RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> token(final HttpServletRequest request, OauthTokenParam oauthTokenParam) {
		// 必须放在这里拿 code 信息，不然 generateOauthTokenInfo() 中会删除掉该信息
		OauthCodeToRedisBO oauthCodeToRedisBO = codeRedisService.get(GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + oauthTokenParam.getCode());

		String grantType = oauthTokenParam.getGrantType();
		// 如果不是 code 模式，在获取 token 的时候是需要校验验证码的
		if (!StringUtil.equalsIgnoreCase(grantType, GlobalConstant.OAUTH_CODE_GRANT_TYPE)) {
			// 校验验证码
			if (needImageValidateCode()) {
				validateCodeService.validate(oauthTokenParam.getDeviceId(), oauthTokenParam.getValidateCode());
			}
		}

		oauthCheckParamService.checkGrantTypeParam(grantType);
		resolveRequestHeader(request, oauthTokenParam);

		OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO = new OauthTokenStrategyHandleBO();
		oauthTokenStrategyContext.checkParam(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);

		OauthToken oauthToken = oauthTokenStrategyContext.generateOauthTokenInfo(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);
		TkeyToken tkeyToken = getTkeyToken(oauthToken);
		tokenRedisService.set(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + oauthToken.getAccessToken(), tkeyToken, oauthClientProperties.getTokenMaxTimeToLiveInSeconds());

		// 异步写入登录事件表数据
		if (StringUtil.equalsIgnoreCase(grantType, GlobalConstant.OAUTH_CODE_GRANT_TYPE)) {
			SysLoginLogCreateServiceBO serviceBO = new SysLoginLogCreateServiceBO();
			OauthUserAttribute userAttribute = oauthTokenStrategyHandleBO.getUserAttribute();
			buildSysLoginLogCreateServiceBOToSuccess(serviceBO, oauthTokenParam.getClientId(), oauthToken.getAccessToken(), userAttribute.getUsername(), oauthCodeToRedisBO);
			applicationContext.publishEvent(new LoginLogEvent(this, serviceBO));
		}

		return ResponseEntity.ok(oauthToken);
	}

	private TkeyToken getTkeyToken(OauthToken oauthToken) {
		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(oauthToken.getAccessToken());
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken.getAccessToken()));
		return tkeyToken;
	}


	/**
	 * 根据 AccessToken 获取用户信息
	 */
	@RequestMapping(value = "/userinfo", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> userinfo(final HttpServletRequest request) {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = oauthCheckParamService.checkAccessTokenParam(request);

		OauthUserProfile oauthUserProfile = new OauthUserProfile();
		buildOauthUserInfoByTokenDTO(oauthUserProfile, oauthAccessTokenToRedisBO);

		return ResponseEntity.ok(oauthUserProfile);
	}


	/**
	 * 验证 AccessToken / RefreshToken 有效性和基础信息
	 */
	@RequestMapping(value = "/introspect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> introspect(final HttpServletRequest request, OauthIntrospectTokenParam oauthIntrospectTokenParam) {

		resolveRequestHeader(request, oauthIntrospectTokenParam);
		OauthIntrospect oauthIntrospect = oauthCheckParamService.checkOauthIntrospectTokenParam(oauthIntrospectTokenParam);

		Long iat = 0L;
		String grantType = "";

		String token = oauthIntrospectTokenParam.getToken();
		String tokenTypeHint = oauthIntrospectTokenParam.getTokenTypeHint();
		if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalConstant.OAUTH_ACCESS_TOKEN_TYPE_HINT)) {
			// 验证 AccessToken
			OauthAccessTokenToRedisBO oauthTokenToRedisDTO = accessTokenRedisService.get(GlobalConstant.REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX + token);
			if (null == oauthTokenToRedisDTO) {
				throw new OauthApiException("token 已失效");
			}
			grantType = oauthTokenToRedisDTO.getGrantType();
			iat = oauthTokenToRedisDTO.getIat();
			oauthIntrospect.setExp(iat + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		} else if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE)) {
			// 验证 RefreshToken
			OauthRefreshTokenToRedisBO oauthTokenToRedisDTO = refreshTokenRedisService.get(GlobalConstant.REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX + token);
			if (null == oauthTokenToRedisDTO) {
				throw new OauthApiException("token 已失效");
			}
			grantType = oauthTokenToRedisDTO.getGrantType();
			iat = oauthTokenToRedisDTO.getIat();
			oauthIntrospect.setExp(iat + oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		}

		oauthIntrospect.setGrantType(grantType);
		oauthIntrospect.setIat(iat);

		return ResponseEntity.ok(oauthIntrospect);
	}

	/**
	 * 登出
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, final HttpServletResponse response, @RequestParam(value = "redirect_uri", required = false) String redirectUri) {

		String tgcCookieValue = CookieUtil.getCookie(request, GlobalConstant.OAUTH_SERVER_COOKIE_KEY);
		if (StringUtil.isNotBlank(tgcCookieValue)) {
			tgcRedisService.delete(GlobalConstant.REDIS_TGC_KEY_PREFIX + tgcCookieValue);
			CookieUtil.deleteCookie(request, response, GlobalConstant.OAUTH_SERVER_COOKIE_KEY);
		}

		if (StringUtil.isNotBlank(redirectUri)) {
			return GlobalConstant.REDIRECT_URI_PREFIX + redirectUri;
		}

		return GlobalConstant.DEFAULT_LOGOUT_PAGE_PATH;

	}


	/**
	 * 刷新验证码
	 */
	@RequestMapping(value = "/refreshImageValidateCode", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> refreshImageValidateCode() {
		Map<String, Object> model = new HashMap<>();
		model.put("deviceId", GenerateIdUtil.getUUID());
		return R.success(model);
	}


	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private void buildOauthUserInfoByTokenDTO(OauthUserProfile oauthUserProfile, OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO) {
		OauthUserAttribute oauthUserAttribute = oauthAccessTokenToRedisBO.getUserAttribute();

		if (null != oauthUserAttribute) {
			oauthUserProfile.setUserAttribute(oauthUserAttribute);
			oauthUserProfile.setUsername(oauthUserAttribute.getUsername());
			oauthUserProfile.setName(oauthUserAttribute.getUsername());
			oauthUserProfile.setUserId(oauthUserAttribute.getUserId());
			oauthUserProfile.setId(oauthUserAttribute.getUserId());
		} else {
			// 客户端模式情况下是没有用户信息的
			oauthUserProfile.setUserAttribute(new OauthUserAttribute());
		}

		oauthUserProfile.setIat(oauthAccessTokenToRedisBO.getIat());
		oauthUserProfile.setExp(oauthAccessTokenToRedisBO.getIat() + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		oauthUserProfile.setClientId(oauthAccessTokenToRedisBO.getClientId());
		oauthUserProfile.setGrantType(oauthAccessTokenToRedisBO.getGrantType());

	}

	private String getRedirectUrlWithCode(String redirectUri, String state, String code) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(redirectUri);
		if (StringUtil.containsIgnoreCase(redirectUri, "?")) {
			stringBuilder.append("&");
		} else {
			stringBuilder.append("?");
		}
		stringBuilder.append(GlobalConstant.OAUTH_CODE_RESPONSE_TYPE);
		stringBuilder.append("=");
		stringBuilder.append(code);
		if (StringUtil.isNotBlank(state)) {
			stringBuilder.append("&");
			stringBuilder.append(GlobalConstant.OAUTH_STATE_KEY);
			stringBuilder.append("=");
			stringBuilder.append(state);
		}

		return stringBuilder.toString();
	}

	private String getRedirectUrlWithAccessToken(String redirectUri, OauthToken oauthToken) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(redirectUri);
		stringBuilder.append("#");
		stringBuilder.append(GlobalConstant.OAUTH_ACCESS_TOKEN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthToken.getAccessToken());
		stringBuilder.append("&");
		stringBuilder.append(GlobalConstant.OAUTH_TOKEN_TYPE_KEY);
		stringBuilder.append("=");
		stringBuilder.append(GlobalConstant.OAUTH_TOKEN_TYPE);
		stringBuilder.append("&");
		stringBuilder.append(GlobalConstant.OAUTH_EXPIRES_IN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		stringBuilder.append("&");
		stringBuilder.append(GlobalConstant.OAUTH_REFRESH_TOKEN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthToken.getRefreshToken());
		return stringBuilder.toString();
	}

	private void resolveRequestHeader(HttpServletRequest request, OauthClientParam oauthClientParam) {
		String authorizationHeader = request.getHeader(GlobalConstant.HTTP_HEADER_AUTHORIZATION);
		if (StringUtil.isBlank(authorizationHeader)) {
			return;
		}

		if (StringUtil.containsIgnoreCase(authorizationHeader, GlobalConstant.BASIC_AUTH_UPPER_PREFIX)) {
			String replaceIgnoreCase = StringUtil.replaceIgnoreCase(authorizationHeader, GlobalConstant.BASIC_AUTH_UPPER_PREFIX, GlobalConstant.BASIC_AUTH_LOWER_PREFIX);
			authorizationHeader = StringUtil.substringAfter(replaceIgnoreCase, GlobalConstant.BASIC_AUTH_LOWER_PREFIX);
		}
		String basic = CodecUtil.base64DecodeBySafe(authorizationHeader);
		List<String> stringList = StringUtil.split(basic, ":");
		if (stringList.size() < 2) {
			return;
		}
		oauthClientParam.setClientId(stringList.get(0));
		oauthClientParam.setClientSecret(stringList.get(1));

	}

	private OauthRequestInfoBO buildOauthRequestInfoBO(final HttpServletRequest request, String redirectUri) {
		String decodeURL = CodecUtil.decodeURL(StringUtil.substringAfter(redirectUri, "?redirect_uri="));

		String userAgent = request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);

		OauthRequestInfoBO oauthRequestInfoBO = new OauthRequestInfoBO();
		oauthRequestInfoBO.setRequestUrl(decodeURL);
		oauthRequestInfoBO.setIpAddress(IPUtil.getIp(request));
		oauthRequestInfoBO.setUserAgent(userAgent);
		oauthRequestInfoBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		oauthRequestInfoBO.setOsName(UserAgentUtil.getOs(userAgent));
		oauthRequestInfoBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
		oauthRequestInfoBO.setBrowserLocale(request.getLocale().getLanguage());

		return oauthRequestInfoBO;
	}

	private void buildSysLoginLogCreateServiceBOToFailure(
			SysLoginLogCreateServiceBO serviceBO,
			String errorMessage,
			String redirectUri,
			String requestIp,
			String userAgent,
			String locale,
			OauthFormLoginParam oauthFormLoginParam
	) {
		serviceBO.setMessage(errorMessage);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setExceptionMsg(errorMessage);
		serviceBO.setClientId(oauthFormLoginParam.getClientId());
		serviceBO.setRequestUrl(CodecUtil.decodeURL(StringUtil.substringAfter(redirectUri, "?redirect_uri=")));
		serviceBO.setUsername(StringUtil.trim(oauthFormLoginParam.getUsername()));
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.NO.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.NO.getCode());
		serviceBO.setIpAddress(requestIp);
		serviceBO.setUserAgent(userAgent);
		serviceBO.setBrowserLocale(locale);
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());
		serviceBO.setOfflineTypeEnum(OfflineTypeEnum.EXPIRE_LOGOUT.getCode());
		serviceBO.setLoginDate(DatetimeUtil.currentEpochMilli());
		serviceBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		serviceBO.setOsName(UserAgentUtil.getOs(userAgent));
		serviceBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
	}

	private void buildSysLoginLogCreateServiceBOToSuccess(
			SysLoginLogCreateServiceBO serviceBO,
			String clientId,
			String accessToken,
			String username,
			OauthCodeToRedisBO oauthCodeToRedisBO
	) {
		serviceBO.setMessage("code 换取 token 成功");
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setClientId(clientId);
		serviceBO.setToken(accessToken);
		serviceBO.setUsername(username);
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.YES.getCode());
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());
		serviceBO.setOfflineTypeEnum(OfflineTypeEnum.EXPIRE_LOGOUT.getCode());
		serviceBO.setLoginDate(DatetimeUtil.currentEpochMilli());

		OauthRequestInfoBO oauthRequestInfoBO = oauthCodeToRedisBO.getOauthRequestInfoBO();
		serviceBO.setRequestUrl(oauthRequestInfoBO.getRequestUrl());
		serviceBO.setIpAddress(oauthRequestInfoBO.getIpAddress());
		serviceBO.setUserAgent(oauthRequestInfoBO.getUserAgent());
		serviceBO.setDeviceName(oauthRequestInfoBO.getDeviceName());
		serviceBO.setOsName(oauthRequestInfoBO.getOsName());
		serviceBO.setBrowserName(oauthRequestInfoBO.getBrowserName());
		serviceBO.setBrowserLocale(oauthRequestInfoBO.getBrowserLocale());
	}

	private Boolean isNeedShowValidateCode() {
		// 查询验证码开关是否开启，以及一天的错误限制次数
		String paramCode = "switch.loginCaptcha.boolean";
		Object paramValue = sysParamService.findOneValueByParamCode(paramCode);
		if (null == paramValue) {
			return false;
		}

		return (Boolean) paramValue;
	}

	private Integer numberLoginErrorShow() {
		String paramCode = "number.loginErrorShowCaptcha.integer";
		Object paramValue = sysParamService.findOneValueByParamCode(paramCode);
		if (null == paramValue) {
			return 10;
		}

		return (Integer) paramValue;
	}


	private Boolean needImageValidateCode() {
		Integer loginErrorNumber = numberLoginErrorShow();
		if (isNeedShowValidateCode() && loginErrorNumber.equals(0)) {
			// 如果是 0 次则表示永远显示，不管登录用户是谁
			return true;
		}
		return false;
	}


	//=====================================私有方法  end=====================================

}
