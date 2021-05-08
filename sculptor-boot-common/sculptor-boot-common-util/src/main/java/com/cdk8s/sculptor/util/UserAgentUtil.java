/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UserAgentUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import cn.hutool.http.useragent.UserAgentParser;
import com.cdk8s.sculptor.constant.GlobalConstant;

import javax.servlet.http.HttpServletRequest;


public class UserAgentUtil {

	private static final String JSON_TYPE = "application/json";
	private static final String X_TYPE = "XMLHttpRequest";

	public static boolean isMobile(HttpServletRequest request) {
		return isMobile(getUserAgent(request));
	}

	public static boolean isMobile(String userAgentString) {
		return UserAgentParser.parse(userAgentString).isMobile();
	}

	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);
	}


	public static boolean isJsonRequest(HttpServletRequest request) {
		String contentTypeHeader = request.getHeader("Content-Type");
		String acceptHeader = request.getHeader("Accept");
		String xRequestedWith = request.getHeader("X-Requested-With");
		String userAgent = request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);

		boolean checkIsJson = (contentTypeHeader != null && StringUtil.containsIgnoreCase(contentTypeHeader, JSON_TYPE))
				|| (acceptHeader != null && StringUtil.containsIgnoreCase(acceptHeader, JSON_TYPE))
				|| StringUtil.equalsIgnoreCase(xRequestedWith, X_TYPE)
				|| StringUtil.containsIgnoreCase(userAgent, "okhttp")
				|| StringUtil.containsIgnoreCase(userAgent, "httpclient")
				|| StringUtil.containsIgnoreCase(userAgent, "miniProgram")
				|| UserAgentUtil.isMobile(userAgent)
				|| StringUtil.containsIgnoreCase(userAgent, "MicroMessenger");
		return checkIsJson;
	}

	public static String getBrowser(String userAgentString) {
		return UserAgentParser.parse(userAgentString).getBrowser().getName();
	}

	public static String getOs(String userAgentString) {
		return UserAgentParser.parse(userAgentString).getOs().getName();
	}

	public static String getPlatform(String userAgentString) {
		return UserAgentParser.parse(userAgentString).getPlatform().getName();
	}
}
