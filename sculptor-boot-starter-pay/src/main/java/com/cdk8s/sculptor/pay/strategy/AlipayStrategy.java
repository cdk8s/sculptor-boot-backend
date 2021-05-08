/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AlipayStrategy.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.strategy;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.cdk8s.sculptor.pay.bo.AlipayAppServiceBO;
import com.cdk8s.sculptor.pay.bo.AlipayQrServiceBO;
import com.cdk8s.sculptor.pay.constant.AlipayConstant;
import com.cdk8s.sculptor.pay.dto.*;
import com.cdk8s.sculptor.pay.properties.AlipayParamProperties;
import com.cdk8s.sculptor.util.StringUtil;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service("alipayStrategy")
public class AlipayStrategy implements PayStrategyInterface {

	@Autowired
	private AlipayParamProperties alipayParamProperties;

	@SneakyThrows
	@Override
	public PayQrDTO qrPay(Object alipayQrServiceBO) {
		AlipayQrServiceBO serviceBO = (AlipayQrServiceBO) alipayQrServiceBO;

		AliPayApiConfigKit.setThreadLocalAliPayApiConfig(buildApiConfig());

		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setSubject(serviceBO.getSubject());
		model.setTotalAmount(serviceBO.getTotalAmount());
		model.setStoreId(serviceBO.getStoreId());
		model.setOutTradeNo(serviceBO.getOutTradeNo());
		model.setTimeoutExpress(serviceBO.getTimeoutExpress());

		String resultStr = AliPayApi.tradePrecreatePayToResponse(model, alipayParamProperties.getCallbackUrl()).getBody();
		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		JSONObject alipay_trade_precreate_response = jsonObject.getJSONObject(AlipayConstant.RESPONSE_KEY);
		String code = alipay_trade_precreate_response.getString(AlipayConstant.CODE);
		String subMsg = alipay_trade_precreate_response.getString(AlipayConstant.SUB_MSG);
		if (!StringUtil.equalsIgnoreCase(code, AlipayConstant.SUCCESS_CODE)) {
			return new PayQrDTO(null, subMsg);
		}
		String qrCode = alipay_trade_precreate_response.getString(AlipayConstant.QR_CODE);
		return new PayQrDTO(qrCode, null);
	}

	@Override
	public PayWapDTO wapPay(Object serviceBO) {
		return null;
	}

	@Override
	public PayMpDTO mpPay(Object serviceBO) {
		return null;
	}

	@Override
	public PayMiniDTO miniPay(Object serviceBO) {
		return null;
	}

	@Override
	public PayAppDTO appPay(Object alipayAppServiceBO) {
		AlipayAppServiceBO serviceBO = (AlipayAppServiceBO) alipayAppServiceBO;

		AliPayApiConfigKit.setThreadLocalAliPayApiConfig(buildApiConfig());

		try {
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setSubject(serviceBO.getSubject());
			model.setOutTradeNo(serviceBO.getOutTradeNo());
			model.setTimeoutExpress(serviceBO.getTimeoutExpress());
			model.setTotalAmount(serviceBO.getTotalAmount());
			model.setProductCode("QUICK_MSECURITY_PAY");
			String orderInfo = AliPayApi.appPayToResponse(model, alipayParamProperties.getCallbackUrl()).getBody();
			return new PayAppDTO(orderInfo, null);
		} catch (AlipayApiException e) {
			return new PayAppDTO(null, e.getMessage());
		}
	}

	private AliPayApiConfig buildApiConfig() {
		AliPayApiConfig aliPayApiConfig = AliPayApiConfig.builder()
				.setAppId(alipayParamProperties.getAppId())
				.setAliPayPublicKey(alipayParamProperties.getPublicKey())
				.setCharset(AlipayConstant.CHARSET)
				.setPrivateKey(alipayParamProperties.getPrivateKey())
				.setServiceUrl(alipayParamProperties.getServerUrl())
				.setSignType(AlipayConstant.SIGN_TYPE)
				.build();
		return aliPayApiConfig;
	}

}
