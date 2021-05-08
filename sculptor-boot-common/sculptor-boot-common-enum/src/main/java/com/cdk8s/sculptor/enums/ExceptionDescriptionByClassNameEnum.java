/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExceptionDescriptionByClassNameEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

public enum ExceptionDescriptionByClassNameEnum {

	ALIYUN_VOD("VodStrategyService", "视频操作出现异常，请联系管理员");

	private String keyWord;
	private String description;

	public String getKeyWord() {
		return keyWord;
	}

	public String getDescription() {
		return description;
	}

	ExceptionDescriptionByClassNameEnum(final String keyWord, final String description) {
		this.keyWord = keyWord;
		this.description = description;
	}

}
