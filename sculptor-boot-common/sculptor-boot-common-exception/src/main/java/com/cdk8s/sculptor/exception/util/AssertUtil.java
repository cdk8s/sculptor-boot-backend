/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AssertUtil.java
 * 项目名称：sculptor-boot-common-exception
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.exception.util;


import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class AssertUtil {

	public static void notBeNull(String object, String tipMsg) {
		if (StringUtil.isBlank(object)) {
			throw new BusinessException(tipMsg);
		}
	}

	public static void notBeNull(Collection object, String tipMsg) {
		if (CollectionUtil.isEmpty(object)) {
			throw new BusinessException(tipMsg);
		}
	}

	public static void notBeNull(Long object, String tipMsg) {
		if (null == object) {
			throw new BusinessException(tipMsg);
		}
	}

	public static void notBeNull(Integer object, String tipMsg) {
		if (null == object) {
			throw new BusinessException(tipMsg);
		}
	}


}

