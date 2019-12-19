package com.cdk8s.sculptor.aop.eventlog;


import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.eventlistener.event.EventLogEvent;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Order(5)
@Aspect
@Component
@Slf4j
public class EventLogAspect {

	@Autowired
	private ApplicationContext applicationContext;

	@SneakyThrows
	@Around("@annotation(eventLog)")
	public Object around(ProceedingJoinPoint point, EventLog eventLog) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("EventLogAspect 类名:<{}>, 方法:<{}>", strClassName, strMethodName);

		SysEventLogCreateServiceBO serviceBO = buildEventLogServiceBO();

		Object[] bodyArgs = point.getArgs();
		if (null != bodyArgs && bodyArgs.length > 0) {
			// 获取 body 参数
			serviceBO.setRequestParam(JsonUtil.toJson(bodyArgs));
		}

		serviceBO.setMessage(eventLog.message());
		serviceBO.setOperateTypeEnum(eventLog.operateType());
		long startTime = DatetimeUtil.currentEpochMilli();

		Object result = point.proceed();

		long endTime = DatetimeUtil.currentEpochMilli();
		serviceBO.setExecuteTime(endTime - startTime);
		serviceBO.setBoolExecuteSuccessEnum(BooleanEnum.YES.getCode());
		applicationContext.publishEvent(new EventLogEvent(this, serviceBO));
		return result;
	}


	@SneakyThrows
	private SysEventLogCreateServiceBO buildEventLogServiceBO() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		SysEventLogCreateServiceBO serviceBO = new SysEventLogCreateServiceBO();
		serviceBO.setRequestDate(DatetimeUtil.currentEpochMilli());
		String userAgent = request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);
		serviceBO.setId(GenerateIdUtil.getId());
		serviceBO.setUserId(UserInfoContext.getCurrentUserId());
		serviceBO.setUsername(UserInfoContext.getCurrentUsername());
		serviceBO.setIpAddress(IPUtil.getIp(request));
		serviceBO.setRequestUrl(URLUtil.getPath(request.getRequestURI()));
		serviceBO.setRequestMethod(request.getMethod());
		serviceBO.setUserAgent(request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT));
		serviceBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		serviceBO.setOsName(UserAgentUtil.getOs(userAgent));
		serviceBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
		serviceBO.setBrowserLocale(request.getLocale().getLanguage());

		String formParam = HttpUtil.toParams(request.getParameterMap());
		if (StringUtil.isNotBlank(formParam)) {
			// 获取 form 参数
			serviceBO.setRequestParam(formParam);
		}

		return serviceBO;
	}

}
