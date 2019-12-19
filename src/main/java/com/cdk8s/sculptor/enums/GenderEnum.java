package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

public enum GenderEnum implements BasicEnum {
	PRIVACY(1, "保密"),
	MALE(2, "男性"),
	FEMALE(3, "女性"),
	NEUTRAL(4, "中性");

	private Integer code;
	private String description;

	GenderEnum(final Integer code, final String description) {
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
		GenderEnum[] allEnums = values();
		for (GenderEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static GenderEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		GenderEnum[] allEnums = values();
		for (GenderEnum enumObject : allEnums) {
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
		GenderEnum[] allEnums = values();
		for (GenderEnum enumObject : allEnums) {
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
		GenderEnum[] allEnums = values();
		for (GenderEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
