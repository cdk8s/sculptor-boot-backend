/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RegisterOriginEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public enum RegisterOriginEnum implements BasicEnum {
	WEB(1, "WEB方式"),
	ANDROID(2, "安卓APP"),
	IOS(3, "苹果APP"),
	H5(4, "H5"),
	WEIXIN_MINI_PROGRAM(5, "微信小程序"),
	WEIXIN_OFFICIAL_ACCOUNT(6, "微信公众号");

	private Integer code;
	private String description;

	RegisterOriginEnum(final Integer code, final String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		RegisterOriginEnum[] allEnums = values();
		for (RegisterOriginEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static RegisterOriginEnum getRandomEnum() {
		RegisterOriginEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static RegisterOriginEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RegisterOriginEnum[] allEnums = values();
		for (RegisterOriginEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RegisterOriginEnum[] allEnums = values();
		for (RegisterOriginEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.getDescription();
			}
		}
		return null;
	}

	public static String getNameByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RegisterOriginEnum[] allEnums = values();
		for (RegisterOriginEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}

	public static RegisterOriginEnum getRegisterOriginEnumCode(HttpServletRequest request) {
		String userAgent = UserAgentUtil.getUserAgent(request);
		if (StringUtil.containsIgnoreCase(userAgent, "miniProgram")) {
			return RegisterOriginEnum.WEIXIN_MINI_PROGRAM;
		} else if (StringUtil.containsIgnoreCase(userAgent, "MicroMessenger")) {
			return RegisterOriginEnum.WEIXIN_OFFICIAL_ACCOUNT;
		} else if (StringUtil.containsIgnoreCase(userAgent, "Android")) {
			return RegisterOriginEnum.ANDROID;
		} else if (StringUtil.containsAny(userAgent.toLowerCase(), "iphone", "ipad", "ipod", "ios")) {
			return RegisterOriginEnum.IOS;
		}
		return RegisterOriginEnum.WEB;
	}
}
