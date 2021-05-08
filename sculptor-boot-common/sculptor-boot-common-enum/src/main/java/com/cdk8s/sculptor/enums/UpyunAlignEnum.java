/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UpyunAlignEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

public enum UpyunAlignEnum implements BasicEnum {
	NORTHWEST(1, "northwest"),
	NORTH(2, "north"),
	NORTHEAST(3, "northeast"),
	WEST(4, "west"),
	CENTER(5, "center"),
	EAST(6, "east"),
	SOUTHWEST(7, "southwest"),
	SOUTH(8, "south"),
	SOUTHEAST(9, "southeast");

	private Integer code;
	private String description;

	UpyunAlignEnum(final Integer code, final String description) {
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
		UpyunAlignEnum[] allEnums = values();
		for (UpyunAlignEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static UpyunAlignEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		UpyunAlignEnum[] allEnums = values();
		for (UpyunAlignEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static UpyunAlignEnum getEnumByDescription(String description) {
		if (null == description) {
			return null;
		}
		UpyunAlignEnum[] allEnums = values();
		for (UpyunAlignEnum enumObject : allEnums) {
			if (enumObject.getDescription().equals(description)) {
				return enumObject;
			}
		}
		return null;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		UpyunAlignEnum[] allEnums = values();
		for (UpyunAlignEnum enumObject : allEnums) {
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
		UpyunAlignEnum[] allEnums = values();
		for (UpyunAlignEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
