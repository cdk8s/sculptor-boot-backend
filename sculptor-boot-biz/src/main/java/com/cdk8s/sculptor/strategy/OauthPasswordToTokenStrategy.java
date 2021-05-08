/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthPasswordToTokenStrategy.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.service.oauth.OauthCheckParamService;
import com.cdk8s.sculptor.service.oauth.OauthGenerateService;
import com.cdk8s.sculptor.service.oauth.OauthLoginApiService;
import com.cdk8s.sculptor.service.oauth.OauthSaveService;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE)
public class OauthPasswordToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	@Autowired
	private OauthLoginApiService oauthLoginApiService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkClientIdParam(oauthTokenParam.getClientId());
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		String clientId = oauthTokenParam.getClientId();
		String clientSecret = oauthTokenParam.getClientSecret();
		oauthCheckParamService.checkClientIdAndClientSecretParam(clientId, clientSecret, false);
		String username = oauthTokenParam.getUsername();
		String password = oauthTokenParam.getPassword();
		oauthCheckParamService.checkUsernamePasswordParam(username, password);

		// 校验用户名密码
		OauthUserAttribute oauthUserAttribute;
		try {
			oauthUserAttribute = oauthLoginApiService.requestLoginApi(username, password);
		} catch (Exception e) {
			// 密码模式一般用于 app 所以响应结果应该用 json
			throw new OauthApiException(e.getMessage());
		}

		if (null == oauthUserAttribute || StringUtil.isBlank(oauthUserAttribute.getUserId())) {
			throw new OauthApiException("用户名或密码错误");
		}

		String userInfoRedisKey = oauthGenerateService.generateUserInfoRedisKey(oauthUserAttribute.getUserId());
		oauthSaveService.saveUserInfoKeyToRedis(userInfoRedisKey, oauthUserAttribute);

		oauthTokenStrategyHandleBO.setUserAttribute(oauthUserAttribute);

		OauthUserAttribute userAttribute = oauthTokenStrategyHandleBO.getUserAttribute();

		OauthToken oauthTokenInfoByClientBO = oauthGenerateService.generateOauthTokenInfoBO(true);

		oauthSaveService.saveAccessToken(oauthTokenInfoByClientBO.getAccessToken(), userAttribute, clientId, GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthTokenInfoByClientBO.getRefreshToken(), userAttribute, clientId, GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE);

		return oauthTokenInfoByClientBO;
	}

	//=====================================业务处理 end=====================================

}
