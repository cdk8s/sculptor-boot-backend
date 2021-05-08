/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PayController.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.PayTypeEnum;
import com.cdk8s.sculptor.pay.bo.AlipayQrServiceBO;
import com.cdk8s.sculptor.pay.bo.WxpayQrServiceBO;
import com.cdk8s.sculptor.pay.constant.AlipayConstant;
import com.cdk8s.sculptor.pay.dto.PayQrDTO;
import com.cdk8s.sculptor.pay.properties.AlipayParamProperties;
import com.cdk8s.sculptor.pay.properties.WxpayParamProperties;
import com.cdk8s.sculptor.pay.service.PayService;
import com.cdk8s.sculptor.qr.service.QrGenerateService;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/open/sysCommon")
public class PayController {

	@Autowired
	private PayService payService;

	@Autowired
	private WxpayParamProperties wxpayParamProperties;

	@Autowired
	private AlipayParamProperties alipayParamProperties;

	@Autowired
	private QrGenerateService qrGenerateService;

	// =====================================查询业务 start=====================================


	@SneakyThrows
	@RequestMapping(value = "/alipay", method = RequestMethod.GET)
	public void alipay(HttpServletResponse response) {
		AlipayQrServiceBO serviceBO = new AlipayQrServiceBO();
		serviceBO.setTotalAmount("1");
		serviceBO.setSubject("这是订单标题");
		serviceBO.setStoreId(RandomUtil.randomAlphabetic(5));
		serviceBO.setOutTradeNo(RandomUtil.randomAlphabetic(10));
		PayQrDTO qrPay = payService.qrPay(PayTypeEnum.ALIPAY.getDescription(), serviceBO);
		if (StringUtil.isNotBlank(qrPay.getErrorMsg())) {
			String errorJSON = JsonUtil.toJson(R.failure(qrPay.getErrorMsg()));
			response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);
			response.getOutputStream().write(errorJSON.getBytes());
			return;
		}
		qrGenerateService.generateQrToOutputStream(qrPay.getQrContent(), response);
	}


	@RequestMapping(value = "/alipayCallback", method = {RequestMethod.POST, RequestMethod.GET})
	public String alipayCallback(HttpServletRequest request) {
		try {
			Map<String, String> params = AliPayApi.toMap(request);

			boolean verifyResult = AlipaySignature.rsaCheckV1(params, alipayParamProperties.getPublicKey(), AlipayConstant.CHARSET, AlipayConstant.SIGN_TYPE);

			if (verifyResult) {
				return "success";
			}
		} catch (Exception e) {
			return "failure";
		}
		return "failure";
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
