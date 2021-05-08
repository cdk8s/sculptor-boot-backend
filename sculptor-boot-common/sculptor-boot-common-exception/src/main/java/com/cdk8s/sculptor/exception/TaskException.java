/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TaskException.java
 * 项目名称：sculptor-boot-common-exception
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.exception;


public class TaskException extends Exception {

	private static final long serialVersionUID = 1L;

	private Code code;

	public TaskException(String msg, Code code) {
		this(msg, code, null);
	}

	public TaskException(String msg, Code code, Exception nestedEx) {
		super(msg, nestedEx);
		this.code = code;
	}

	public Code getCode() {
		return code;
	}

	public enum Code {
		TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
	}
}
