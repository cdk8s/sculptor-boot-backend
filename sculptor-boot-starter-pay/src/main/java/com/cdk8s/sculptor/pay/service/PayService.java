/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PayService.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.service;


import com.cdk8s.sculptor.pay.dto.PayQrDTO;
import com.cdk8s.sculptor.pay.strategy.PayStrategyInterface;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class PayService {

	private final Map<String, PayStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public PayService(Map<String, PayStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	@SneakyThrows
	public PayQrDTO qrPay(String beanName, Object serviceBO) {
		return strategyMap.get(beanName).qrPay(serviceBO);
	}

}
