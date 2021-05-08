/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：R.java
 * 项目名称：sculptor-boot-common-spring
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.response.biz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class R {

	private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	private static final String DEFAULT_FAILURE_MESSAGE = "操作失败";
	private static final boolean DEFAULT_SUCCESS = true;
	private static final boolean DEFAULT_FAILURE = false;


	//=====================================正常返回 start=====================================


	public static ResponseEntity<ResultObject> success() {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(DEFAULT_SUCCESS_MESSAGE);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}


	public static ResponseEntity<ResultObject> success(Object data) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(DEFAULT_SUCCESS_MESSAGE);
		resultObject.setData(data);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}

	public static ResponseEntity<ResultObject> success(Object data, String message) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(message);
		resultObject.setData(data);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}

	//=====================================正常返回  end=====================================

	//=====================================异常返回 start=====================================

	public static ResponseEntity<ResultObject> failure() {
		return failure(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static ResponseEntity<ResultObject> failure(HttpStatus httpStatus) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.FAILURE);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(DEFAULT_FAILURE_MESSAGE);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}

	public static ResponseEntity<ResultObject> failure(String message) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.FAILURE);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultObject);
	}

	public static ResponseEntity<ResultObject> failure(HttpStatus httpStatus, String message) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(ResultCodeEnum.FAILURE);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(message);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}

	public static ResponseEntity<ResultObject> failure(HttpStatus httpStatus, ResultCodeEnum resultCodeEnum, String message) {
		ResultObject resultObject = new ResultObject();
		resultObject.setCode(resultCodeEnum);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(message);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}
	//=====================================异常返回  end=====================================
}
