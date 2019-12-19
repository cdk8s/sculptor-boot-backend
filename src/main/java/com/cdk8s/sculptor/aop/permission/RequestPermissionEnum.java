package com.cdk8s.sculptor.aop.permission;

import com.cdk8s.sculptor.enums.BasicEnum;

import java.util.HashMap;
import java.util.Map;

public enum RequestPermissionEnum implements BasicEnum {
	AND_RELATION(1, "并且"),
	OR_RELATION(2, "或者");

	public static final int AND = 1;
	public static final int OR = 2;

	private Integer code;
	private String description;

	RequestPermissionEnum(final Integer code, final String description) {
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

	public static RequestPermissionEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RequestPermissionEnum[] allEnums = values();
		for (RequestPermissionEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		RequestPermissionEnum[] allEnums = values();
		for (RequestPermissionEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RequestPermissionEnum[] allEnums = values();
		for (RequestPermissionEnum enumObject : allEnums) {
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
		RequestPermissionEnum[] allEnums = values();
		for (RequestPermissionEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
