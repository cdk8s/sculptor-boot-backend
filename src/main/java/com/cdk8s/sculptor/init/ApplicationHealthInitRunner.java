package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.actuator.CustomHttpApiServerHealthEndpoint;
import com.cdk8s.sculptor.properties.InitParamProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Order(5)
@Component
public class ApplicationHealthInitRunner implements ApplicationRunner {

	@Autowired
	private CustomHttpApiServerHealthEndpoint customHttpApiServerHealthEndpoint;

	@Autowired
	private ReactiveHealthIndicator redisReactiveHealthIndicator;

	@Autowired
	private HealthIndicator dbHealthIndicator;

	@Autowired
	private InitParamProperties initParamProperties;

	//=====================================业务处理 start=====================================

	@Override
	public void run(ApplicationArguments args) {

		if (initParamProperties.getStartHttpHealthEnabled()) {
			http();
		}
		if (initParamProperties.getStartRedisHealthEnabled()) {
			redis();
		}
		if (initParamProperties.getStartDbHealthEnabled()) {
			db();
		}
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private void http() {
		Health customUPMSHealth = customHttpApiServerHealthEndpoint.health();
		if (customUPMSHealth.getStatus().equals(Status.DOWN)) {
			log.error("启动请求 http 接口失败");
		}
	}

	private void redis() {
		Mono<Health> redisHealth = redisReactiveHealthIndicator.health();
		redisHealth.subscribe(h -> {
			if (h.getStatus().equals(Status.DOWN)) {
				log.error("启动连接 Redis 失败");
			}
		});
	}

	private void db() {
		Health dbHealth = dbHealthIndicator.health();
		if (dbHealth.getStatus().equals(Status.DOWN)) {
			log.error("启动连接 DB 失败");
		}
	}

	//=====================================私有方法  end=====================================

}
