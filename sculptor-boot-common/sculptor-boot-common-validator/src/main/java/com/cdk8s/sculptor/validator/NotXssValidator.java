/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NotXssValidator.java
 * 项目名称：sculptor-boot-common-validator
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.validator;

import com.cdk8s.sculptor.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotXssValidator implements ConstraintValidator<NotXss, String> {

	private int max;
	private int min;

	@Override
	public void initialize(NotXss constraintAnnotation) {
		this.max = constraintAnnotation.max();
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (StringUtil.isBlank(value)) {
			return true;
		}

		if (min > 0) {
			if (value.length() < min) {
				context.buildConstraintViolationWithTemplate("${org.hibernate.validator.constraints.Length.message}");
				return false;
			}
		}

		if (max > 0) {
			if (value.length() > max) {
				context.buildConstraintViolationWithTemplate("${org.hibernate.validator.constraints.Length.message}");
				return false;
			}
		}

		return StringUtil.checkXss(value);
	}
}
