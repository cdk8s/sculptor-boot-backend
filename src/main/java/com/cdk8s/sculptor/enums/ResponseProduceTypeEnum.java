package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果类型：JSON / HTML
 */
public enum ResponseProduceTypeEnum implements BasicEnum {

	JSON(1, "application/json;charset=UTF-8"),
	HTML(2, "text/html");

	private Integer code;
	private String description;

	ResponseProduceTypeEnum(final Integer code, final String description) {
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

	public static ResponseProduceTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		ResponseProduceTypeEnum[] allEnums = values();
		for (ResponseProduceTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		ResponseProduceTypeEnum[] allEnums = values();
		for (ResponseProduceTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		ResponseProduceTypeEnum[] allEnums = values();
		for (ResponseProduceTypeEnum enumObject : allEnums) {
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
		ResponseProduceTypeEnum[] allEnums = values();
		for (ResponseProduceTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
