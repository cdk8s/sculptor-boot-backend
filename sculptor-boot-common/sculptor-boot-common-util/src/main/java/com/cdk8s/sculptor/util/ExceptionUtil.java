/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExceptionUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 关于异常的工具类.
 */
@Slf4j
public final class ExceptionUtil {

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		return ExceptionUtils.getStackTrace(e);
	}

	public static void printStackTraceAsString(Throwable e) {
		if (e == null) {
			return;
		}
		log.error("------zch------错误信息 <{}>", ExceptionUtils.getStackTrace(e));
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getRootCauseStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}

		String[] rootCauseStackTrace = ExceptionUtils.getRootCauseStackTrace(e);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		if (rootCauseStackTrace.length < 5) {
			for (int i = 0; i < rootCauseStackTrace.length; i++) {
				stringBuilder.append(rootCauseStackTrace[i]);
				stringBuilder.append("\n");
			}
			return stringBuilder.toString();
		} else {
			for (int i = 0; i < 5; i++) {
				stringBuilder.append(rootCauseStackTrace[i]);
				stringBuilder.append("\n");
			}
			return stringBuilder.toString();
		}
	}

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

}
