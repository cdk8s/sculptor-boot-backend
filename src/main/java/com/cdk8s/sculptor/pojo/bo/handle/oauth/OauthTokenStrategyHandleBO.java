package com.cdk8s.sculptor.pojo.bo.handle.oauth;

import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OauthTokenStrategyHandleBO {
	private String userInfoRedisKey;
	private OauthUserAttribute userAttribute;
}
