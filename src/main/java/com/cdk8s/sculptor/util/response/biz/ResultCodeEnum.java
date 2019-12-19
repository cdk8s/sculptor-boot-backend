package com.cdk8s.sculptor.util.response.biz;


/**
 * 统一返回异常
 * 自定义错误编码，目前暂定和状态码一致
 */
public enum ResultCodeEnum {
	/**
	 * 成功
	 */
	SUCCESS(200),
	/**
	 * 失败
	 */
	FAILURE(400),//失败
	/**
	 * 未认证
	 */
	UNAUTHORIZED(401),
	/**
	 * 没有权限
	 */
	FORBIDDEN(403),
	/**
	 * 不存在
	 */
	NOT_FOUND(404),
	/**
	 * 服务器内部错误
	 */
	INTERNAL_SERVER_ERROR(500);

	private final int code;

	ResultCodeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
