/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PayTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import com.cdk8s.sculptor.util.RandomUtil;

import java.util.HashMap;
import java.util.Map;

public enum PayTypeEnum implements BasicEnum {
	WXPAY(1, "wxpayStrategy"),
	ALIPAY(2, "alipayStrategy");

	private Integer code;
	private String description;

	PayTypeEnum(final Integer code, final String description) {
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

	public static PayTypeEnum getRandomEnum() {
		PayTypeEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static PayTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		PayTypeEnum[] allEnums = values();
		for (PayTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		PayTypeEnum[] allEnums = values();
		for (PayTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		PayTypeEnum[] allEnums = values();
		for (PayTypeEnum enumObject : allEnums) {
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
		PayTypeEnum[] allEnums = values();
		for (PayTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
