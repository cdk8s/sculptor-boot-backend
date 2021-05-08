/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WxpayStrategy.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.strategy;

import com.alibaba.fastjson.JSON;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.pay.bo.*;
import com.cdk8s.sculptor.pay.constant.WxpayConstant;
import com.cdk8s.sculptor.pay.dto.*;
import com.cdk8s.sculptor.pay.properties.WxpayParamProperties;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("wxpayStrategy")
public class WxpayStrategy implements PayStrategyInterface {

	@Autowired
	private WxpayParamProperties wxpayParamProperties;

	@SneakyThrows
	@Override
	public PayQrDTO qrPay(Object wxpayQrServiceBO) {
		WxpayQrServiceBO serviceBO = (WxpayQrServiceBO) wxpayQrServiceBO;
		Map<String, String> params = UnifiedOrderModel
				.builder()
				.appid(wxpayParamProperties.getAppId())
				.mch_id(wxpayParamProperties.getMchId())
				.nonce_str(WxPayKit.generateStr())
				.body(serviceBO.getBody())
				.attach(serviceBO.getAttach())
				.out_trade_no(serviceBO.getOutTradeNo())
				.total_fee(serviceBO.getTotalFee())
				.notify_url(wxpayParamProperties.getCallbackUrl())
				.trade_type(TradeType.NATIVE.getTradeType())
				.build()
				.createSign(wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);

		Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

		String returnCode = result.get(WxpayConstant.RETURN_CODE);
		String returnMsg = result.get(WxpayConstant.RETURN_MSG);
		String errCodeDes = result.get(WxpayConstant.ERR_CODE_DES);
		if (!WxPayKit.codeIsOk(returnCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayQrDTO(null, returnMsg);
		}
		String resultCode = result.get(WxpayConstant.RESULT_CODE);
		if (!WxPayKit.codeIsOk(resultCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayQrDTO(null, returnMsg);
		}

		String qrCode = result.get(WxpayConstant.CODE_URL);
		return new PayQrDTO(qrCode, null);
	}

	@Override
	public PayWapDTO wapPay(Object wxpayWapServiceBO) {
		WxpayWapServiceBO serviceBO = (WxpayWapServiceBO) wxpayWapServiceBO;

		String wapUrl = wxpayParamProperties.getWapUrl();
		String wapName = wxpayParamProperties.getWapName();
		if (StringUtil.isBlank(wapUrl)) {
			throw new BusinessException("wapUrl 必须配置");
		}
		if (StringUtil.isBlank(wapName)) {
			throw new BusinessException("wapName 必须配置");
		}

		H5SceneInfo sceneInfo = new H5SceneInfo();

		H5SceneInfo.H5 h5_info = new H5SceneInfo.H5();
		h5_info.setType("Wap");
		h5_info.setWap_url(wapUrl);
		h5_info.setWap_name(wapName);
		sceneInfo.setH5_info(h5_info);

		Map<String, String> params = UnifiedOrderModel
				.builder()
				.appid(wxpayParamProperties.getAppId())
				.mch_id(wxpayParamProperties.getMchId())
				.nonce_str(WxPayKit.generateStr())
				.body(serviceBO.getBody())
				.attach(serviceBO.getAttach())
				.out_trade_no(serviceBO.getOutTradeNo())
				.total_fee(serviceBO.getTotalFee())
				.notify_url(wxpayParamProperties.getCallbackUrl())
				.trade_type(TradeType.MWEB.getTradeType())
				.scene_info(JsonUtil.toJson(sceneInfo))
				.build()
				.createSign(wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);

		Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

		String returnCode = result.get(WxpayConstant.RETURN_CODE);
		String returnMsg = result.get(WxpayConstant.RETURN_MSG);
		String errCodeDes = result.get(WxpayConstant.ERR_CODE_DES);
		if (!WxPayKit.codeIsOk(returnCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayWapDTO(null, returnMsg);
		}
		String resultCode = result.get(WxpayConstant.RESULT_CODE);
		if (!WxPayKit.codeIsOk(resultCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayWapDTO(null, returnMsg);
		}

		String prepayId = result.get(WxpayConstant.PREPAY_ID);
		String webUrl = result.get(WxpayConstant.MWEB_URL);

		return new PayWapDTO(webUrl, null);
	}

	@Override
	public PayMpDTO mpPay(Object wxpayMpServiceBO) {
		WxpayMpServiceBO serviceBO = (WxpayMpServiceBO) wxpayMpServiceBO;

		Map<String, String> params = UnifiedOrderModel
				.builder()
				.appid(wxpayParamProperties.getAppId())
				.mch_id(wxpayParamProperties.getMchId())
				.nonce_str(WxPayKit.generateStr())
				.body(serviceBO.getBody())
				.attach(serviceBO.getAttach())
				.out_trade_no(serviceBO.getOutTradeNo())
				.total_fee(serviceBO.getTotalFee())
				.notify_url(wxpayParamProperties.getCallbackUrl())
				.trade_type(TradeType.JSAPI.getTradeType())
				.openid(serviceBO.getOpenId())
				.build()
				.createSign(wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);

		Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

		String returnCode = result.get(WxpayConstant.RETURN_CODE);
		String returnMsg = result.get(WxpayConstant.RETURN_MSG);
		String errCodeDes = result.get(WxpayConstant.ERR_CODE_DES);//在一些其他错误里面，它才是具体错误描述内容
		if (!WxPayKit.codeIsOk(returnCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayMpDTO(null, returnMsg);
		}
		String resultCode = result.get(WxpayConstant.RESULT_CODE);
		if (!WxPayKit.codeIsOk(resultCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayMpDTO(null, returnMsg);
		}

		String prepayId = result.get(WxpayConstant.PREPAY_ID);//微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时

		Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId, wxpayParamProperties.getAppId(), wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String brandWCPayRequest = JsonUtil.toJson(packageParams);
		return new PayMpDTO(brandWCPayRequest, null);
	}

	@Override
	public PayMiniDTO miniPay(Object wxpayMiniServiceBO) {
		WxpayMiniServiceBO serviceBO = (WxpayMiniServiceBO) wxpayMiniServiceBO;

		Map<String, String> params = UnifiedOrderModel
				.builder()
				.appid(wxpayParamProperties.getAppId())
				.mch_id(wxpayParamProperties.getMchId())
				.nonce_str(WxPayKit.generateStr())
				.body(serviceBO.getBody())
				.attach(serviceBO.getAttach())
				.out_trade_no(serviceBO.getOutTradeNo())
				.total_fee(serviceBO.getTotalFee())
				.notify_url(wxpayParamProperties.getCallbackUrl())
				.trade_type(TradeType.JSAPI.getTradeType())
				.openid(serviceBO.getOpenId())
				.build()
				.createSign(wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);

		Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

		String returnCode = result.get(WxpayConstant.RETURN_CODE);
		String returnMsg = result.get(WxpayConstant.RETURN_MSG);
		String errCodeDes = result.get(WxpayConstant.ERR_CODE_DES);
		if (!WxPayKit.codeIsOk(returnCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayMiniDTO(null, returnMsg);
		}
		String resultCode = result.get(WxpayConstant.RESULT_CODE);
		if (!WxPayKit.codeIsOk(resultCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayMiniDTO(null, returnMsg);
		}

		String prepayId = result.get(WxpayConstant.PREPAY_ID);
		Map<String, String> packageParams = WxPayKit.miniAppPrepayIdCreateSign(wxpayParamProperties.getAppId(), prepayId, wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);
		String paySign = JSON.toJSONString(packageParams);
		return new PayMiniDTO(paySign, null);
	}

	@Override
	public PayAppDTO appPay(Object wxpayAppServiceBO) {
		WxpayAppServiceBO serviceBO = (WxpayAppServiceBO) wxpayAppServiceBO;

		Map<String, String> params = UnifiedOrderModel
				.builder()
				.appid(wxpayParamProperties.getAppId())
				.mch_id(wxpayParamProperties.getMchId())
				.nonce_str(WxPayKit.generateStr())
				.body(serviceBO.getBody())
				.attach(serviceBO.getAttach())
				.out_trade_no(serviceBO.getOutTradeNo())
				.total_fee(serviceBO.getTotalFee())
				.notify_url(wxpayParamProperties.getCallbackUrl())
				.trade_type(TradeType.APP.getTradeType())
				.build()
				.createSign(wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);

		Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

		String returnCode = result.get(WxpayConstant.RETURN_CODE);
		String returnMsg = result.get(WxpayConstant.RETURN_MSG);
		String errCodeDes = result.get(WxpayConstant.ERR_CODE_DES);
		if (!WxPayKit.codeIsOk(returnCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayAppDTO(null, returnMsg);
		}
		String resultCode = result.get(WxpayConstant.RESULT_CODE);
		if (!WxPayKit.codeIsOk(resultCode)) {
			if (StringUtil.isNotBlank(errCodeDes)) {
				returnMsg = errCodeDes;
			}
			return new PayAppDTO(null, returnMsg);
		}

		String prepayId = result.get(WxpayConstant.PREPAY_ID);
		Map<String, String> packageParams = WxPayKit.appPrepayIdCreateSign(wxpayParamProperties.getAppId(), wxpayParamProperties.getMchId(), prepayId, wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256);

		String payReq = JSON.toJSONString(packageParams);
		return new PayAppDTO(payReq, null);
	}

}
