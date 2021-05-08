/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PayStrategyInterface.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.strategy;

import com.cdk8s.sculptor.pay.dto.*;

public interface PayStrategyInterface {
	// 生成二维码支付
	PayQrDTO qrPay(Object serviceBO);

	// H5 支付（用户在微信以外的手机浏览器请求微信支付的场景唤起微信支付）
	PayWapDTO wapPay(Object serviceBO);

	// 公众号支付（用户通过微信扫码、关注公众号等方式进入商家H5页面，并在微信内调用JSSDK完成支付）
	PayMpDTO mpPay(Object serviceBO);

	// 小程序支付（用户在微信小程序中使用微信支付的场景）
	PayMiniDTO miniPay(Object serviceBO);

	// APP支付
	PayAppDTO appPay(Object serviceBO);
}
