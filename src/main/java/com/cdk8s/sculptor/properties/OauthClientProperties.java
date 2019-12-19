package com.cdk8s.sculptor.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "custom.properties.oauth.client")
public class OauthClientProperties {


	private Long tokenMaxTimeToLiveInSeconds = 86400L;

}
