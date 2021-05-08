/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：MisfirePolicyEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;
import com.cdk8s.sculptor.util.RandomUtil;

public enum MisfirePolicyEnum implements BasicEnum {
	DEFAULT(1, "默认"),
	NOW_RUN(2, "立即执行"),
	ONLY_RUN(3, "执行一次"),
	SUSPEND_RUN(4, "放弃执行");

	private Integer code;
	private String description;

	MisfirePolicyEnum(final Integer code, final String description) {
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
		MisfirePolicyEnum[] allEnums = values();
		for (MisfirePolicyEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static MisfirePolicyEnum getRandomEnum() {
		MisfirePolicyEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static MisfirePolicyEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		MisfirePolicyEnum[] allEnums = values();
		for (MisfirePolicyEnum enumObject : allEnums) {
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
		MisfirePolicyEnum[] allEnums = values();
		for (MisfirePolicyEnum enumObject : allEnums) {
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
		MisfirePolicyEnum[] allEnums = values();
		for (MisfirePolicyEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
