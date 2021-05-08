/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzJobInvokeUtil.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz;


import com.cdk8s.sculptor.pojo.entity.SysJob;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务执行工具
 */
public class QuartzJobInvokeUtil {
	/**
	 * 执行方法
	 */
	public static void invokeMethod(SysJob sysJob) throws Exception {
		String invokeTarget = sysJob.getInvokeTarget();
		String beanName = getBeanName(invokeTarget);
		String methodName = getMethodName(invokeTarget);
		List<Object[]> methodParams = getMethodParams(invokeTarget);

		if (!isValidClassName(beanName)) {
			Object bean = SpringUtil.getBean(beanName);
			invokeMethod(bean, methodName, methodParams);
		} else {
			Object bean = Class.forName(beanName).newInstance();
			invokeMethod(bean, methodName, methodParams);
		}
	}

	/**
	 * 调用任务方法
	 */
	private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (CollectionUtil.isNotEmpty(methodParams)) {
			Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
			method.invoke(bean, getMethodParamsValue(methodParams));
		} else {
			Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}

	/**
	 * 校验是否为为class包名
	 *
	 * @return true是 false否
	 */
	public static boolean isValidClassName(String invokeTarget) {
		return StringUtils.countMatches(invokeTarget, ".") > 1;
	}

	/**
	 * 获取bean名称
	 *
	 * @param invokeTarget 目标字符串
	 */
	public static String getBeanName(String invokeTarget) {
		String beanName = StringUtils.substringBefore(invokeTarget, "(");
		return StringUtils.substringBeforeLast(beanName, ".");
	}

	/**
	 * 获取bean方法
	 *
	 * @param invokeTarget 目标字符串
	 * @return method方法
	 */
	public static String getMethodName(String invokeTarget) {
		String methodName = StringUtils.substringBefore(invokeTarget, "(");
		return StringUtils.substringAfterLast(methodName, ".");
	}

	/**
	 * 获取method方法参数相关列表
	 *
	 * @param invokeTarget 目标字符串
	 * @return method方法相关参数列表
	 */
	public static List<Object[]> getMethodParams(String invokeTarget) {
		String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
		if (StringUtils.isEmpty(methodStr)) {
			return null;
		}
		String[] methodParams = methodStr.split(",");
		List<Object[]> clazz = new LinkedList<>();
		for (int i = 0; i < methodParams.length; i++) {
			String str = StringUtils.trimToEmpty(methodParams[i]);
			// String字符串类型，包含'
			if (StringUtils.contains(str, "\"")) {
				clazz.add(new Object[]{StringUtils.replace(str, "\"", ""), String.class});
			}
			// boolean布尔类型，等于true或者false
			else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
				clazz.add(new Object[]{Boolean.valueOf(str), Boolean.class});
			}
			// long长整形，包含L
			else if (StringUtils.containsIgnoreCase(str, "L")) {
				clazz.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
			}
			// double浮点类型，包含D
			else if (StringUtils.containsIgnoreCase(str, "D")) {
				clazz.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
			}
			// 其他类型归类为整形
			else {
				clazz.add(new Object[]{Integer.valueOf(str), Integer.class});
			}
		}
		return clazz;
	}

	/**
	 * 获取参数类型
	 *
	 * @param methodParams 参数相关列表
	 * @return 参数类型列表
	 */
	public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
		Class<?>[] clazz = new Class<?>[methodParams.size()];
		int index = 0;
		for (Object[] os : methodParams) {
			clazz[index] = (Class<?>) os[1];
			index++;
		}
		return clazz;
	}

	/**
	 * 获取参数值
	 *
	 * @param methodParams 参数相关列表
	 * @return 参数值列表
	 */
	public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
		Object[] clazz = new Object[methodParams.size()];
		int index = 0;
		for (Object[] os : methodParams) {
			clazz[index] = os[0];
			index++;
		}
		return clazz;
	}
}
