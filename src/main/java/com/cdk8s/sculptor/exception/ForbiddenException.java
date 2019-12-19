package com.cdk8s.sculptor.exception;


import com.cdk8s.sculptor.util.response.oauth.ResponseErrorEnum;

public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = -1L;

	private String message;
	private int code = 403;

	public ForbiddenException(String message) {
		super(message);
		this.message = message;
	}

	public ForbiddenException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	public ForbiddenException(String message, ResponseErrorEnum responseErrorEnum) {
		super(message);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	public ForbiddenException(String message, ResponseErrorEnum responseErrorEnum, Throwable e) {
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
