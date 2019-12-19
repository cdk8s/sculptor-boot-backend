package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;
import com.cdk8s.sculptor.util.RandomUtil;

public enum DeleteEnum implements BasicEnum {
	NOT_DELETED(1, "未删除"),
	DELETED(2, "已删除");

	private Integer code;
	private String description;

	DeleteEnum(final Integer code, final String description) {
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
		DeleteEnum[] allEnums = values();
		for (DeleteEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static DeleteEnum getRandomEnum() {
		DeleteEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static DeleteEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		DeleteEnum[] allEnums = values();
		for (DeleteEnum enumObject : allEnums) {
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
		DeleteEnum[] allEnums = values();
		for (DeleteEnum enumObject : allEnums) {
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
		DeleteEnum[] allEnums = values();
		for (DeleteEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
