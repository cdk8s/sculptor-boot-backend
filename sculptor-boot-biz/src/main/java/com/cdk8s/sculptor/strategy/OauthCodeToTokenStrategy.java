/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthCodeToTokenStrategy.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthCodeToRedisBO;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthUserInfoToRedisBO;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.service.oauth.OauthCheckParamService;
import com.cdk8s.sculptor.service.oauth.OauthGenerateService;
import com.cdk8s.sculptor.service.oauth.OauthSaveService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalConstant.OAUTH_CODE_GRANT_TYPE)
public class OauthCodeToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private StringRedisService<String, OauthCodeToRedisBO> codeRedisService;

	@Autowired
	private StringRedisService<String, OauthUserInfoToRedisBO> userInfoRedisService;

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkOauthTokenParam(oauthTokenParam);

		OauthCodeToRedisBO oauthCodeToRedisBO = codeRedisService.get(GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + oauthTokenParam.getCode());
		if (null == oauthCodeToRedisBO) {
			throw new OauthApiException("code 无效");
		}

		if (StringUtil.notEqualsIgnoreCase(oauthCodeToRedisBO.getClientId(), oauthTokenParam.getClientId())) {
			throw new OauthApiException("该 code 与当前请求的 client_id 参数不匹配");
		}

		String userInfoRedisKey = oauthCodeToRedisBO.getUserInfoRedisKey();
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
		String userInfoRedisKey = oauthTokenStrategyHandleBO.getUserInfoRedisKey();
		OauthUserInfoToRedisBO oauthUserInfoToRedisBO = userInfoRedisService.get(userInfoRedisKey);

		OauthToken oauthToken = oauthGenerateService.generateOauthTokenInfoBO(true);

		OauthUserAttribute userAttribute = oauthUserInfoToRedisBO.getUserAttribute();
		oauthSaveService.saveAccessToken(oauthToken.getAccessToken(), userAttribute, oauthTokenParam.getClientId(), GlobalConstant.OAUTH_CODE_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthToken.getRefreshToken(), userAttribute, oauthTokenParam.getClientId(), GlobalConstant.OAUTH_CODE_GRANT_TYPE);

		// code 只能被用一次，这里用完会立马被删除
		codeRedisService.delete(GlobalConstant.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + oauthTokenParam.getCode());
		return oauthToken;
	}

	//=====================================业务处理 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
