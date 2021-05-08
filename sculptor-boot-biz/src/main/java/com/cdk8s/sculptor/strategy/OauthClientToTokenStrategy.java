/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthClientToTokenStrategy.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.service.oauth.OauthCheckParamService;
import com.cdk8s.sculptor.service.oauth.OauthGenerateService;
import com.cdk8s.sculptor.service.oauth.OauthSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalConstant.OAUTH_CLIENT_GRANT_TYPE)
public class OauthClientToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkClientIdAndClientSecretParam(oauthTokenParam.getClientId(), oauthTokenParam.getClientSecret(), true);
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		OauthToken oauthTokenInfoByClientBO = oauthGenerateService.generateOauthTokenInfoBO(true);

		oauthSaveService.saveAccessToken(oauthTokenInfoByClientBO.getAccessToken(), null, oauthTokenParam.getClientId(), GlobalConstant.OAUTH_CLIENT_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthTokenInfoByClientBO.getRefreshToken(), null, oauthTokenParam.getClientId(), GlobalConstant.OAUTH_CLIENT_GRANT_TYPE);

		return oauthTokenInfoByClientBO;
	}

	//=====================================业务处理 end=====================================

}
