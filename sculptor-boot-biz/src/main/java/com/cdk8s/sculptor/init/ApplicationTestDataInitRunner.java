/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ApplicationTestDataInitRunner.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.*;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.properties.InitParamProperties;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.properties.OauthProperties;
import com.cdk8s.sculptor.util.SpringUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Order(10)
@Component
@ConditionalOnProperty(name = "custom.properties.init.start-test-data-init-enabled", havingValue = "true", matchIfMissing = false)
public class ApplicationTestDataInitRunner implements ApplicationRunner {

	public static List<String> CACHE_INIT_TKEY_KEY_LIST = new ArrayList<>();

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisService<String, OauthClientToRedisBO> clientRedisService;

	@Autowired
	private StringRedisService<String, OauthCodeToRedisBO> codeRedisService;

	@Autowired
	private StringRedisService<String, OauthAccessTokenToRedisBO> accessTokenRedisService;

	@Autowired
	private StringRedisService<String, OauthRefreshTokenToRedisBO> refreshTokenRedisService;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private TkeyProperties tkeyProperties;

	@Autowired
	private InitParamProperties initParamProperties;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {
		log.info("=================================预设 Redis 测试数据 Start=================================");


		if (initParamProperties.getStartRemoveOldAllCacheEnabled()) {
			Object executeResult = redisTemplate.execute((RedisCallback<Object>) connection -> {
				// 先清空 redis 库中数据
				connection.flushDb();
				log.info("启动清空 redis 所以旧数据成功");
				return true;
			});

			if (null != executeResult && !((Boolean) executeResult)) {
				throw new SystemException("启动清空 redis 所以旧数据失败");
			}
		}

		OauthClientToRedisBO oauthClientToRedisBO = getClient();
		clientRedisService.set(GlobalConstant.REDIS_CLIENT_ID_KEY_PREFIX + oauthClientToRedisBO.getClientId(), oauthClientToRedisBO);

		String firstActiveProfile = SpringUtil.getFirstActiveProfile();
		if (StringUtil.isNotBlank(firstActiveProfile) && !StringUtil.equalsIgnoreCase(firstActiveProfile, "prod")) {
			// 只有非生产环境才会生成测试token
			buildTestCodeAndToken();
		}
		log.info("=================================预设 Redis 测试数据 End=================================");

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private void buildTestCodeAndToken() {
		String accessTokenKey = GlobalConstant.REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX + GlobalConstantToJunit.ACCESS_TOKEN;
		String refreshTokenKey = GlobalConstant.REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX + GlobalConstantToJunit.REFRESH_TOKEN;
		String codeKey = GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalConstantToJunit.CODE;
		String code2Key = GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalConstantToJunit.CODE2;
		accessTokenRedisService.set(accessTokenKey, getAccessToken(), oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		refreshTokenRedisService.set(refreshTokenKey, getRefreshToken(), oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		codeRedisService.set(codeKey, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());
		codeRedisService.set(code2Key, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());


		/*构建客户端 key 信息 start*/
		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(GlobalConstantToJunit.ACCESS_TOKEN);
		tkeyToken.setRefreshToken(GlobalConstantToJunit.REFRESH_TOKEN);

		OauthUserAttribute oauthUserAttribute = getAccessToken().getUserAttribute();

		OauthUserProfile oauthUserProfile = new OauthUserProfile();
		oauthUserProfile.setUsername(oauthUserAttribute.getUsername());
		oauthUserProfile.setName(oauthUserAttribute.getUsername());
		oauthUserProfile.setId(oauthUserAttribute.getUserId());
		oauthUserProfile.setUserId(oauthUserAttribute.getUserId());

		com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute attribute = new com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute();
		attribute.setUserId(oauthUserAttribute.getUserId());
		attribute.setUsername(oauthUserAttribute.getUsername());
		attribute.setNickname(oauthUserAttribute.getNickname());
		attribute.setRealName(oauthUserAttribute.getRealName());
		attribute.setAvatarUrl(oauthUserAttribute.getAvatarUrl());
		attribute.setEmail(oauthUserAttribute.getEmail());
		attribute.setMobilePhone(oauthUserAttribute.getMobilePhone());
		attribute.setTenantId(oauthUserAttribute.getTenantId());
		attribute.setUserType(oauthUserAttribute.getUserType());
		attribute.setUserAttributeExt(oauthUserAttribute.getUserAttributeExt());

		oauthUserProfile.setUserAttribute(attribute);
		oauthUserProfile.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthUserProfile.setClientId(tkeyProperties.getClientId());
		oauthUserProfile.setIat(1561522123L);
		oauthUserProfile.setExp(1561522123L + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());

		tkeyToken.setAttributes(oauthUserProfile);
		String clientAccessTokenKey = GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + GlobalConstantToJunit.ACCESS_TOKEN;
		tokenRedisService.set(clientAccessTokenKey, tkeyToken, oauthClientProperties.getTokenMaxTimeToLiveInSeconds());

		CACHE_INIT_TKEY_KEY_LIST.add(accessTokenKey);
		CACHE_INIT_TKEY_KEY_LIST.add(refreshTokenKey);
		CACHE_INIT_TKEY_KEY_LIST.add(codeKey);
		CACHE_INIT_TKEY_KEY_LIST.add(code2Key);
		CACHE_INIT_TKEY_KEY_LIST.add(clientAccessTokenKey);
		/*构建客户端 key 信息 end*/
	}

	private OauthClientToRedisBO getClient() {
		OauthClientToRedisBO oauthClientToRedisBO = new OauthClientToRedisBO();
		oauthClientToRedisBO.setId(GlobalConstantToJunit.ID_LONG);
		oauthClientToRedisBO.setClientName("欢迎登录");
		oauthClientToRedisBO.setClientId(tkeyProperties.getClientId());
		oauthClientToRedisBO.setClientSecret(tkeyProperties.getClientSecret());
		oauthClientToRedisBO.setClientUrl("^(http|https)://.*");
		oauthClientToRedisBO.setClientDesc("欢迎登录");
		oauthClientToRedisBO.setLogoUrl("https://www.easyicon.net/api/resizeApi.php?id=1200686&size=32");
		return oauthClientToRedisBO;
	}

	private OauthAccessTokenToRedisBO getAccessToken() {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = new OauthAccessTokenToRedisBO();
		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();

		Map<String, Object> userAttributeExt = new HashMap<>();
		userAttributeExt.put(GlobalConstant.USER_TYPE_ENUM_MAP_KEY, UserTypeEnum.ADMIN.getCode());
		userAttributeExt.put(GlobalConstant.TENANT_ID_MAP_KEY, GlobalConstant.TOP_TENANT_ID);
		oauthUserAttribute.setUserAttributeExt(userAttributeExt);

		oauthUserAttribute.setRealName(GlobalConstantToJunit.USERNAME);
		oauthUserAttribute.setNickname(GlobalConstantToJunit.USERNAME);
		oauthUserAttribute.setEmail(GlobalConstantToJunit.USER_EMAIL);
		oauthUserAttribute.setUserId(GlobalConstantToJunit.USER_ID);
		oauthUserAttribute.setUsername(GlobalConstantToJunit.USERNAME);
		oauthUserAttribute.setTenantId(GlobalConstantToJunit.TOP_TENANT_ID);
		oauthUserAttribute.setUserType(String.valueOf(UserTypeEnum.ADMIN.getCode()));

		oauthAccessTokenToRedisBO.setUserAttribute(oauthUserAttribute);
		oauthAccessTokenToRedisBO.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthAccessTokenToRedisBO.setClientId(tkeyProperties.getClientId());
		oauthAccessTokenToRedisBO.setIat(1561522123L);
		return oauthAccessTokenToRedisBO;
	}

	private OauthRefreshTokenToRedisBO getRefreshToken() {
		OauthRefreshTokenToRedisBO oauthRefreshTokenToRedisBO = new OauthRefreshTokenToRedisBO();
		oauthRefreshTokenToRedisBO.setUserInfoRedisKey(GlobalConstantToJunit.USER_INFO_REDIS_KEY);
		oauthRefreshTokenToRedisBO.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthRefreshTokenToRedisBO.setClientId(tkeyProperties.getClientId());
		oauthRefreshTokenToRedisBO.setIat(1561522123L);
		return oauthRefreshTokenToRedisBO;

	}

	private OauthCodeToRedisBO getCode() {
		OauthCodeToRedisBO oauthCodeToRedisBO = new OauthCodeToRedisBO();
		oauthCodeToRedisBO.setTgc(GlobalConstantToJunit.TGC);
		oauthCodeToRedisBO.setUserInfoRedisKey(GlobalConstantToJunit.USER_INFO_REDIS_KEY);
		oauthCodeToRedisBO.setClientId(tkeyProperties.getClientId());
		oauthCodeToRedisBO.setIat(1561522123L);

		String userAgent = GlobalConstantToJunit.USER_AGENT;
		OauthRequestInfoBO oauthRequestInfoBO = new OauthRequestInfoBO();
		oauthRequestInfoBO.setRequestUrl("http://www.gitnavi.com");
		oauthRequestInfoBO.setIpAddress(GlobalConstantToJunit.IP);
		oauthRequestInfoBO.setUserAgent(userAgent);
		oauthRequestInfoBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		oauthRequestInfoBO.setOsName(UserAgentUtil.getOs(userAgent));
		oauthRequestInfoBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
		oauthRequestInfoBO.setBrowserLocale("zh");
		oauthCodeToRedisBO.setOauthRequestInfoBO(oauthRequestInfoBO);

		return oauthCodeToRedisBO;
	}

	//=====================================私有方法  end=====================================
}
