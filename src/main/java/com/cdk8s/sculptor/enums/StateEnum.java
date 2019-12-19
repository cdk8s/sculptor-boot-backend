package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;
import com.cdk8s.sculptor.util.RandomUtil;

public enum StateEnum implements BasicEnum {
	ENABLE(1, "启用"),
	DISABLE(2, "禁用");

	private Integer code;
	private String description;

	StateEnum(final Integer code, final String description) {
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
		StateEnum[] allEnums = values();
		for (StateEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static StateEnum getRandomEnum() {
		StateEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static StateEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		StateEnum[] allEnums = values();
		for (StateEnum enumObject : allEnums) {
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
		StateEnum[] allEnums = values();
		for (StateEnum enumObject : allEnums) {
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
		StateEnum[] allEnums = values();
		for (StateEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
