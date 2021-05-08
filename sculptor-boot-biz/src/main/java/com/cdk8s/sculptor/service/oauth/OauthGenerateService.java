/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthGenerateService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.oauth;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.properties.OauthProperties;
import com.cdk8s.sculptor.util.NumericGeneratorUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthGenerateService {

	@Autowired
	private OauthProperties oauthProperties;

	//=====================================业务处理 start=====================================

	public OauthToken generateOauthTokenInfoBO(boolean needIncludeRefreshToken) {
		OauthToken oauthToken = new OauthToken();
		oauthToken.setAccessToken(generateAccessToken());
		if (needIncludeRefreshToken) {
			oauthToken.setRefreshToken(generateRefreshToken());
		}
		oauthToken.setTokenType(GlobalConstant.OAUTH_TOKEN_TYPE);
		oauthToken.setExpiresIn(oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());

		return oauthToken;
	}

	public String generateUserInfoRedisKey(String userId) {
		return GlobalConstant.REDIS_OAUTH_USER_INFO_KEY_PREFIX + userId;
	}

	public String generateTgc() {
		return getUniqueTicket(GlobalConstant.OAUTH_TGC_PREFIX);
	}

	public String generateCode() {
		return getUniqueTicket(GlobalConstant.OAUTH_CODE_PREFIX);
	}

	public String generateAccessToken() {
		return getUniqueTicket(GlobalConstant.OAUTH_ACCESS_TOKEN_PREFIX);
	}

	public String generateRefreshToken() {
		return getUniqueTicket(GlobalConstant.OAUTH_REFRESH_TOKEN_PREFIX);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private String getUniqueTicket(String prefix) {
		// 组成结构：前缀-节点编号+计算器数-随机数
		return prefix + oauthProperties.getNodeNumber() + NumericGeneratorUtil.getNumber() + "-" + RandomUtil.randomAlphanumeric(32);
	}

	//=====================================私有方法  end=====================================

}
