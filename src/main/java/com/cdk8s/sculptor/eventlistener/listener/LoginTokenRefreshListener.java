package com.cdk8s.sculptor.eventlistener.listener;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.eventlistener.event.LoginTokenRefreshEvent;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginTokenRefreshListener {

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	// =====================================业务 start=====================================

	@Async
	@EventListener(LoginTokenRefreshEvent.class)
	public void service(LoginTokenRefreshEvent event) {
		TkeyToken tkeyToken = event.getLoginTokenRefreshBO().getTkeyToken();
		tokenRedisService.set(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + tkeyToken.getAccessToken(), tkeyToken, oauthClientProperties.getTokenMaxTimeToLiveInSeconds());
	}

	// =====================================业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================
}
