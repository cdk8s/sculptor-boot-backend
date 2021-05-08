/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinConstant.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.constant;


public interface WeixinConstant {


	//=====================================biz start=====================================

	String REDIS_TOKEN_KEY = "WEIXIN:ACCESS_TOKEN";
	String REDIS_QR_KEY_PREFIX = "WEIXIN:CREATE_QR:";

	Integer QR_EXPIRE_TIME_SECOND = 1800;

	Long ACCESS_TOKEN_EXPIRE_TIME_SECOND = 7000L;

	String WEIXIN_API_RESPONSE_ERROR_KEY = "errcode";
	String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	String CREATE_QR_CODE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
	String CREATE_QR_CODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	//=====================================biz end=====================================

}
