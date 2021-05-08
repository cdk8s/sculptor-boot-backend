/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WebsocketBusinessTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import java.util.HashMap;
import java.util.Map;

public enum WebsocketBusinessTypeEnum implements BasicEnum {
	MATERIAL_LIBRARY_UPLOAD(1, "素材库数据"),
	SYSTEM_NOTICE(2, "系统通知"),
	WEIXIN_QR_LOGIN(3, "微信二维码扫码关注登录");

	private Integer code;
	private String description;

	WebsocketBusinessTypeEnum(final Integer code, final String description) {
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
		WebsocketBusinessTypeEnum[] allEnums = values();
		for (WebsocketBusinessTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static WebsocketBusinessTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		WebsocketBusinessTypeEnum[] allEnums = values();
		for (WebsocketBusinessTypeEnum enumObject : allEnums) {
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
		WebsocketBusinessTypeEnum[] allEnums = values();
		for (WebsocketBusinessTypeEnum enumObject : allEnums) {
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
		WebsocketBusinessTypeEnum[] allEnums = values();
		for (WebsocketBusinessTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
