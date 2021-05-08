/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CustomAppender.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

// package com.cdk8s.sculptor.extend.logs;
//
// import ch.qos.logback.classic.Level;
// import ch.qos.logback.classic.spi.ILoggingEvent;
// import ch.qos.logback.classic.spi.IThrowableProxy;
// import ch.qos.logback.core.AppenderBase;
// import com.cdk8s.sculptor.config.ExceptionControllerAdvice;
// import com.cdk8s.sculptor.exception.BusinessException;
// import com.cdk8s.sculptor.util.DatetimeUtil;
// import com.google.common.util.concurrent.AtomicLongMap;
//
// import java.util.Date;
//
// /**
//  * 还需要在 logback-dev.xml 配置文件上配置好对应的设置
//  */
// public class CustomAppender extends AppenderBase<ILoggingEvent> {
//
// 	// 使用guava的AtomicLongMap,用于并发计数
// 	public static final AtomicLongMap<String> ATOMIC_LONG_MAP = AtomicLongMap.create();
//
// 	// 重写接收日志事件方法
// 	@Override
// 	protected void append(ILoggingEvent event) {
// 		if (event.getLevel() == Level.ERROR) {
// 			// 每个输出语句都会进来这个方法，量太大，所以我们这里只重点监控 error 级别日志
// 			IThrowableProxy throwableProxy = event.getThrowableProxy();
// 			// 确认抛出异常
// 			boolean adviceException = event.getLoggerName().equalsIgnoreCase(ExceptionControllerAdvice.class.getName());
// 			if (adviceException) {
// 				// 通过统一异常处理抛出的异常
// 				// 统一异常类会有很多 error 输出，所以实际用的时候最好根据某个场景进行收集数据
// 				// 比如这里根据某个异常输出的信息，认为其异常很重要，所以在这里进行了收集
// 				boolean errorMessage = event.getFormattedMessage().equalsIgnoreCase("统一异常信息输出：<抛异常了>");
// 				if (errorMessage) {
// 					// 以每分钟为key，记录每分钟异常数量
// 					String key = DatetimeUtil.formatDate(new Date(), "yyyyMMddHHmm");
// 					long errorCount = ATOMIC_LONG_MAP.incrementAndGet(key);
// 					if (errorCount > 10) {
// 						// zchtodo 可以用来做超过几次次触发报警代码，比如发邮件，有企业微信消息等
// 						System.err.println("=================================CustomAppender.....");
// 					}
// 					// 清理历史计数统计，防止极端情况下内存泄露
// 					for (String oldKey : ATOMIC_LONG_MAP.asMap().keySet()) {
// 						if (!key.equals(oldKey)) {
// 							ATOMIC_LONG_MAP.remove(oldKey);
// 						}
// 					}
// 				}
//
// 			} else if (throwableProxy != null) {
// 				// throw 异常
// 				if (throwableProxy.getClassName().equalsIgnoreCase(BusinessException.class.getName())) {
// 					// zchtodo 如果只认某个异常，可以这样做判断
// 				}
//
// 				// 以每分钟为key，记录每分钟异常数量
// 				String key = DatetimeUtil.formatDate(new Date(), "yyyyMMddHHmm");
// 				long errorCount = ATOMIC_LONG_MAP.incrementAndGet(key);
// 				if (errorCount > 3) {
// 					// zchtodo 超过几次次触发报警代码
// 					System.err.println("=================================CustomAppender.....");
// 				}
// 				// 清理历史计数统计，防止极端情况下内存泄露
// 				for (String oldKey : ATOMIC_LONG_MAP.asMap().keySet()) {
// 					if (!key.equals(oldKey)) {
// 						ATOMIC_LONG_MAP.remove(oldKey);
// 					}
// 				}
// 			}
// 		}
// 	}
// }
