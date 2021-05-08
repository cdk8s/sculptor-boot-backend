/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PayAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay;

import com.cdk8s.sculptor.pay.properties.AlipayParamProperties;
import com.cdk8s.sculptor.pay.properties.WxpayParamProperties;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.wxpay.WxPayApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.cdk8s.sculptor.pay"})
@ConditionalOnClass({AliPayApi.class, WxPayApi.class})
@EnableConfigurationProperties({AlipayParamProperties.class, WxpayParamProperties.class})
public class PayAutoConfiguration {

}
