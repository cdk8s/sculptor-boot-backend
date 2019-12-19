package com.cdk8s.sculptor.aop.permission;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPermission {


	String[] value();

	/**
	 * 类型（1并且，2或）
	 */
	int requestPermissionType() default 1;

}
