/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NumericGeneratorUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import java.util.concurrent.atomic.LongAdder;

public class NumericGeneratorUtil {

	private static final LongAdder LONG_ADDER = new LongAdder();

	public static long getNumber() {
		LONG_ADDER.increment();
		return LONG_ADDER.longValue();
	}
}
