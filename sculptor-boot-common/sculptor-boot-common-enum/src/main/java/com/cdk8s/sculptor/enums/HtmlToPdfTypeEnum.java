/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：HtmlToPdfTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import com.cdk8s.sculptor.util.RandomUtil;

import java.util.HashMap;
import java.util.Map;

public enum HtmlToPdfTypeEnum implements BasicEnum {
	THYMELEAF(1, "thymeleafHtmlToPdfStrategy"),
	FREEMARKER(2, "freemarkerHtmlToPdfStrategy");

	private Integer code;
	private String description;

	HtmlToPdfTypeEnum(final Integer code, final String description) {
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

	public static HtmlToPdfTypeEnum getRandomEnum() {
		HtmlToPdfTypeEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static HtmlToPdfTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		HtmlToPdfTypeEnum[] allEnums = values();
		for (HtmlToPdfTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		HtmlToPdfTypeEnum[] allEnums = values();
		for (HtmlToPdfTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		HtmlToPdfTypeEnum[] allEnums = values();
		for (HtmlToPdfTypeEnum enumObject : allEnums) {
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
		HtmlToPdfTypeEnum[] allEnums = values();
		for (HtmlToPdfTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
