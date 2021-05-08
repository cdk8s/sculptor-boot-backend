/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ValidateCodeException.java
 * 项目名称：sculptor-boot-common-exception
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.exception;


import com.cdk8s.sculptor.enums.ResponseErrorEnum;

public class ValidateCodeException extends RuntimeException {
	private static final long serialVersionUID = -1L;

	private String message;
	private int code = 500;

	public ValidateCodeException(String message) {
		super(message);
		this.message = message;
	}

	public ValidateCodeException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	public ValidateCodeException(String message, ResponseErrorEnum responseErrorEnum) {
		super(message);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	public ValidateCodeException(String message, ResponseErrorEnum responseErrorEnum, Throwable e) {
		super(message, e);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
