/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NoRepeatSubmitToRedisson.java
 * 项目名称：sculptor-boot-starter-redisson
 * 项目描述：sculptor-boot-starter-redisson
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.redisson.aop.norepeat;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmitToRedisson {

	long waitTimeByMilliseconds() default 1000;

	int leaseTimeByMilliseconds() default 6000;

}
