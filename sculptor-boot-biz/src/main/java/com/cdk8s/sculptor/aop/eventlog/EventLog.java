/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EventLog.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.aop.eventlog;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventLog {

	/**
	 * 存储的日志内容
	 */
	String message();

	/**
	 * 操作类型（1查询，2添加，3修改对象，4修改状态，5删除, 6导入，7导出）
	 */
	int operateType();
}
