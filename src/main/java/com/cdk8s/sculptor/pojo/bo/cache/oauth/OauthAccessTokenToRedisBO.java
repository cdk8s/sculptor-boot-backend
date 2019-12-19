package com.cdk8s.sculptor.pojo.bo.cache.oauth;

import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
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
public class OauthAccessTokenToRedisBO extends OauthTokenToRedisBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private OauthUserAttribute userAttribute;


}
