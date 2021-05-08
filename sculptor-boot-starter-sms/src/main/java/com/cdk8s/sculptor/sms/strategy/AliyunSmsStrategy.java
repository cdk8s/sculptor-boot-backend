/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AliyunSmsStrategy.java
 * 项目名称：sculptor-boot-starter-sms
 * 项目描述：sculptor-boot-starter-sms
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.sms.strategy;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cdk8s.sculptor.sms.pojo.SmsStrategyBO;
import com.cdk8s.sculptor.sms.properties.SmsParamProperties;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("aliyunSmsStrategy")
public class AliyunSmsStrategy implements SmsStrategyInterface {

	@Autowired
	private SmsParamProperties smsParamProperties;

	//=====================================业务处理 start=====================================


	@Override
	public SmsStrategyBO sendSmsVerificationCode(String phoneNumbers, String smsTemplateCode, String code, String bizId) {
		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		CommonResponse response = sendSms(phoneNumbers, smsParamProperties.getAliyunLoginSignName(), smsTemplateCode, JsonUtil.toJson(params), bizId, null);
		if (null == response) {
			return null;
		}
		String responseData = response.getData();
		if (StringUtil.isBlank(responseData)) {
			return null;
		}

		SmsStrategyBO smsStrategyBO = new SmsStrategyBO();
		smsStrategyBO.setState(false);

		HashMap<String, String> resultMap = JsonUtil.toMap(responseData, String.class, String.class);

		String codeValue = resultMap.get("Code");
		if (StringUtil.isNotBlank(codeValue) && "OK".equals(codeValue.toUpperCase())) {
			smsStrategyBO.setState(true);
		} else {
			log.error("------zch------阿里云短信发送失败= <{}>", responseData);
		}
		smsStrategyBO.setBizId(resultMap.get("BizId"));
		smsStrategyBO.setMessage(resultMap.get("Message"));

		return smsStrategyBO;
	}


	@Override
	public SmsStrategyBO sendSms(String phoneNumbers, String smsTemplateCode, Map<String, String> params, String bizId) {
		String toJson = null;
		if (null != params) {
			toJson = JsonUtil.toJson(params);
		}

		CommonResponse response = sendSms(phoneNumbers, smsParamProperties.getAliyunLoginSignName(), smsTemplateCode, toJson, bizId, null);
		if (null == response) {
			return null;
		}
		String responseData = response.getData();
		if (StringUtil.isBlank(responseData)) {
			return null;
		}

		SmsStrategyBO smsStrategyBO = new SmsStrategyBO();
		smsStrategyBO.setState(false);

		HashMap<String, String> resultMap = JsonUtil.toMap(responseData, String.class, String.class);

		String codeValue = resultMap.get("Code");
		if (StringUtil.isNotBlank(codeValue) && "OK".equals(codeValue.toUpperCase())) {
			smsStrategyBO.setState(true);
		}
		smsStrategyBO.setBizId(resultMap.get("BizId"));
		smsStrategyBO.setMessage(resultMap.get("Message"));

		return smsStrategyBO;
	}


	@Override
	public SmsStrategyBO batchSms(List<String> phoneNumberList, String smsTemplateCode, Map<String, String> params, String bizId) {
		String toJson = null;
		if (null != params) {
			toJson = JsonUtil.toJson(params);
		}

		String phoneNumberJson = JsonUtil.toJson(phoneNumberList);

		List<String> signNameList = new ArrayList<>();
		for (int i = 0; i < phoneNumberList.size(); i++) {
			signNameList.add(smsParamProperties.getAliyunLoginSignName());
		}
		String signNameJson = JsonUtil.toJson(signNameList);

		CommonResponse response = batchSms(phoneNumberJson, signNameJson, smsTemplateCode, toJson, bizId, null);
		if (null == response) {
			return null;
		}
		String responseData = response.getData();
		if (StringUtil.isBlank(responseData)) {
			return null;
		}

		SmsStrategyBO smsStrategyBO = new SmsStrategyBO();
		smsStrategyBO.setState(false);

		HashMap<String, String> resultMap = JsonUtil.toMap(responseData, String.class, String.class);

		String codeValue = resultMap.get("Code");
		if (StringUtil.isNotBlank(codeValue) && "OK".equalsIgnoreCase(codeValue)) {
			smsStrategyBO.setState(true);
		}
		smsStrategyBO.setBizId(resultMap.get("BizId"));
		smsStrategyBO.setMessage(resultMap.get("Message"));

		return smsStrategyBO;
	}

	// =====================================业务处理 end=====================================


