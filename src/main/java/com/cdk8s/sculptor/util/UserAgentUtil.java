package com.cdk8s.sculptor.util;

import cn.hutool.http.useragent.UserAgentParser;


public class UserAgentUtil {

	public static boolean isMobile(String userAgentString) {
		return UserAgentParser.parse(userAgentString).isMobile();
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
