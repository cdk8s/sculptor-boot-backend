/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoginOriginEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;
import com.cdk8s.sculptor.util.RandomUtil;

public enum LoginOriginEnum implements BasicEnum {
	WEB(1, "WEB方式"),
	ANDROID(2, "安卓APP"),
	IOS(3, "苹果APP"),
	H5(4, "H5"),
	WEIXIN_MINI_PROGRAM(5, "微信小程序"),
	WEIXIN_OFFICIAL_ACCOUNT(6, "微信公众号");

	private Integer code;
	private String description;

	LoginOriginEnum(final Integer code, final String description) {
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
		LoginOriginEnum[] allEnums = values();
		for (LoginOriginEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static LoginOriginEnum getRandomEnum() {
		LoginOriginEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static LoginOriginEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		LoginOriginEnum[] allEnums = values();
		for (LoginOriginEnum enumObject : allEnums) {
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
		LoginOriginEnum[] allEnums = values();
		for (LoginOriginEnum enumObject : allEnums) {
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
		LoginOriginEnum[] allEnums = values();
		for (LoginOriginEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
