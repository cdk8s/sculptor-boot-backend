/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ApplicationEchoBeanInitRunner.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.util.SpringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.servlet.AbstractFilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Order(20)
@Component
public class ApplicationEchoBeanInitRunner implements ApplicationRunner {

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {

		ApplicationContext applicationContext = SpringUtil.getApplicationContext();
		if (applicationContext.getParent() == null) {
			log.info("=================================Spring 容器中的 Bean start=================================");

			List<String> abstractFilterRegistrationBeanList = new ArrayList<>();
			List<String> servletFilterBeanList = new ArrayList<>();
			List<String> handlerInterceptorBeanList = new ArrayList<>();
			List<String> aopInterceptorBeanList = new ArrayList<>();
			List<String> aopAspectBeanList = new ArrayList<>();
			List<String> otherBeanList = new ArrayList<>();

			for (String instanceBeanName : applicationContext.getBeanDefinitionNames()) {
				if (instanceBeanName.equalsIgnoreCase("ureport.props")) {
					// 因为抽象类的 bean 是无法被 getBean 出来的，不然会报：Error creating bean with name 'ureport.props': Bean definition is abstract
					// 所以这里需要做判断
					continue;
				}
				Object beanObject = applicationContext.getBean(instanceBeanName);
				if (beanObject instanceof AbstractFilterRegistrationBean) {
					abstractFilterRegistrationBeanList.add(instanceBeanName);
				} else if (beanObject instanceof javax.servlet.Filter) {
					servletFilterBeanList.add(instanceBeanName);
				} else if (beanObject instanceof HandlerInterceptor) {
					handlerInterceptorBeanList.add(instanceBeanName);
				} else if (beanObject instanceof org.aopalliance.intercept.Interceptor) {
					aopInterceptorBeanList.add(instanceBeanName);
				} else {
					Annotation[] annotations = applicationContext.getBean(instanceBeanName).getClass().getAnnotations();
					if (annotations.length != 0) {
						for (Annotation annotation : annotations) {
							if (annotation.annotationType().equals(Aspect.class)) {
								aopAspectBeanList.add(instanceBeanName);
							}
						}
					} else {
						otherBeanList.add(instanceBeanName);
					}
				}
			}

			for (String instanceBeanName : abstractFilterRegistrationBeanList) {
				log.info("=================================abstractFilterRegistrationBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}

			log.info("==================================================================");

			for (String instanceBeanName : servletFilterBeanList) {
				log.info("=================================servletFilterBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}

			log.info("==================================================================");

			for (String instanceBeanName : handlerInterceptorBeanList) {
				log.info("=================================handlerInterceptorBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}

			log.info("==================================================================");

			for (String instanceBeanName : aopInterceptorBeanList) {
				log.info("=================================aopInterceptorBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}

			log.info("==================================================================");

			for (String instanceBeanName : aopAspectBeanList) {
				log.info("=================================aopAspectBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}

			log.info("==================================================================");

			for (String instanceBeanName : otherBeanList) {
				log.info("=================================otherBeanList = {}, 单例 = {}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}


			log.info("=================================Spring 容器中的 Bean end=================================");

		}
	}
}
