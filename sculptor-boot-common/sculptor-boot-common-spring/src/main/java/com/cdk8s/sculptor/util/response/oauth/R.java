/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：R.java
 * 项目名称：sculptor-boot-common-spring
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.response.oauth;

import com.cdk8s.sculptor.constant.GlobalConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class R {

	//=====================================正常返回 start=====================================


	//=====================================正常返回  end=====================================

	//=====================================异常返回 start=====================================

	public static ResponseEntity<ResponseErrorObject> failure() {
		return failure(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus) {
		ResponseErrorObject responseErrorObject = new ResponseErrorObject();
		responseErrorObject.setError(GlobalConstant.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorDescription(GlobalConstant.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorUriMsg(GlobalConstant.OAUTH_ERROR_URI_MSG);
		return ResponseEntity.status(httpStatus).body(responseErrorObject);
	}

	public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus, String message) {
		ResponseErrorObject responseErrorObject = new ResponseErrorObject();
		responseErrorObject.setError(GlobalConstant.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorDescription(message);
		responseErrorObject.setErrorUriMsg(GlobalConstant.OAUTH_ERROR_URI_MSG);
		return ResponseEntity.status(httpStatus).body(responseErrorObject);
	}

	//=====================================异常返回  end=====================================
}
