/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CustomErrorController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.ResponseErrorEnum;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.response.biz.ResultObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;

	@SneakyThrows
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		// 没有指定返回类型，根据 http 请求头进行判断返回什么类型
		boolean checkIsJson = UserAgentUtil.isJsonRequest(request);
		if (status != null) {
			Object requestPathObject = request.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH);
			Object requestQueryObject = request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);

			String requestPathString = requestPathObject.toString();
			if (null != requestQueryObject) {
				String requestQueryString = requestQueryObject.toString();
				model.addAttribute("requestQueryString", requestQueryString);
				log.error("------zch------requestQueryString: <{}>", requestQueryString);
			}
			String currentDateTime = DatetimeUtil.currentDateTime();
			model.addAttribute("requestPath", requestPathString);
			model.addAttribute("timestamp", currentDateTime);
			log.error("------zch------requestPath: <{}>", requestPathString);
			log.error("------zch------timestamp: <{}>", currentDateTime);

			Object errorException = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
			if (errorException instanceof Exception) {
				model.addAttribute("errorMsg", ExceptionUtil.getStackTraceAsString((Exception) errorException));
				log.error("------zch------errorMsg: <{}>", ExceptionUtil.getStackTraceAsString((Exception) errorException));
			}

			int statusCode = Integer.parseInt(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				log.error("------zch------statusCode: <{}>", statusCode);
				if (checkIsJson) {
					response.setStatus(HttpStatus.NOT_FOUND.value());
					response.setCharacterEncoding(GlobalConstant.UTF_8);
					response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);

					ResultObject resultObject = new ResultObject();
					resultObject.setCode(ResponseErrorEnum.RECORD_NOT_EXIST.getCode());
					resultObject.setIsSuccess(false);
					resultObject.setMsg("您请求的接口不存在，请联系管理员进行处理");
					resultObject.setTimestamp(DatetimeUtil.currentEpochMilli());
					String json = JsonUtil.toJson(resultObject);
					PrintWriter out = response.getWriter();
					out.print(json);
					return null;
				}
				return "/404";
			}
		}

		if (checkIsJson) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCharacterEncoding(GlobalConstant.UTF_8);
			response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);

			ResultObject resultObject = new ResultObject();
			resultObject.setCode(ResponseErrorEnum.RECORD_NOT_EXIST.getCode());
			resultObject.setIsSuccess(false);
			resultObject.setMsg("系统发生异常，请联系管理员进行处理");
			resultObject.setTimestamp(DatetimeUtil.currentEpochMilli());
			String json = JsonUtil.toJson(resultObject);
			PrintWriter out = response.getWriter();
			out.print(json);
			return null;
		}
		return getErrorPath();
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}


}
