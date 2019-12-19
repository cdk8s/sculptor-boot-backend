package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginLogEvent extends ApplicationEvent {

	private SysLoginLogCreateServiceBO serviceBO;

	public LoginLogEvent(Object source, SysLoginLogCreateServiceBO serviceBO) {
		super(source);
		this.serviceBO = serviceBO;
	}
}