	// =====================================私有方法 start=====================================

	private CommonResponse sendSms(String phoneNumbers, String signName, String templateCode, String templateParam, String outId, String smsUpExtendCode) {
		String sendAction = "SendSms";
		return smsCommon(phoneNumbers, signName, templateCode, smsParamProperties.getAliyunAccessKeyId(), smsParamProperties.getAliyunAccessSecret(), sendAction, outId, smsUpExtendCode, templateParam);
	}


	private CommonResponse batchSms(String phoneNumberJson, String signNameJson, String templateCode, String templateParamJson, String outId, String smsUpExtendCode) {
		String sendAction = "SendBatchSms";
		return batchSms(phoneNumberJson, signNameJson, templateCode, smsParamProperties.getAliyunAccessKeyId(), smsParamProperties.getAliyunAccessSecret(), sendAction, outId, smsUpExtendCode, templateParamJson);
	}


	private CommonResponse smsCommon(String phoneNumbers, String signName, String templateCode, String accessKeyId, String accessSecret, String action, String outId, String smsUpExtendCode, String templateParam) {

		DefaultProfile profile = DefaultProfile.getProfile(smsParamProperties.getAliyunRegionId(), accessKeyId, accessSecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain(smsParamProperties.getAliyunSmsDomain());
		request.setVersion(smsParamProperties.getAliyunSmsVersion());
		request.setAction(action);
		request.putQueryParameter("RegionId", smsParamProperties.getAliyunRegionId());
		request.putQueryParameter("PhoneNumbers", phoneNumbers);
		request.putQueryParameter("TemplateCode", templateCode);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateParam", templateParam);
		try {
			CommonResponse response = client.getCommonResponse(request);
			if (null != response) {
				log.info("------zch------阿里云发送单个短信结果： <{}>", JsonUtil.toJson(response));
			} else {
				log.error("------zch------阿里云发送单个短信结果 null");
			}
			return response;
		} catch (ServerException e) {
			ExceptionUtil.printStackTraceAsString(e);
			log.error("-----zch------sms Error response from server. Should review and fix it. {}", e.getMessage());
			log.error("------zch------sms phoneNumbers: {}", phoneNumbers);
		} catch (ClientException e) {
			ExceptionUtil.printStackTraceAsString(e);
			log.error("-----zch------sms Error response from server. Should review and fix it. {}", e.getMessage());
			log.info("------zch------sms Error Code: " + e.getErrCode());
			log.info("------zch------sms Error Message: " + e.getMessage());
			log.info("------zch------sms Msg ID: " + e.getRequestId());
			log.error("------zch------sms phoneNumbers: " + phoneNumbers);
		}
		return null;
	}

	private CommonResponse batchSms(String phoneNumberJson, String signNameJson, String templateCode, String accessKeyId, String accessSecret, String action, String outId, String smsUpExtendCode, String templateParamJson) {
		DefaultProfile profile = DefaultProfile.getProfile(smsParamProperties.getAliyunRegionId(), accessKeyId, accessSecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain(smsParamProperties.getAliyunSmsDomain());
		request.setSysVersion(smsParamProperties.getAliyunSmsVersion());
		request.setSysAction(action);
		request.putQueryParameter("PhoneNumberJson", phoneNumberJson);
		request.putQueryParameter("SignNameJson", signNameJson);
		request.putQueryParameter("TemplateCode", templateCode);

		if (StringUtil.isNotBlank(templateParamJson)) {
			request.putQueryParameter("TemplateParamJson", templateParamJson);
		}

		try {
			CommonResponse response = client.getCommonResponse(request);
			if (null != response) {
				log.info("------zch------阿里云发送批量短信结果：<{}>", JsonUtil.toJson(response));
			} else {
				log.error("------zch------阿里云发送批量短信结果 null");
			}
			return response;
		} catch (ServerException e) {
			ExceptionUtil.printStackTraceAsString(e);
			log.error("-----zch------sms Error response from sms server. Should review and fix it.", e);
			log.error("------zch------sms phoneNumberJson: " + phoneNumberJson);
		} catch (ClientException e) {
			ExceptionUtil.printStackTraceAsString(e);
			log.error("------zch------sms Error response from sms server. Should review and fix it. ", e);
			log.info("------zch------sms Error Code: " + e.getErrCode());
			log.info("------zch------sms Error Message: " + e.getMessage());
			log.info("------zch------sms Msg ID: " + e.getRequestId());
			log.error("------zch------sms phoneNumberJson: " + phoneNumberJson);
		}
		return null;
	}

	// =====================================私有方法 end=====================================
}
