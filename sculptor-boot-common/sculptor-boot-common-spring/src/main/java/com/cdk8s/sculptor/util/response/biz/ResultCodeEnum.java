/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ResultCodeEnum.java
 * 项目名称：sculptor-boot-common-spring
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.response.biz;


/**
 * 统一返回异常
 * 自定义错误编码，目前暂定和状态码一致
 */
public enum ResultCodeEnum {
	/**
	 * 未绑定微信
	 */
	NOT_BINDING_WECHAT(200011),
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
