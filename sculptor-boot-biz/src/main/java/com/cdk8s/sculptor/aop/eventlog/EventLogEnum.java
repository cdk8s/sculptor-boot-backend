/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EventLogEnum.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.aop.eventlog;

import com.cdk8s.sculptor.enums.BasicEnum;

import java.util.HashMap;
import java.util.Map;

public enum EventLogEnum implements BasicEnum {
	QUERY(1, "查询"),
	CREATE(2, "创建"),
	UPDATE_INFO(3, "修改对象"),
	UPDATE_STATE(4, "修改状态"),
	DELETE(5, "删除"),
	IMPORT(6, "导入"),
	EXPORT(7, "导出");

	public static final int OPERATE_TYPE_QUERY = 1;
	public static final int OPERATE_TYPE_CREATE = 2;
	public static final int OPERATE_TYPE_UPDATE_INFO = 3;
	public static final int OPERATE_TYPE_UPDATE_STATE = 4;
	public static final int OPERATE_TYPE_DELETE = 5;
	public static final int OPERATE_TYPE_IMPORT = 6;
	public static final int OPERATE_TYPE_EXPORT = 7;

	private Integer code;
	private String description;

	EventLogEnum(final Integer code, final String description) {
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

	public static EventLogEnum getEnumByCode(Integer code) {
		if (null == code) {
			return null;
		}
		EventLogEnum[] allEnums = values();
		for (EventLogEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject;
			}
		}
		return null;
	}

	public static Map<Integer, String> getAllEnum() {
		Map<Integer, String> map = new HashMap<>();
		EventLogEnum[] allEnums = values();
		for (EventLogEnum enumObject : allEnums) {
			map.put(enumObject.getCode(), enumObject.getDescription());
		}
		return map;
	}

	public static String getDescriptionByCode(Integer code) {
		if (null == code) {
			return null;
		}
		EventLogEnum[] allEnums = values();
		for (EventLogEnum enumObject : allEnums) {
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
		EventLogEnum[] allEnums = values();
		for (EventLogEnum enumObject : allEnums) {
			if (enumObject.getCode().equals(code)) {
				return enumObject.name();
			}
		}
		return null;
	}
}
