/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ApplicationCacheInitRunner.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.properties.InitParamProperties;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.SpringUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;


@Slf4j
@Order(15)
@Component
@ConditionalOnProperty(name = "custom.properties.init.start-remove-old-biz-cache-enabled", havingValue = "true", matchIfMissing = false)
public class ApplicationCacheInitRunner implements ApplicationRunner {

	@Autowired
	private InitParamProperties initParamProperties;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {

		log.info("=================================预设 cacheEvict 缓存清除 Start=================================");

		List<String> cacheNames = initParamProperties.getBizCacheNames();
		if (CollectionUtil.isEmpty(cacheNames)) {
			return;
		}

		for (String cacheName : cacheNames) {
			Object bean = SpringUtil.getBean(StringUtil.upperCamelToLowerCamel(cacheName));
			Method method = bean.getClass().getDeclaredMethod("cacheEvict", null);
			method.invoke(bean, null);
		}

		log.info("=================================预设 cacheEvict 缓存清除 End=================================");

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================
}
