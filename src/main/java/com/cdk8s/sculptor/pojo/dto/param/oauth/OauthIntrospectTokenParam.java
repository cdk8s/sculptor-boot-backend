package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class OauthIntrospectTokenParam extends OauthClientParam {

	private String token;
	private String tokenTypeHint;

}
