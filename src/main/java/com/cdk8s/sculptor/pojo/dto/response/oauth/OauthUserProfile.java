package com.cdk8s.sculptor.pojo.dto.response.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OauthUserProfile implements Serializable {

	private static final long serialVersionUID = -1L;

	// root 对象中必须要有一个主键，Spring Security 的 FixedPrincipalExtractor.java 限定了这几个："user", "username","userid", "user_id", "login", "id", "name"
	// 也因为这个场景，所以这里冗余了其他几个属性
	// 客户端需要用到哪个属性作为主键就用哪个，没必要全部搬过去
	private String username;
	private String name;
	private String id;
	private String userId;

	private OauthUserAttribute userAttribute;
	private String grantType;
	private String clientId;

	private Long iat;
	private Long exp;


}
