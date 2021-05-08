/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SmsStrategyService.java
 * 项目名称：sculptor-boot-starter-sms
 * 项目描述：sculptor-boot-starter-sms
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.sms.service;


import com.cdk8s.sculptor.sms.pojo.SmsStrategyBO;
import com.cdk8s.sculptor.sms.strategy.SmsStrategyInterface;
import com.cdk8s.sculptor.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SmsStrategyService {

	private final Map<String, SmsStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public SmsStrategyService(Map<String, SmsStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	public SmsStrategyBO sendSmsVerificationCode(String beanName, String phoneNumbers, String smsTemplateCode, String code, String bizId) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).sendSmsVerificationCode(phoneNumbers, smsTemplateCode, code, bizId);
		}
		return null;
	}

	public SmsStrategyBO sendSms(String beanName, String phoneNumbers, String smsTemplateCode, Map<String, String> params, String bizId) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).sendSms(phoneNumbers, smsTemplateCode, params, bizId);
		}
		return null;
	}

	public SmsStrategyBO batchSms(String beanName, List<String> phoneNumberList, String smsTemplateCode, Map<String, String> params, String bizId) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).batchSms(phoneNumberList, smsTemplateCode, params, bizId);
		}
		return null;
	}
}
