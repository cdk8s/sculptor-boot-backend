package com.cdk8s.sculptor.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class RedissonConfig {

	@Value("${spring.redis.password}")
	private String redisPassword;

	@Bean
	public RedissonClient redisson() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson/redisson-single.yml").getURL());
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setPassword(redisPassword);
		return Redisson.create(config);
	}
}
