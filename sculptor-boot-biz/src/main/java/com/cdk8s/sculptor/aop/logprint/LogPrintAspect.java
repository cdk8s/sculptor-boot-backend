/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LogPrintAspect.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.aop.logprint;

import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.LOWEST_PRECEDENCE)
@Aspect
@Component
@Slf4j
public class LogPrintAspect {

	@Pointcut("execution(public * com.cdk8s.sculptor..multiapi..*.*(..)) || execution(public * com.cdk8s.sculptor..controller..*.*(..)) || execution(public * com.cdk8s.sculptor..web..*.*(..))")
	private void pointcut() {
	}

	@Before(value = "pointcut()")
	public void before(JoinPoint joinPoint) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null == requestAttributes) {
			return;
		}

		HttpServletRequest request = requestAttributes.getRequest();
		if (log.isDebugEnabled()) {
			log.info("----------------------begin----------------------");
		}
		try {
			if (log.isDebugEnabled()) {

				log.info("----------------------url: {}", request.getRequestURL().toString());
				log.info("----------------------httpMethod: {}", request.getMethod());
				log.info("----------------------method: {}", joinPoint.getSignature());
				log.info("----------------------urlQueryString: {}", request.getQueryString());

				Object[] args = joinPoint.getArgs();
				List<Object> argsList = new ArrayList<>();
				if (args.length > 0) {
					for (Object obj : args) {
						if (obj instanceof MultipartFile) {
							continue;
						} else if (obj instanceof StandardMultipartHttpServletRequest) {
							continue;
						} else if (obj instanceof HttpServletRequest) {
							continue;
						} else if (obj instanceof HttpServletResponse) {
							continue;
						} else if (obj instanceof Model) {
							continue;
						}
						argsList.add(obj);
					}
				}

				// log.info("----------------------Request Body: {}", JSONObject.toJSONString(argsList));
				log.info("----------------------Request Body: {}", JsonUtil.toJson(argsList));
			}
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
		}
	}

	@AfterReturning(returning = "obj", pointcut = "pointcut()")
	public void after(Object obj) {
		try {
			if (log.isDebugEnabled()) {
				String result = null;
				if (null != obj) {
					// result = JSONObject.toJSONString(obj);
					result = JsonUtil.toJson(obj);
					if (result.length() > 1000) {
						result = StringUtil.substring(result, 0, 1000);
					}
				}
				log.info("----------------------Response: {}", result);
			}
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
		}
		if (log.isDebugEnabled()) {
			log.info("----------------------end----------------------");
		}
	}
}
