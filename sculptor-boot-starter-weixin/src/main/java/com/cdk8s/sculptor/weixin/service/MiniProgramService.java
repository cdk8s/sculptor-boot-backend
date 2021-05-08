/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：MiniProgramService.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.service;

import com.cdk8s.sculptor.util.okhttp.HttpService;
import com.cdk8s.sculptor.weixin.pojo.response.MiniProgramCodeToSessionResponse;
import com.cdk8s.sculptor.weixin.properties.WeixinProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@Service
public class MiniProgramService {

	@Autowired
	private HttpService<MiniProgramCodeToSessionResponse> httpService;

	@Autowired
	private WeixinProperties weixinProperties;

	// =====================================查询业务 start=====================================


	public MiniProgramCodeToSessionResponse codeToSession(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
		url = MessageFormat.format(url, weixinProperties.getMiniProgramAppId(), weixinProperties.getMiniProgramAppSecret(), code);
		return httpService.getBySuccessKey(url, MiniProgramCodeToSessionResponse.class, "获取微信小程序 openid", "session_key");
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================

}

