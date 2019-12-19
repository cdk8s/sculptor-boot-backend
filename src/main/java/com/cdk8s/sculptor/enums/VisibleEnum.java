package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

public enum VisibleEnum implements BasicEnum {
	DISPLAY(1, "显示"),
	HIDDEN(2, "隐藏");

	private Integer code;
	private String description;

	VisibleEnum(final Integer code, final String description) {
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
		VisibleEnum[] allEnums = values();
		for (VisibleEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static VisibleEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		VisibleEnum[] allEnums = values();
		for (VisibleEnum enumObject : allEnums) {
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
		VisibleEnum[] allEnums = values();
		for (VisibleEnum enumObject : allEnums) {
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
		VisibleEnum[] allEnums = values();
		for (VisibleEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
