/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AlipayQrServiceBO.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AlipayQrServiceBO {
	private String totalAmount;
	private String subject;
	private String storeId;
	private String timeoutExpress = "30m";
	private String outTradeNo;
}
