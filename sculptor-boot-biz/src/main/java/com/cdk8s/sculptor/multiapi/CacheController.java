/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CacheController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.config.RedisCacheConfig;
import com.cdk8s.sculptor.init.ApplicationTestDataInitRunner;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.SpringUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;


/**
 * cdk8stodo 预留功能
 */
@Slf4j
@RestController
@RequestMapping("/multiapi")
public class CacheController {

	@Autowired
	private RedisTemplate redisTemplate;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 特殊情况手动删除业务缓存
	 */
	@SneakyThrows
	@RequestMapping(value = "/open/7mMoExM0I3LIwT4_cache", method = RequestMethod.GET)
	public ResponseEntity<?> cache1(@RequestParam(value = "serviceName", required = false) String serviceName) {
		if (StringUtil.isNotBlank(serviceName)) {
			// 删除指定
			invokeMethod(serviceName);
		} else {
			// 删除所有
			List<String> cacheServiceList = RedisCacheConfig.CACHE_SERVICE_BEAN_NAME_LIST;
			if (CollectionUtil.isNotEmpty(cacheServiceList)) {
				for (String beanName : cacheServiceList) {
					if (StringUtil.isBlank(beanName)) {
						continue;
					}
					invokeMethod(beanName);
				}
			}
			List<String> initTkeyKeyList = ApplicationTestDataInitRunner.CACHE_INIT_TKEY_KEY_LIST;
			if (CollectionUtil.isNotEmpty(initTkeyKeyList)) {
				for (String keyName : initTkeyKeyList) {
					if (StringUtil.isBlank(keyName)) {
						continue;
					}
					redisTemplate.delete(keyName);
				}
			}
		}
		return R.success();
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	@SneakyThrows
	private void invokeMethod(String serviceName) {
		Method method;
		Object bean;
		try {
			bean = SpringUtil.getBean(StringUtil.upperCamelToLowerCamel(serviceName));
			method = bean.getClass().getDeclaredMethod("cacheEvict", null);
		} catch (Exception e) {
			// 不存在该类或方法的则跳过
			return;
		}
		method.invoke(bean, null);
	}


	// =====================================私有方法 end=====================================
}
