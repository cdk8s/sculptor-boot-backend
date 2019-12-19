package com.cdk8s.sculptor.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "custom.properties.init")
public class InitParamProperties {

	private Boolean startRemoveOldAllCacheEnabled = false;
	private Boolean startRemoveOldBizCacheEnabled = false;
	private List<String> bizCacheNames;

	private Boolean startTestDataInitEnabled = false;
	private Boolean swaggerEnabled = false;

	private Boolean startDbHealthEnabled = true;
	private Boolean startRedisHealthEnabled = true;
	private Boolean startHttpHealthEnabled = false;
}
