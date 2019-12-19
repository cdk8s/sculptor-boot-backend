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
