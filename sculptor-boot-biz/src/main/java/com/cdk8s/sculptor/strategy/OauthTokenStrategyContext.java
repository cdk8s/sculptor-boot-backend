/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthTokenStrategyContext.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy;

import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class OauthTokenStrategyContext {


	private final Map<String, OauthTokenStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public OauthTokenStrategyContext(Map<String, OauthTokenStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	public void checkParam(String beanName, OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		if (StringUtil.isNotBlank(beanName)) {
			strategyMap.get(beanName).checkParam(oauthTokenParam, oauthTokenStrategyHandleBO);
		}
	}

	public OauthToken generateOauthTokenInfo(String beanName, OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).handle(oauthTokenParam, oauthTokenStrategyHandleBO);
		}
		return null;
	}


}
