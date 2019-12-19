package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthTokenParam extends OauthClientParam {
	private String grantType;

	private String code;
	private String refreshToken;
	private String redirectUri;

	private String username;
	private String password;
}
