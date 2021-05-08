/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CustomLongConverter.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLongConverter extends StdSerializer<Long> {
	public CustomLongConverter() {
		super(Long.class);
	}

	/**
	 * 前端 JS 对于超过 16 位的 20175678901234567 会变成：20175678901234570
	 */
	@SneakyThrows
	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) {
		if (value.toString().length() > 15) {
			gen.writeString(value.toString());
		} else {
			gen.writeNumber(value);
		}
	}
}
