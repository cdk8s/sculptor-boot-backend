/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NoRepeatSubmitToRedissonAspect.java
 * 项目名称：sculptor-boot-starter-redisson
 * 项目描述：sculptor-boot-starter-redisson
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.redisson.aop.norepeat;


import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RequestHolder;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Order(4)
@Aspect
@Slf4j
public class NoRepeatSubmitToRedissonAspect {

	@Autowired
	private RedissonClient redissonClient;

	@SneakyThrows
	@Around("@annotation(noRepeatSubmitToRedisson)")
	public Object around(ProceedingJoinPoint point, NoRepeatSubmitToRedisson noRepeatSubmitToRedisson) {
		HttpServletRequest request = RequestHolder.getRequest();
		if (null == request) {
			throw new SystemException("重复提交验证必须含有 request 对象");
		}
		String ip = IPUtil.getIp(request);

		Object[] bodyArgs = point.getArgs();

		if (null == bodyArgs || bodyArgs.length == 0) {
			throw new SystemException("重复提交验证必须含有 body 内容参数");
		}

		List<Object> paramToJSON = new ArrayList<>();
		for (Object obj : bodyArgs) {
			if (!(obj instanceof HttpServletRequest || obj instanceof HttpServletResponse)) {
				paramToJSON.add(obj);
			}
		}

		String lockKey = Md5Util.md5ByGuava(JsonUtil.toJson(paramToJSON));
		lockKey = "REDISSON_LOCK:" + ip + ":" + lockKey;
		RLock lock = redissonClient.getLock(lockKey);
		boolean res = lock.tryLock(noRepeatSubmitToRedisson.waitTimeByMilliseconds(), noRepeatSubmitToRedisson.leaseTimeByMilliseconds(), TimeUnit.MILLISECONDS);
		if (res) {
			Object result;
			try {
				result = point.proceed();
			} finally {
				lock.unlock();
			}
			return result;
		}

		return R.failure(HttpStatus.BAD_REQUEST, "重复请求，请稍后再试");
	}


}
