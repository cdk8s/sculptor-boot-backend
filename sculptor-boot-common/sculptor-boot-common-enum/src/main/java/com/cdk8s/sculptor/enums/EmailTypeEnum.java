/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EmailTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import com.cdk8s.sculptor.util.RandomUtil;

import java.util.HashMap;
import java.util.Map;

public enum EmailTypeEnum implements BasicEnum {
	ALIYUN(1, "aliyunEmailStrategy");

	private Integer code;
	private String description;

	EmailTypeEnum(final Integer code, final String description) {
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

	public static EmailTypeEnum getRandomEnum() {
		EmailTypeEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static EmailTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		EmailTypeEnum[] allEnums = values();
		for (EmailTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		EmailTypeEnum[] allEnums = values();
		for (EmailTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		EmailTypeEnum[] allEnums = values();
		for (EmailTypeEnum enumObject : allEnums) {
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
		EmailTypeEnum[] allEnums = values();
		for (EmailTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
