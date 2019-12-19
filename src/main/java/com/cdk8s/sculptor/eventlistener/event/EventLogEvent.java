package com.cdk8s.sculptor.eventlistener.event;

import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventLogEvent extends ApplicationEvent {

	private SysEventLogCreateServiceBO serviceBO;

	public EventLogEvent(Object source, SysEventLogCreateServiceBO serviceBO) {
		super(source);
		this.serviceBO = serviceBO;
	}
}
