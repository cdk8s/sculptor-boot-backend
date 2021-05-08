/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthCheckParamService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.oauth;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.ResponseProduceTypeEnum;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthAccessTokenToRedisBO;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthClientToRedisBO;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthTgcToRedisBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthAuthorizeParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthFormLoginParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthIntrospectTokenParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthIntrospect;
import com.cdk8s.sculptor.properties.OauthProperties;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class OauthCheckParamService {

	@Autowired
	private OauthClientService oauthClientService;

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private StringRedisService<String, OauthTgcToRedisBO> tgcRedisService;

	@Autowired
	private StringRedisService<String, OauthAccessTokenToRedisBO> accessTokenRedisService;

	//=====================================业务处理 start=====================================

	public String checkCookieTgc(String userAgent, String requestIp, String tgcCookieValue) {

		try {
			checkUserAgentAndRequestIpParam(userAgent, requestIp);
		} catch (Exception e) {
			throw new OauthApiException(e.getMessage(), ResponseProduceTypeEnum.HTML);
		}

		OauthTgcToRedisBO oauthTgcToRedisBO = tgcRedisService.get(GlobalConstant.REDIS_TGC_KEY_PREFIX + tgcCookieValue);
		if (null == oauthTgcToRedisBO) {
			throw new OauthApiException("TGC 已失效，请重新登录", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		// 防止重放攻击
		// if ((!StringUtil.equalsIgnoreCase(oauthTgcToRedisBO.getUserAgent(), userAgent)) || (!StringUtil.equalsIgnoreCase(oauthTgcToRedisBO.getRequestIp(), requestIp))) {
		// 	throw new OauthApiException("您的登录历史密钥只能在同一台电脑和网络上使用", ResponseProduceTypeEnum.HTML);
		// }

		return oauthTgcToRedisBO.getUserInfoRedisKey();
	}

	public void checkGrantTypeParam(String grantType) {
		if (StringUtil.isBlank(grantType)) {
			throw new OauthApiException("grant_type 不能为空");
		}

		Map<String, Object> grantTypeMap = new HashMap<>(4);
		grantTypeMap.put(GlobalConstant.OAUTH_CODE_GRANT_TYPE, GlobalConstant.OAUTH_CODE_GRANT_TYPE);
		grantTypeMap.put(GlobalConstant.OAUTH_CLIENT_GRANT_TYPE, GlobalConstant.OAUTH_CLIENT_GRANT_TYPE);
		grantTypeMap.put(GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE, GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE);
		grantTypeMap.put(GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE, GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE);

		if (null == grantTypeMap.get(grantType)) {
			throw new OauthApiException("请求类型不匹配");
		}

		List<String> enableGrantTypeList = oauthProperties.getEnableGrantTypeList();
		if (!enableGrantTypeList.contains(grantType.toLowerCase())) {
			throw new OauthApiException("请求类型没有开启");
		}

	}

	public OauthAccessTokenToRedisBO checkAccessTokenParam(final HttpServletRequest request) {
		String accessTokenFromHeader = request.getHeader(GlobalConstant.HTTP_HEADER_AUTHORIZATION);
		String accessTokenFromRequest = request.getParameter(GlobalConstant.OAUTH_ACCESS_TOKEN_KEY);

		if (StringUtil.isBlank(accessTokenFromHeader) && StringUtil.isBlank(accessTokenFromRequest)) {
			throw new OauthApiException("access_token 不能为空");
		}

		String accessToken;
		if (StringUtil.isNotBlank(accessTokenFromHeader)) {
			// header 参数优先级高于 form
			if (StringUtil.containsIgnoreCase(accessTokenFromHeader, GlobalConstant.OAUTH_TOKEN_TYPE_UPPER_PREFIX)) {
				String replaceIgnoreCase = StringUtil.replaceIgnoreCase(accessTokenFromHeader, GlobalConstant.OAUTH_TOKEN_TYPE_UPPER_PREFIX, GlobalConstant.OAUTH_TOKEN_TYPE_LOWER_PREFIX);
				accessToken = StringUtil.substringAfter(replaceIgnoreCase, GlobalConstant.OAUTH_TOKEN_TYPE_LOWER_PREFIX);
			} else {
				accessToken = accessTokenFromHeader;
			}
		} else {
			accessToken = accessTokenFromRequest;
		}

		if (!StringUtil.containsIgnoreCase(accessToken, GlobalConstant.OAUTH_ACCESS_TOKEN_PREFIX)) {
			throw new OauthApiException("access_token 参数格式不对，必须包含：" + GlobalConstant.OAUTH_ACCESS_TOKEN_PREFIX);
		}

		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = accessTokenRedisService.get(GlobalConstant.REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX + accessToken);
		if (null == oauthAccessTokenToRedisBO) {
			throw new OauthApiException("access_token 已失效");
		}

		return oauthAccessTokenToRedisBO;
	}

	public OauthClientToRedisBO checkClientIdParam(String clientId) {
		if (StringUtil.isBlank(clientId)) {
			throw new OauthApiException("client_id 参数不能为空");
		}

		// clientId 只能包含数字、字母和下划线
		String regString = "^[A-Za-z0-9_]+$";
		if (!clientId.matches(regString)) {
			throw new OauthApiException("client_id 参数值必须为字母、数字、下划线组成");
		}

		OauthClientToRedisBO oauthClientToRedisBO = oauthClientService.findByClientId(clientId);
		if (null == oauthClientToRedisBO || StringUtil.isBlank(oauthClientToRedisBO.getClientId())) {
			throw new OauthApiException("client_id 不存在");
		}

		return oauthClientToRedisBO;
	}

	public void checkClientIdAndClientSecretParam(String clientId, String clientSecret, Boolean clientSecretIsRequired) {

		OauthClientToRedisBO oauthClientToRedisBO = checkClientIdParam(clientId);

		if (clientSecretIsRequired && StringUtil.isBlank(clientSecret)) {
			throw new OauthApiException("client_secret 参数不能为空");
		}

		if (clientSecretIsRequired) {
			if (StringUtil.notEqualsIgnoreCase(oauthClientToRedisBO.getClientSecret(), clientSecret)) {
				throw new OauthApiException("client_id 与 client_secret 不匹配");
			}
		}
	}

	public OauthClientToRedisBO checkOauthAuthorizeParam(OauthAuthorizeParam oauthAuthorizeParam) {
		String responseType = oauthAuthorizeParam.getResponseType();
		String redirectUri = oauthAuthorizeParam.getRedirectUri();
		String clientId = oauthAuthorizeParam.getClientId();

		OauthClientToRedisBO clientIdAndRedirectUriObject;
		try {
			clientIdAndRedirectUriObject = checkClientIdAndRedirectUriParam(clientId, redirectUri);
		} catch (Exception e) {
			throw new OauthApiException(e.getMessage(), ResponseProduceTypeEnum.HTML);
		}

		if (StringUtil.isBlank(responseType)) {
			throw new OauthApiException("response_type 参数不能为空", ResponseProduceTypeEnum.HTML);
		}

		if (!(StringUtil.equalsIgnoreCase(responseType, GlobalConstant.OAUTH_CODE_RESPONSE_TYPE) || StringUtil.equalsIgnoreCase(responseType, GlobalConstant.OAUTH_TOKEN_RESPONSE_TYPE))) {
			throw new OauthApiException("response_type 参数值有误", ResponseProduceTypeEnum.HTML);
		}

		return clientIdAndRedirectUriObject;
	}

	public void checkUserAgentAndRequestIpParam(String userAgent, String requestIp) {
		if (StringUtil.isBlank(userAgent)) {
			throw new OauthApiException("userAgent 参数不能为空");
		}

		if (StringUtil.isBlank(requestIp)) {
			throw new OauthApiException("requestIp 参数不能为空");
		}
	}

	public void checkUsernamePasswordParam(String username, String password) {

		if (StringUtil.isBlank(username)) {
			throw new OauthApiException("用户名不能为空");
		}

		if (StringUtil.isBlank(password)) {
			throw new OauthApiException("密码不能为空");
		}
	}

	public void checkOauthFormLoginParam(OauthFormLoginParam oauthFormLoginParam) {
		String username = oauthFormLoginParam.getUsername();
		String password = oauthFormLoginParam.getPassword();
		String redirectUri = oauthFormLoginParam.getRedirectUri();
		String clientId = oauthFormLoginParam.getClientId();

		checkUsernamePasswordParam(username, password);

		checkClientIdAndRedirectUriParam(clientId, redirectUri);
	}

	public void checkOauthTokenParam(OauthTokenParam oauthTokenParam) {
		String code = oauthTokenParam.getCode();
		String grantType = oauthTokenParam.getGrantType();
		String redirectUri = oauthTokenParam.getRedirectUri();
		String clientId = oauthTokenParam.getClientId();
		String clientSecret = oauthTokenParam.getClientSecret();

		if (StringUtil.isBlank(code)) {
			throw new OauthApiException("code 参数不能为空");
		}

		if (StringUtil.isBlank(grantType)) {
			throw new OauthApiException("grant_type 参数不能为空");
		}

		if (StringUtil.isBlank(clientSecret)) {
			throw new OauthApiException("client_secret 参数不能为空");
		}

		if (!StringUtil.containsIgnoreCase(code, GlobalConstant.OAUTH_CODE_PREFIX)) {
			throw new OauthApiException("code 参数格式不对，必须包含：" + GlobalConstant.OAUTH_CODE_PREFIX);
		}

		if (StringUtil.notEqualsIgnoreCase(grantType, GlobalConstant.OAUTH_CODE_GRANT_TYPE)) {
			throw new OauthApiException("grant_type 参数值必须为：" + GlobalConstant.OAUTH_CODE_GRANT_TYPE);
		}

		OauthClientToRedisBO oauthClientToRedisBO = checkClientIdAndRedirectUriParam(clientId, redirectUri);
		if (StringUtil.notEqualsIgnoreCase(oauthClientToRedisBO.getClientSecret(), clientSecret)) {
			throw new OauthApiException("client_id 与 client_secret 不匹配");
		}
	}

	public void checkOauthRefreshTokenParam(OauthTokenParam oauthTokenParam) {
		String grantType = oauthTokenParam.getGrantType();
		String refreshToken = oauthTokenParam.getRefreshToken();
		String clientId = oauthTokenParam.getClientId();
		String clientSecret = oauthTokenParam.getClientSecret();

		if (StringUtil.isBlank(refreshToken)) {
			throw new OauthApiException("refresh_token 参数不能为空");
		}

		if (StringUtil.isBlank(grantType)) {
			throw new OauthApiException("grant_type 参数不能为空");
		}

		if (StringUtil.isBlank(clientSecret)) {
			throw new OauthApiException("client_secret 参数不能为空");
		}

		if (!StringUtil.containsIgnoreCase(refreshToken, GlobalConstant.OAUTH_REFRESH_TOKEN_PREFIX)) {
			throw new OauthApiException("refresh_token 参数格式不对，必须包含：" + GlobalConstant.OAUTH_REFRESH_TOKEN_PREFIX);
		}

		if (StringUtil.notEqualsIgnoreCase(grantType, GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE)) {
			throw new OauthApiException("grant_type 参数值必须为：" + GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE);
		}

		OauthClientToRedisBO oauthClientToRedisBO = checkClientIdParam(clientId);

		if (StringUtil.notEqualsIgnoreCase(oauthClientToRedisBO.getClientSecret(), clientSecret)) {
			throw new OauthApiException("client_id 与 client_secret 不匹配");
		}
	}

	public OauthIntrospect checkOauthIntrospectTokenParam(OauthIntrospectTokenParam oauthIntrospectTokenParam) {
		String token = oauthIntrospectTokenParam.getToken();
		String tokenTypeHint = oauthIntrospectTokenParam.getTokenTypeHint();
		String clientSecret = oauthIntrospectTokenParam.getClientSecret();

		if (StringUtil.isBlank(token)) {
			throw new OauthApiException("token 参数不能为空");
		}

		if (StringUtil.isBlank(tokenTypeHint)) {
			throw new OauthApiException("token_type_hint 参数不能为空");
		}

		if (StringUtil.isBlank(clientSecret)) {
			throw new OauthApiException("client_secret 参数不能为空");
		}

		if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalConstant.OAUTH_ACCESS_TOKEN_TYPE_HINT)) {
			if (!StringUtil.containsIgnoreCase(token, GlobalConstant.OAUTH_ACCESS_TOKEN_PREFIX)) {
				throw new OauthApiException("access_token 参数格式不对，必须包含：" + GlobalConstant.OAUTH_ACCESS_TOKEN_PREFIX);
			}
		} else if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE)) {
			if (!StringUtil.containsIgnoreCase(token, GlobalConstant.OAUTH_REFRESH_TOKEN_PREFIX)) {
				throw new OauthApiException("refresh_token 参数格式不对，必须包含：" + GlobalConstant.OAUTH_REFRESH_TOKEN_PREFIX);
			}
		} else {
			throw new OauthApiException("token_type_hint 参数值不合法");
		}

		String clientId = oauthIntrospectTokenParam.getClientId();
		OauthClientToRedisBO oauthClientToRedisBO = checkClientIdParam(clientId);

		if (StringUtil.notEqualsIgnoreCase(oauthClientToRedisBO.getClientSecret(), clientSecret)) {
			throw new OauthApiException("client_id 与 client_secret 不匹配");
		}

		OauthIntrospect oauthIntrospect = new OauthIntrospect();
		oauthIntrospect.setTokenType(GlobalConstant.OAUTH_TOKEN_TYPE);
		oauthIntrospect.setClientId(clientId);
		return oauthIntrospect;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private OauthClientToRedisBO checkClientIdAndRedirectUriParam(String clientId, String redirectUri) {

		OauthClientToRedisBO checkClientIdObject = checkClientIdParam(clientId);

		if (StringUtil.isBlank(redirectUri)) {
			throw new OauthApiException("redirect_uri 参数不能为空");
		}

		String regularToRedirectUri = checkClientIdObject.getClientUrl();
		if (!redirectUri.matches(regularToRedirectUri)) {
			throw new OauthApiException("redirect_uri 不匹配该 client_id");
		}

		return checkClientIdObject;
	}

	//=====================================私有方法  end=====================================

}
