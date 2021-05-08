/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：GenerateIdUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.id;


import java.util.UUID;

/**
 * 雪花 ID 生成器，eg：417454619141214208（18位）
 */
public final class GenerateIdUtil {

	private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

	public static Long getId() {
		return snowflakeIdWorker.nextId();
	}

	public static String getIdString() {
		return String.valueOf(snowflakeIdWorker.nextId());
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}

