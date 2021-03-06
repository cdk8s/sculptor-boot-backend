/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RegisterTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;
import com.cdk8s.sculptor.util.RandomUtil;

public enum RegisterTypeEnum implements BasicEnum {
	SYSTEM_INIT(1, "系统预置"),
	MANAGEMENT_ADD(2, "后台管理系统新增"),
	REGISTER(3, "主动注册"),
	INVITE(4, "被邀请注册");

	private Integer code;
	private String description;

	RegisterTypeEnum(final Integer code, final String description) {
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
		RegisterTypeEnum[] allEnums = values();
		for (RegisterTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static RegisterTypeEnum getRandomEnum() {
		RegisterTypeEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static RegisterTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		RegisterTypeEnum[] allEnums = values();
		for (RegisterTypeEnum enumObject : allEnums) {
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
		RegisterTypeEnum[] allEnums = values();
		for (RegisterTypeEnum enumObject : allEnums) {
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
		RegisterTypeEnum[] allEnums = values();
		for (RegisterTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
