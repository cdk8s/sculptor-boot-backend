package com.cdk8s.sculptor.pojo.bo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class LogoutTokenRefreshBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private String accessToken;

}
