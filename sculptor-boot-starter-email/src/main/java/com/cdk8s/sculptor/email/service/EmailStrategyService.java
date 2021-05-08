/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EmailStrategyService.java
 * 项目名称：sculptor-boot-starter-email
 * 项目描述：sculptor-boot-starter-email
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.email.service;


import com.cdk8s.sculptor.email.strategy.EmailStrategyInterface;
import com.cdk8s.sculptor.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmailStrategyService {

	private final Map<String, EmailStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public EmailStrategyService(Map<String, EmailStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	public Boolean sendEmail(String beanName, String receiverEmailAddress, String emailSubjectTitle, String emailHtmlBody) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).sendEmail(receiverEmailAddress, emailSubjectTitle, emailHtmlBody);
		}
		return false;
	}

}
