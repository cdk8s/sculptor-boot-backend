package com.cdk8s.sculptor.strategy;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthRefreshTokenToRedisBO;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthUserInfoToRedisBO;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.service.oauth.OauthCheckParamService;
import com.cdk8s.sculptor.service.oauth.OauthGenerateService;
import com.cdk8s.sculptor.service.oauth.OauthSaveService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE)
public class OauthRefreshTokenToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private StringRedisService<String, OauthUserInfoToRedisBO> userInfoRedisService;

	@Autowired
	private StringRedisService<String, OauthRefreshTokenToRedisBO> refreshTokenRedisService;

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthSaveService oauthSaveService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkOauthRefreshTokenParam(oauthTokenParam);

		OauthRefreshTokenToRedisBO oauthRefreshTokenToRedisBO = refreshTokenRedisService.get(GlobalConstant.REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX + oauthTokenParam.getRefreshToken());
		if (null == oauthRefreshTokenToRedisBO) {
			throw new OauthApiException("refresh_token 已失效");
		}

		if (StringUtil.notEqualsIgnoreCase(oauthRefreshTokenToRedisBO.getClientId(), oauthTokenParam.getClientId())) {
			throw new OauthApiException("该 refresh_token 与当前请求的 client_id 参数不匹配");
		}

		String userInfoRedisKey = oauthRefreshTokenToRedisBO.getUserInfoRedisKey();
		if (null != userInfoRedisKey) {
			OauthUserInfoToRedisBO oauthUserInfoToRedisBO = userInfoRedisService.get(userInfoRedisKey);
			if (null == oauthUserInfoToRedisBO) {
				throw new OauthApiException("未找到该用户信息");
			}

			oauthTokenStrategyHandleBO.setUserAttribute(oauthUserInfoToRedisBO.getUserAttribute());
			oauthTokenStrategyHandleBO.setUserInfoRedisKey(userInfoRedisKey);
		}
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {

		OauthToken oauthTokenInfoByRefreshTokenBO = oauthGenerateService.generateOauthTokenInfoBO(false);

		if (null != oauthTokenStrategyHandleBO.getUserAttribute()) {
			oauthSaveService.saveAccessToken(oauthTokenInfoByRefreshTokenBO.getAccessToken(), oauthTokenStrategyHandleBO.getUserAttribute(), oauthTokenParam.getClientId(), GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE);
		} else {
			oauthSaveService.saveAccessToken(oauthTokenInfoByRefreshTokenBO.getAccessToken(), null, oauthTokenParam.getClientId(), GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE);
		}

		return oauthTokenInfoByRefreshTokenBO;
	}

	//=====================================业务处理 end=====================================

}
