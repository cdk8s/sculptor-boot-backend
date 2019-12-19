package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthAuthorizeParam {
	private String responseType;
	private String clientId;
	private String redirectUri;
	private String state;
}
