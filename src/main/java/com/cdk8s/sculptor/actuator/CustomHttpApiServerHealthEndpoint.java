package com.cdk8s.sculptor.actuator;

import com.cdk8s.sculptor.util.okhttp.OkHttpResponse;
import com.cdk8s.sculptor.util.okhttp.OkHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 验证对外网络
 */
@Component
public class CustomHttpApiServerHealthEndpoint extends AbstractHealthIndicator {

	@Autowired
	private OkHttpService okHttpService;

	//======================================================

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		OkHttpResponse okHttpResponse = okHttpService.get("https://www.taobao.com");
		if (okHttpResponse.getStatus() == HttpStatus.OK.value()) {
			builder.up();
		} else {
			builder.down();
		}
	}
}
