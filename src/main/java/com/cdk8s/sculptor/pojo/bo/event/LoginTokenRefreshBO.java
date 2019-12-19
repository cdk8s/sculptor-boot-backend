package com.cdk8s.sculptor.pojo.bo.event;

import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class LoginTokenRefreshBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private TkeyToken tkeyToken;

}
