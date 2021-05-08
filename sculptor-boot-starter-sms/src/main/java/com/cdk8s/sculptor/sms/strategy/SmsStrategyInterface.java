/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SmsStrategyInterface.java
 * 项目名称：sculptor-boot-starter-sms
 * 项目描述：sculptor-boot-starter-sms
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.sms.strategy;


import com.cdk8s.sculptor.sms.pojo.SmsStrategyBO;

import java.util.List;
import java.util.Map;

public interface SmsStrategyInterface {

	SmsStrategyBO sendSmsVerificationCode(String phoneNumbers, String smsTemplateCode, String code, String bizId);

	SmsStrategyBO sendSms(String phoneNumbers, String smsTemplateCode, Map<String, String> params, String bizId);

	SmsStrategyBO batchSms(List<String> phoneNumberList, String smsTemplateCode, Map<String, String> params, String bizId);

}
