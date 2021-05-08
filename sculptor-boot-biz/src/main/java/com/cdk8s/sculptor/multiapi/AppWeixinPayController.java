/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppWeixinPayController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.PayTypeEnum;
import com.cdk8s.sculptor.pay.bo.WxpayQrServiceBO;
import com.cdk8s.sculptor.pay.dto.PayQrDTO;
import com.cdk8s.sculptor.pay.properties.WxpayParamProperties;
import com.cdk8s.sculptor.pay.service.PayService;
import com.cdk8s.sculptor.qr.service.QrGenerateService;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/multiapi")
public class AppWeixinPayController {

	@Autowired
	private PayService payService;

	@Autowired
	private WxpayParamProperties wxpayParamProperties;

	@Autowired
	private QrGenerateService qrGenerateService;

	// =====================================查询业务 start=====================================

	/**
	 * 前端直接写成图片：<img width="300" src="http://sculptor.cdk8s.com:9091/sculptor-boot-backend/multiapi/weixinQrPay"/>
	 *
	 * @param response
	 */
	@SneakyThrows
	@RequestMapping(value = "/weixinQrPay", method = RequestMethod.GET)
	public void wxpay(HttpServletResponse response) {
		WxpayQrServiceBO serviceBO = new WxpayQrServiceBO();
		serviceBO.setTotalFee("1");
		serviceBO.setBody("这是商品名称");
		serviceBO.setAttach("这是附加信息");
		serviceBO.setOutTradeNo(RandomUtil.randomAlphabetic(10));
		PayQrDTO qrPay = payService.qrPay(PayTypeEnum.WXPAY.getDescription(), serviceBO);
		if (StringUtil.isNotBlank(qrPay.getErrorMsg())) {
			String errorJSON = JsonUtil.toJson(R.failure(qrPay.getErrorMsg()));

			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setCharacterEncoding(GlobalConstant.UTF_8);
			response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);
			PrintWriter out = response.getWriter();
			out.print(errorJSON);
			return;
		}
		qrGenerateService.generateQrToOutputStream(qrPay.getQrContent(), response);
	}

	/**
	 * 微信回调内容格式
	 * <xml>
	 * <appid><![CDATA[wx4a5d8c9b7a497aac]]></appid>
	 * <attach><![CDATA[这是附加信息]]></attach>
	 * <bank_type><![CDATA[CITIC_CREDIT]]></bank_type>
	 * <cash_fee><![CDATA[1]]></cash_fee>
	 * <fee_type><![CDATA[CNY]]></fee_type>
	 * <is_subscribe><![CDATA[Y]]></is_subscribe>
	 * <mch_id><![CDATA[1515647571]]></mch_id>
	 * <nonce_str><![CDATA[HiSZIiFJlX]]></nonce_str>
	 * <openid><![CDATA[oHCHJ1eZkrkXW43_Eo0PjYjKZe4M]]></openid>
	 * <out_trade_no><![CDATA[123456789123]]></out_trade_no>
	 * <result_code><![CDATA[SUCCESS]]></result_code>
	 * <return_code><![CDATA[SUCCESS]]></return_code>
	 * <sign><![CDATA[6C51E9D2E281DC12F0585B2DB3D74ECEA707543266EA6F3BB64DBAAC2EDE7062]]></sign>
	 * <time_end><![CDATA[20200619002030]]></time_end>
	 * <total_fee>1</total_fee>
	 * <trade_type><![CDATA[NATIVE]]></trade_type>
	 * <transaction_id><![CDATA[4200000607202006194647437991]]></transaction_id>
	 * </xml>
	 */
	@SneakyThrows
	@RequestMapping(value = "/open/wxpayCallback", method = {RequestMethod.POST, RequestMethod.GET})
	public void wxpayCallback(HttpServletRequest request, HttpServletResponse response) {
		String xmlMsg = HttpKit.readData(request);
		log.info("wxpayCallback 支付回调内容：<{}>", xmlMsg);
		Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);
		String returnCode = params.get("return_code");

		// 注意此处签名方式需与统一下单的签名类型一致
		if (WxPayKit.verifyNotify(params, wxpayParamProperties.getPartnerKey(), SignType.HMACSHA256)) {
			// 验签成功
			if (WxPayKit.codeIsOk(returnCode)) {
				// zchtodo 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
				// zchtodo 业务操作：模拟更新订单信息
				// 回复腾讯服务器
				Map<String, String> xml = new HashMap<String, String>(2);
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");

				response.setStatus(HttpStatus.OK.value());
				response.setCharacterEncoding(GlobalConstant.UTF_8);
				response.setContentType("text/xml");

				PrintWriter out = response.getWriter();
				out.print(WxPayKit.toXml(xml));
			}
		}
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================
}
