/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthProperties.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.properties;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 604800s = 7 天
 * 86400s = 24 小时
 * 43200s = 12 小时
 */
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "tkey.sso.oauth")
public class OauthProperties {

	private String errorUriMsg = "See the full API docs at https://github.com/cdk8s";
	private Integer nodeNumber = 10;
	private Boolean tgcCookieSecure = true;
	private Integer rememberMeMaxTimeToLiveInSeconds = 604800;
	private Integer codeMaxTimeToLiveInSeconds = 120;
	private Integer accessTokenMaxTimeToLiveInSeconds = 43200;
	private Integer refreshTokenMaxTimeToLiveInSeconds = 86400;
	private Integer tgcAndUserInfoMaxTimeToLiveInSeconds = 86400;
	private List<String> enableGrantTypeList;

	public List<String> getEnableGrantTypeList() {
		if (CollectionUtil.isEmpty(enableGrantTypeList)) {
			enableGrantTypeList = new ArrayList<>();
			enableGrantTypeList.add(GlobalConstant.OAUTH_CLIENT_GRANT_TYPE);
			enableGrantTypeList.add(GlobalConstant.OAUTH_CODE_GRANT_TYPE);
			enableGrantTypeList.add(GlobalConstant.OAUTH_PASSWORD_GRANT_TYPE);
			enableGrantTypeList.add(GlobalConstant.OAUTH_TOKEN_GRANT_TYPE);
			enableGrantTypeList.add(GlobalConstant.OAUTH_REFRESH_TOKEN_GRANT_TYPE);
		}
		return enableGrantTypeList;
	}


}
