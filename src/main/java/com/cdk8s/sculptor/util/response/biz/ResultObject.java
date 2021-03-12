package com.cdk8s.sculptor.util.response.biz;

import com.cdk8s.sculptor.util.DatetimeUtil;


public class ResultObject<T extends Object> {
	
	private int code;
	
	private boolean isSuccess;
	
	private String msg;
	
	private Long timestamp;
	
	private T data;
	
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
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}

