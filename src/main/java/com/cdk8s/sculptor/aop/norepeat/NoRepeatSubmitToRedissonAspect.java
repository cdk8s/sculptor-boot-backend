package com.cdk8s.sculptor.aop.norepeat;


import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
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

import java.util.concurrent.TimeUnit;

@Order(4)
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitToRedissonAspect {

	@Autowired
	private RedissonClient redissonClient;

	@SneakyThrows
	@Around("@annotation(noRepeatSubmitToRedisson)")
	public Object around(ProceedingJoinPoint point, NoRepeatSubmitToRedisson noRepeatSubmitToRedisson) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("NoRepeatSubmitToRedissonAspect 类名:<{}>, 方法:<{}>", strClassName, strMethodName);

		long startTime = DatetimeUtil.currentEpochMilli();
		Object[] bodyArgs = point.getArgs();
		if (null == bodyArgs || bodyArgs.length == 0) {
			throw new SystemException("重复提交验证必须含有 body 内容参数");
		}

		// 处理 body 参数，把参数 md5 成一个字符串做 key
		String lockKey = Md5Util.md5ByGuava(JsonUtil.toJson(bodyArgs));
		RLock lock = redissonClient.getLock(lockKey);
		// 尝试加锁，最多等待3秒，上锁以后5秒自动解锁
		boolean res = lock.tryLock(5, 3, TimeUnit.SECONDS);
		if (res) {
			// 获取锁成功, 执行进程
			Object result;
			try {
				// 执行实际方法
				result = point.proceed();
			} finally {
				// 解锁
				lock.unlock();
				long endTime = DatetimeUtil.currentEpochMilli();
				long execTime = endTime - startTime;
				log.debug("NoRepeatSubmitToRedissonAspect 耗时:<{}ms>", execTime);
			}
			return result;
		}

		// 获取锁失败，认为是重复提交的请求
		return R.failure(HttpStatus.BAD_REQUEST, "重复请求，请稍后再试");
	}


}
