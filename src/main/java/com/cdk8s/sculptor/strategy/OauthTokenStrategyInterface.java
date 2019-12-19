package com.cdk8s.sculptor.strategy;


import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;

public interface OauthTokenStrategyInterface {

	/**
	 * 检查请求参数
	 */
	void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO);

	/**
	 * 生成 Token
	 */
	OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO);

}
