package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

public enum BooleanEnum implements BasicEnum {
	YES(1, "是"),
	NO(2, "否");

	private Integer code;
	private String description;

	BooleanEnum(final Integer code, final String description) {
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

	public static BooleanEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		BooleanEnum[] allEnums = values();
		for (BooleanEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		BooleanEnum[] allEnums = values();
		for (BooleanEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		BooleanEnum[] allEnums = values();
		for (BooleanEnum enumObject : allEnums) {
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
		BooleanEnum[] allEnums = values();
		for (BooleanEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
