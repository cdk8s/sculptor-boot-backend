package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.*;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.properties.InitParamProperties;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.properties.OauthProperties;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
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


@Slf4j
@Order(10)
@Component
@ConditionalOnProperty(name = "custom.properties.init.start-test-data-init-enabled", havingValue = "true", matchIfMissing = false)
public class ApplicationTestDataInitRunner implements ApplicationRunner {

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

		accessTokenRedisService.set(GlobalConstant.REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX + GlobalConstantToJunit.ACCESS_TOKEN, getAccessToken(), oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		refreshTokenRedisService.set(GlobalConstant.REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX + GlobalConstantToJunit.REFRESH_TOKEN, getRefreshToken(), oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		codeRedisService.set(GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalConstantToJunit.CODE, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());
		codeRedisService.set(GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalConstantToJunit.CODE2, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());

		/*构建客户端 key 信息 start*/
		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(GlobalConstantToJunit.ACCESS_TOKEN);
		tkeyToken.setRefreshToken(GlobalConstantToJunit.REFRESH_TOKEN);

		OauthUserAttribute userAttribute = getAccessToken().getUserAttribute();

		OauthUserProfile oauthUserProfile = new OauthUserProfile();
		oauthUserProfile.setUsername(userAttribute.getUsername());
		oauthUserProfile.setName(userAttribute.getUsername());
		oauthUserProfile.setId(userAttribute.getUserId());
		oauthUserProfile.setUserId(userAttribute.getUserId());

		com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute attribute = new com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute();
		attribute.setEmail(userAttribute.getEmail());
		attribute.setUserId(userAttribute.getUserId());
		attribute.setUsername(userAttribute.getUsername());

		oauthUserProfile.setUserAttribute(attribute);
		oauthUserProfile.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthUserProfile.setClientId(GlobalConstantToJunit.CLIENT_ID);
		oauthUserProfile.setIat(1561522123L);
		oauthUserProfile.setExp(1561522123L + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());

		tkeyToken.setAttributes(oauthUserProfile);
		tokenRedisService.set(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + GlobalConstantToJunit.ACCESS_TOKEN, tkeyToken, oauthClientProperties.getTokenMaxTimeToLiveInSeconds());
		/*构建客户端 key 信息 end*/

		log.info("=================================预设 Redis 测试数据 End=================================");

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private OauthClientToRedisBO getClient() {
		OauthClientToRedisBO oauthClientToRedisBO = new OauthClientToRedisBO();
		oauthClientToRedisBO.setId(GlobalConstantToJunit.ID_LONG);
		oauthClientToRedisBO.setClientName("通用测试系统1");
		oauthClientToRedisBO.setClientId(GlobalConstantToJunit.CLIENT_ID);
		oauthClientToRedisBO.setClientSecret(GlobalConstantToJunit.CLIENT_SECRET);
		oauthClientToRedisBO.setClientUrl("^(http|https)://.*");
		oauthClientToRedisBO.setClientDesc("通用测试系统1");
		oauthClientToRedisBO.setLogoUrl("https://www.easyicon.net/api/resizeApi.php?id=1200686&size=32");
		return oauthClientToRedisBO;
	}

	private OauthAccessTokenToRedisBO getAccessToken() {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = new OauthAccessTokenToRedisBO();
		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setEmail(GlobalConstantToJunit.USER_EMAIL);
		oauthUserAttribute.setUserId(GlobalConstantToJunit.USER_ID);
		oauthUserAttribute.setUsername(GlobalConstantToJunit.USERNAME);

		oauthAccessTokenToRedisBO.setUserAttribute(oauthUserAttribute);
		oauthAccessTokenToRedisBO.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthAccessTokenToRedisBO.setClientId(GlobalConstantToJunit.CLIENT_ID);
		oauthAccessTokenToRedisBO.setIat(1561522123L);
		return oauthAccessTokenToRedisBO;
	}

	private OauthRefreshTokenToRedisBO getRefreshToken() {
		OauthRefreshTokenToRedisBO oauthRefreshTokenToRedisBO = new OauthRefreshTokenToRedisBO();
		oauthRefreshTokenToRedisBO.setUserInfoRedisKey(GlobalConstantToJunit.USER_INFO_REDIS_KEY);
		oauthRefreshTokenToRedisBO.setGrantType(GlobalConstantToJunit.CODE_GRANT_TYPE);
		oauthRefreshTokenToRedisBO.setClientId(GlobalConstantToJunit.CLIENT_ID);
		oauthRefreshTokenToRedisBO.setIat(1561522123L);
		return oauthRefreshTokenToRedisBO;

	}

	private OauthCodeToRedisBO getCode() {
		OauthCodeToRedisBO oauthCodeToRedisBO = new OauthCodeToRedisBO();
		oauthCodeToRedisBO.setTgc(GlobalConstantToJunit.TGC);
		oauthCodeToRedisBO.setUserInfoRedisKey(GlobalConstantToJunit.USER_INFO_REDIS_KEY);
		oauthCodeToRedisBO.setClientId(GlobalConstantToJunit.CLIENT_ID);
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
