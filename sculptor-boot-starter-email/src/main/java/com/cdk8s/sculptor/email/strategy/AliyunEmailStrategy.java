/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AliyunEmailStrategy.java
 * 项目名称：sculptor-boot-starter-email
 * 项目描述：sculptor-boot-starter-email
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.email.strategy;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cdk8s.sculptor.email.properties.EmailParamProperties;
import com.cdk8s.sculptor.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("aliyunEmailStrategy")
public class AliyunEmailStrategy implements EmailStrategyInterface {

	@Autowired
	private EmailParamProperties emailParamProperties;

	//=====================================业务处理 start=====================================

	@Override
	public Boolean sendEmail(String receiverEmailAddress, String emailSubjectTitle, String emailHtmlBody) {
		IClientProfile profile = DefaultProfile.getProfile(emailParamProperties.getAliyunRegionId(), emailParamProperties.getAliyunAccessKeyId(), emailParamProperties.getAliyunAccessSecret());

		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName(emailParamProperties.getAliyunSendDomain());
			request.setFromAlias(emailParamProperties.getAliyunDefaultSendUserName());
			request.setAddressType(1);
			request.setReplyToAddress(false);

			request.setToAddress(receiverEmailAddress);
			request.setTagName("notice");
			request.setActionName("SingleSendMail");
			request.setSubject(emailSubjectTitle);
			request.setHtmlBody(emailHtmlBody);
			request.setMethod(MethodType.POST);
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			return true;
		} catch (ClientException e) {

			log.debug("------zch------errcode <{}>", e.getErrCode());
			ExceptionUtil.printStackTraceAsString(e);
		} catch (Exception e) {
			log.debug("------zch------errcode <{}>", e.getMessage());
			ExceptionUtil.printStackTraceAsString(e);
		}
		return false;
	}

	// =====================================业务处理 end=====================================


	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================
}
