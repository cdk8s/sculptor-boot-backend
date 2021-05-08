/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthUserIdToTokenStrategy.java
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

/**
 * 根据用户 ID 生成 token
 * 只能用于内部明确已经存在该用户、且已认证过的情况下
 */
@Slf4j
@Service(GlobalConstant.OAUTH_USER_ID_GRANT_TYPE)
public class OauthUserIdToTokenStrategy implements OauthTokenStrategyInterface {

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

		OauthUserAttribute oauthUserAttribute;
		try {
			oauthUserAttribute = oauthLoginApiService.requestLoginByUserId(oauthTokenParam.getUserId());
		} catch (Exception e) {
			throw new OauthApiException(e.getMessage());
		}

		if (null == oauthUserAttribute || StringUtil.isBlank(oauthUserAttribute.getUserId())) {
			throw new OauthApiException("登录失败，未找到该用户");
		}

		String userInfoRedisKey = oauthGenerateService.generateUserInfoRedisKey(oauthUserAttribute.getUserId());
		oauthSaveService.saveUserInfoKeyToRedis(userInfoRedisKey, oauthUserAttribute);

		oauthTokenStrategyHandleBO.setUserAttribute(oauthUserAttribute);

		OauthUserAttribute userAttribute = oauthTokenStrategyHandleBO.getUserAttribute();

		OauthToken oauthTokenInfoByClientBO = oauthGenerateService.generateOauthTokenInfoBO(true);

		oauthSaveService.saveAccessToken(oauthTokenInfoByClientBO.getAccessToken(), userAttribute, clientId, oauthTokenParam.getGrantType());
		oauthSaveService.saveRefreshToken(oauthTokenInfoByClientBO.getRefreshToken(), userAttribute, clientId, oauthTokenParam.getGrantType());

		return oauthTokenInfoByClientBO;
	}

	//=====================================业务处理 end=====================================

}
