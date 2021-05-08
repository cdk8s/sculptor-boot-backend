/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SpringUtil.java
 * 项目名称：sculptor-boot-common-spring
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring bean 工具类
 */
@Component
public final class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	//获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getFirstActiveProfile() {
		String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
		if (ArrayUtils.isEmpty(profiles)) {
			return null;
		}
		return profiles[0];
	}

	/**
	 * 获取对象
	 * 直接写 bean 名称即可，记得一般 bean 都是小写开头，不要直接复制类名过来
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) getApplicationContext().getBean(name);
	}

	/**
	 * 获取对象
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 获取对象
	 */
	public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
		return getApplicationContext().getBean(name, clazz);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 */
	public static boolean containsBean(String name) {
		return getApplicationContext().containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return getApplicationContext().isSingleton(name);
	}

	/**
	 * 获取其类型
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return getApplicationContext().getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 */
	public static String[] getAliases(String name) {
		return getApplicationContext().getAliases(name);
	}

	/**
	 * 获取aop代理对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}
}
