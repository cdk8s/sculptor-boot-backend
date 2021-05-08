/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：HttpService.java
 * 项目名称：sculptor-boot-common-okhttp
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.okhttp;

import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class HttpService<T> {

	@Autowired
	private OkHttpService okHttpService;

	//=====================================业务处理 start=====================================


	/**
	 * GET 请求
	 *
	 * @param url              请求地址
	 * @param clazz            请求返回序列化对象
	 * @param apiDescription   介绍该请求的描述，当有异常的时候用于异常描述
	 * @param errorResponseKey 返回结果包含该字符串则认定接口请求不成功
	 */
	public T get(String url, Class<T> clazz, String apiDescription, String errorResponseKey) {
		OkHttpResponse okHttpResponse = okHttpService.get(url);
		return buildResponseByErrorKey(okHttpResponse, clazz, apiDescription, errorResponseKey);
	}


	/**
	 * GET 请求
	 *
	 * @param url                请求地址
	 * @param clazz              请求返回序列化对象
	 * @param apiDescription     介绍该请求的描述，当有异常的时候用于异常描述
	 * @param successResponseKey 返回结果包含该字符串则认定接口请求成功
	 */
	public T getBySuccessKey(String url, Class<T> clazz, String apiDescription, String successResponseKey) {
		OkHttpResponse okHttpResponse = okHttpService.get(url);
		return buildResponseBySuccessKey(okHttpResponse, clazz, apiDescription, successResponseKey);
	}

	public T get(String url, Map<String, String> queries, Map<String, String> headers, Class<T> clazz, String apiDescription, String errorResponseKey) {
		OkHttpResponse okHttpResponse = okHttpService.get(url, queries, headers);
		return buildResponseByErrorKey(okHttpResponse, clazz, apiDescription, errorResponseKey);
	}

	public T post(String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz, String apiDescription, String errorResponseKey) {
		OkHttpResponse okHttpResponse = okHttpService.post(url, params, headers);
		return buildResponseByErrorKey(okHttpResponse, clazz, apiDescription, errorResponseKey);
	}

	public T postByJSON(String url, String jsonParams, Map<String, String> headers, Class<T> clazz, String apiDescription, String errorResponseKey) {
		OkHttpResponse okHttpResponse = okHttpService.postJsonParams(url, jsonParams, headers);
		return buildResponseByErrorKey(okHttpResponse, clazz, apiDescription, errorResponseKey);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private T buildResponseByErrorKey(OkHttpResponse okHttpResponse, Class<T> clazz, String apiDescription, String errorResponseKey) {
		if (null == okHttpResponse || okHttpResponse.getStatus() != HttpStatus.OK.value()) {
			throw new SystemException(apiDescription + " 失败，无返回");
		}

		String response = okHttpResponse.getResponse();
		if (StringUtil.isNotBlank(errorResponseKey)) {
			if (StringUtil.containsIgnoreCase(response, errorResponseKey)) {
				log.error("------zch------<{}> 返回异常信息 <{}>", apiDescription, response);
				throw new SystemException(apiDescription + " 失败，返回异常信息：" + response);
			}
		}

		log.debug("------zch------<{}> 返回正常信息 <{}>", apiDescription, response);

		if (clazz == String.class) {
			return (T) response;
		}

		T t;
		try {
			t = JsonUtil.toObject(response, clazz);
		} catch (Exception e) {
			throw new SystemException(apiDescription + " 失败，解析 JSON 失败");
		}
		return t;
	}

	private T buildResponseBySuccessKey(OkHttpResponse okHttpResponse, Class<T> clazz, String apiDescription, String successResponseKey) {
		if (null == okHttpResponse || okHttpResponse.getStatus() != HttpStatus.OK.value()) {
			throw new SystemException(apiDescription + " 失败，无返回");
		}

		String response = okHttpResponse.getResponse();
		if (StringUtil.isNotBlank(successResponseKey)) {
			if (!StringUtil.containsIgnoreCase(response, successResponseKey)) {
				log.error("------zch------<{}> 返回异常信息 <{}>", apiDescription, response);
				throw new SystemException(apiDescription + " 失败，返回异常信息：" + response);
			}
		}

		log.debug("------zch------<{}> 返回正常信息 <{}>", apiDescription, response);

		if (clazz == String.class) {
			return (T) response;
		}

		T t;
		try {
			t = JsonUtil.toObject(response, clazz);
		} catch (Exception e) {
			throw new SystemException(apiDescription + " 失败，解析 JSON 失败");
		}
		return t;
	}

	//=====================================私有方法  end=====================================

}
