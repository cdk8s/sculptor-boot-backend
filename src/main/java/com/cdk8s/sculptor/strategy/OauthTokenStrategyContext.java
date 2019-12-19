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
