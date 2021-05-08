/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ResultObject.java
 * 项目名称：sculptor-boot-common-spring
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.response.biz;


import com.cdk8s.sculptor.util.DatetimeUtil;

public class ResultObject {

	private int code;
	private boolean isSuccess;
	private String msg;
	private Long timestamp;
	private Object data;

	public ResultObject setCode(ResultCodeEnum resultCodeEnum) {
		this.code = resultCodeEnum.getCode();
		return this;
	}

	public long getTimestamp() {
		if (null == timestamp) {
			return DatetimeUtil.currentEpochMilli();
		}
		return timestamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
