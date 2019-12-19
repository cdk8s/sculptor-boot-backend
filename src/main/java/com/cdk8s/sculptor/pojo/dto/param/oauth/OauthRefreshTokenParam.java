package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthRefreshTokenParam {
	private String grantType;
	private String refreshToken;
	private String clientId;
	private String clientSecret;
}
