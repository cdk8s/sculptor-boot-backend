/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NotXss.java
 * 项目名称：sculptor-boot-common-validator
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotXssValidator.class)
@Documented
public @interface NotXss {

	// 默认错误消息
	String message() default "{org.hibernate.validator.constraints.SafeHtml.message}";

	Class<?>[] groups() default {};

	int max() default 0;

	int min() default 0;

	Class<? extends Payload>[] payload() default {};

	@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		NotXss[] value();
	}
}
