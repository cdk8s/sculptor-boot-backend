/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExceptionControllerAdvice.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.ExceptionDescriptionByClassNameEnum;
import com.cdk8s.sculptor.enums.ExceptionDescriptionEnum;
import com.cdk8s.sculptor.enums.ResponseProduceTypeEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.ForbiddenException;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.cdk8s.sculptor.util.response.biz.ResultObject;
import com.cdk8s.sculptor.util.response.oauth.ResponseErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

	//=====================================业务处理 start=====================================


	/**
	 * 系统异常
	 */
	@ExceptionHandler(SystemException.class)
	public Object handleSystemException(HttpServletRequest httpServletRequest, SystemException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常，请联系管理员处理";
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest, message, null, null, false);
	}

	/**
	 * 空指针异常
	 */
	@ExceptionHandler(NullPointerException.class)
	public Object handleNullPointerException(HttpServletRequest httpServletRequest, NullPointerException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常.";
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest, message, null, null, false);
	}


	/**
	 * 操作权限异常
	 */
	@ExceptionHandler(ForbiddenException.class)
	public Object handleSystemException(HttpServletRequest httpServletRequest, ForbiddenException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "您没有该操作的权限，请联系管理员处理";
		}
		return getResponseObject(e, HttpStatus.FORBIDDEN, httpServletRequest, message, null, null, false);
	}

	/**
	 * 普通业务异常
	 */
	@ExceptionHandler(BusinessException.class)
	public Object handleBusinessException(HttpServletRequest httpServletRequest, BusinessException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "业务操作发生异常，请联系管理员处理";
		}

		ResponseProduceTypeEnum responseProduceTypeEnum = e.getResponseProduceTypeEnum();
		if (null == responseProduceTypeEnum) {
			responseProduceTypeEnum = ResponseProduceTypeEnum.JSON;
		}
		String pagePath = e.getPagePath();
		if (StringUtil.isBlank(pagePath)) {
			pagePath = "error";
		}

		return getResponseObject(e, HttpStatus.BAD_REQUEST, httpServletRequest, message, responseProduceTypeEnum, pagePath, false);
	}

	/**
	 * OAuth API 业务异常
	 */
	@ExceptionHandler(OauthApiException.class)
	public Object handleOauthApiException(HttpServletRequest httpServletRequest, OauthApiException e) {
		String message = e.getMessage();
		ResponseProduceTypeEnum responseProduceTypeEnum = e.getResponseProduceTypeEnum();
		if (null == responseProduceTypeEnum) {
			responseProduceTypeEnum = ResponseProduceTypeEnum.JSON;
		}
		String pagePath = e.getPagePath();
		if (StringUtil.isBlank(pagePath)) {
			pagePath = "error";
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, httpServletRequest, message, responseProduceTypeEnum, pagePath, true);
	}


	/**
	 * Hibernate Validator 参数校验失败错误统一返回
	 */
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public Object bindExceptionHandler(HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) {
		String message;
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null == fieldError) {
			message = "请求参数有误";
		} else {
			message = fieldError.getDefaultMessage();
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, httpServletRequest, message, null, null, false);
	}

	/**
	 * Hibernate Validator 参数校验失败错误统一返回
	 */
	@ExceptionHandler(value = {BindException.class})
	public Object bindExceptionHandler(HttpServletRequest httpServletRequest, BindException e) {
		String message;
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null == fieldError) {
			message = "请求参数有误";
		} else {
			message = fieldError.getDefaultMessage();
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, httpServletRequest, message, null, null, false);
	}

	/**
	 * 其他异常
	 */
	@ExceptionHandler(Exception.class)
	public Object handleException(HttpServletRequest httpServletRequest, Exception e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			boolean flag = true;
			message = "系统发生异常";
			// 如果是异常信息为空，则读取少量堆栈信息进行判断
			String traceAsString = ExceptionUtil.getRootCauseStackTraceAsString(e);
			for (ExceptionDescriptionEnum exceptionDescription : ExceptionDescriptionEnum.values()) {
				if (StringUtil.containsIgnoreCase(traceAsString, exceptionDescription.getKeyWord())) {
					message = exceptionDescription.getDescription();
					flag = false;
					break;
				}
			}
			if (flag) {
				// 异常描述中没找到，继续从类描述中查找
				for (ExceptionDescriptionByClassNameEnum exceptionDescription : ExceptionDescriptionByClassNameEnum.values()) {
					if (StringUtil.containsIgnoreCase(message, exceptionDescription.getKeyWord())) {
						message = exceptionDescription.getDescription();
						break;
					}
				}
			}
		} else {
			log.error(message);
			boolean flag = true;
			for (ExceptionDescriptionEnum exceptionDescription : ExceptionDescriptionEnum.values()) {
				if (StringUtil.containsIgnoreCase(message, exceptionDescription.getKeyWord())) {
					message = exceptionDescription.getDescription();
					flag = false;
					break;
				}
			}
			if (flag) {
				// 异常描述中没找到，继续从类描述中查找
				for (ExceptionDescriptionByClassNameEnum exceptionDescription : ExceptionDescriptionByClassNameEnum.values()) {
					if (StringUtil.containsIgnoreCase(message, exceptionDescription.getKeyWord())) {
						message = exceptionDescription.getDescription();
						break;
					}
				}
			}
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest, message, null, null, false);

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private Object getResponseObject(Exception e, HttpStatus httpStatus, HttpServletRequest httpServletRequest, String message, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath, Boolean isOauthException) {

		log.error("请求 URI：<{}>", httpServletRequest.getRequestURI());
		log.error("返回异常状态码：<{}>", httpStatus.value());
		log.error("统一异常信息输出：<{}>", message);
		if (null != UserInfoContext.getCurrentUser()) {
			log.error("统一异常用户信息 userId：<{}>", UserInfoContext.getCurrentUserId());
			log.error("统一异常用户信息 username：<{}>", UserInfoContext.getCurrentUsername());
		}
		if (!log.isDebugEnabled()) {
			// 非 debug 下才显示，因为下面已经有 debug 状态下完整展示堆栈的方法了
			log.error("统一异常堆栈输出：<{}>", ExceptionUtil.getRootCauseStackTraceAsString(e));
		}

		if (log.isDebugEnabled()) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		}

		if (StringUtil.isBlank(pagePath)) {
			pagePath = "error";
		}

		// 指定返回类型
		if (null != responseProduceTypeEnum) {
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.JSON) {
				if (isOauthException) {
					return returnJsonToOauth(httpStatus, message);
				}
				return returnJson(httpStatus, message);

			}
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.HTML) {
				return returnHtml(httpServletRequest, message, pagePath);
			}
		}

		// 没有指定返回类型，根据 http 请求头进行判断返回什么类型
		boolean checkIsJson = UserAgentUtil.isJsonRequest(httpServletRequest);
		if (checkIsJson) {
			if (isOauthException) {
				return returnJsonToOauth(httpStatus, message);
			}
			return returnJson(httpStatus, message);
		} else {
			return returnHtml(httpServletRequest, message, pagePath);
		}
	}

	private ResponseEntity<ResultObject> returnJson(HttpStatus httpStatus, String message) {
		return R.failure(httpStatus, message);
	}

	private ResponseEntity<ResponseErrorObject> returnJsonToOauth(HttpStatus httpStatus, String message) {
		return com.cdk8s.sculptor.util.response.oauth.R.failure(httpStatus, message);
	}

	private ModelAndView returnHtml(HttpServletRequest httpServletRequest, String message, String pagePath) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(GlobalConstant.DEFAULT_LOGIN_ERROR_KEY, message);
		modelAndView.addObject("url", httpServletRequest.getRequestURL());
		modelAndView.setViewName(pagePath);
		return modelAndView;
	}

	//=====================================私有方法  end=====================================
}
