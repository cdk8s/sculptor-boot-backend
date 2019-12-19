package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.event.LogoutTokenRefreshBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogoutTokenRefreshEvent extends ApplicationEvent {

	private LogoutTokenRefreshBO logoutTokenRefreshBO;

	public LogoutTokenRefreshEvent(Object source, LogoutTokenRefreshBO logoutTokenRefreshBO) {
		super(source);
		this.logoutTokenRefreshBO = logoutTokenRefreshBO;
	}
}
