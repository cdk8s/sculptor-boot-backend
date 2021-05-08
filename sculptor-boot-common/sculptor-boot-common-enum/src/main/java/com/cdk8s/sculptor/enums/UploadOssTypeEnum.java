/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadOssTypeEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

import com.cdk8s.sculptor.util.RandomUtil;

import java.util.HashMap;
import java.util.Map;

public enum UploadOssTypeEnum implements BasicEnum {
	ALIYUN(1, "aliyunUploadStrategy"),
	UPYUN(2, "upyunUploadStrategy"),
	LOCAL(3, "localUploadStrategy");

	private Integer code;
	private String description;

	UploadOssTypeEnum(final Integer code, final String description) {
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

	public static UploadOssTypeEnum getRandomEnum() {
		UploadOssTypeEnum[] allEnums = values();
		return allEnums[RandomUtil.nextInt(0, allEnums.length)];
	}

	public static UploadOssTypeEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		UploadOssTypeEnum[] allEnums = values();
		for (UploadOssTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		UploadOssTypeEnum[] allEnums = values();
		for (UploadOssTypeEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		UploadOssTypeEnum[] allEnums = values();
		for (UploadOssTypeEnum enumObject : allEnums) {
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
		UploadOssTypeEnum[] allEnums = values();
		for (UploadOssTypeEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
