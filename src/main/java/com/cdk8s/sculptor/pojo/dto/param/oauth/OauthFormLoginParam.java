package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthFormLoginParam extends OauthAuthorizeParam {
	private String username;
	private String password;
	private String deviceId;
	private String validateCode;
	private Boolean boolIsRememberMe;

}
