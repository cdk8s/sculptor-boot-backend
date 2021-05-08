/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinService.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.service;

import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.code.CodecUtil;
import com.cdk8s.sculptor.util.okhttp.HttpService;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.weixin.constant.WeixinConstant;
import com.cdk8s.sculptor.weixin.pojo.param.WeixinCreateQrParam;
import com.cdk8s.sculptor.weixin.pojo.response.WeixinAccessTokenResponse;
import com.cdk8s.sculptor.weixin.pojo.response.WeixinCreateQrResponse;
import com.cdk8s.sculptor.weixin.pojo.response.WeixinUserInfoResponse;
import com.cdk8s.sculptor.weixin.properties.WeixinProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class WeixinService {

	@Autowired
	private WeixinProperties weixinProperties;

	@Autowired
	private HttpService<WeixinAccessTokenResponse> weixinAccessTokenHttpService;

	@Autowired
	private HttpService<WeixinCreateQrResponse> weixinCreateQrHttpService;

	@Autowired
	private HttpService<WeixinUserInfoResponse> weixinUserInfoHttpService;

	@Autowired
	private StringRedisService<String, String> weixinAccessTokenRedisService;

	@Autowired
	private StringRedisService<String, String> weixinQrTicketRedisService;

	// =====================================查询业务 start=====================================

	public String getAccessToken() {
		String accessToken = weixinAccessTokenRedisService.get(WeixinConstant.REDIS_TOKEN_KEY);
		if (StringUtil.isNotBlank(accessToken)) {
			return accessToken;
		}
		Map<String, String> queries = new HashMap<>();
		queries.put("grant_type", "client_credential");
		queries.put("appid", weixinProperties.getAppId());
		queries.put("secret", weixinProperties.getAppSecret());

		WeixinAccessTokenResponse weixinAccessTokenResponse = weixinAccessTokenHttpService.get(WeixinConstant.TOKEN_URL, queries, null, WeixinAccessTokenResponse.class, "获取微信 AccessToken 接口", WeixinConstant.WEIXIN_API_RESPONSE_ERROR_KEY);
		String result = weixinAccessTokenResponse.getAccess_token();

		weixinAccessTokenRedisService.set(WeixinConstant.REDIS_TOKEN_KEY, result, WeixinConstant.ACCESS_TOKEN_EXPIRE_TIME_SECOND);
		return result;
	}


	public String getQrTicket(String uuid) {
		String qrKey = WeixinConstant.REDIS_QR_KEY_PREFIX + uuid;
		weixinQrTicketRedisService.set(qrKey, uuid, WeixinConstant.QR_EXPIRE_TIME_SECOND);

		WeixinCreateQrParam weixinCreateQrParam = new WeixinCreateQrParam();
		weixinCreateQrParam.setExpire_seconds(WeixinConstant.QR_EXPIRE_TIME_SECOND);
		weixinCreateQrParam.setAction_name("QR_STR_SCENE");

		WeixinCreateQrParam.ActionInfoBean.SceneBean sceneBean = new WeixinCreateQrParam.ActionInfoBean.SceneBean();
		sceneBean.setScene_str(uuid);

		WeixinCreateQrParam.ActionInfoBean actionInfoBean = new WeixinCreateQrParam.ActionInfoBean();
		actionInfoBean.setScene(sceneBean);

		weixinCreateQrParam.setAction_info(actionInfoBean);

		String jsonParam = JsonUtil.toJson(weixinCreateQrParam);
		String url = WeixinConstant.CREATE_QR_CODE_TICKET_URL + getAccessToken();
		WeixinCreateQrResponse weixinCreateQrResponse = weixinCreateQrHttpService.postByJSON(url, jsonParam, null, WeixinCreateQrResponse.class, "获取微信临时二维码接口", WeixinConstant.WEIXIN_API_RESPONSE_ERROR_KEY);

		return weixinCreateQrResponse.getTicket();
	}

	public WeixinUserInfoResponse getUserInfo(String openid) {
		Map<String, String> map = new HashMap<>();
		map.put("access_token", getAccessToken());
		map.put("openid", openid);
		map.put("lang", "zh_CN");
		WeixinUserInfoResponse result = weixinUserInfoHttpService.get(WeixinConstant.USER_INFO_URL, map, null, WeixinUserInfoResponse.class, "获取微信用户信息接口", WeixinConstant.WEIXIN_API_RESPONSE_ERROR_KEY);
		return result;
	}




	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================

}

