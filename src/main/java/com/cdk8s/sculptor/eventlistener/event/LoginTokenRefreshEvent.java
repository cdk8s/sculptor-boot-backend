package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.event.LoginTokenRefreshBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginTokenRefreshEvent extends ApplicationEvent {

	private LoginTokenRefreshBO loginTokenRefreshBO;

	public LoginTokenRefreshEvent(Object source, LoginTokenRefreshBO loginTokenRefreshBO) {
		super(source);
		this.loginTokenRefreshBO = loginTokenRefreshBO;
	}
}
