package com.cdk8s.sculptor.pojo.dto.param.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Setter
@Getter
@ToString(callSuper = true)
public class OauthClientCodeRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "code 不能为空")
	private String code;

	@NotBlank(message = "redirectUri 不能为空")
	private String redirectUri;


}
