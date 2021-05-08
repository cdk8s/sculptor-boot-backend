/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthTokenStrategyHandleBO.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.handle.oauth;

import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OauthTokenStrategyHandleBO {
	private String userInfoRedisKey;
	private OauthUserAttribute userAttribute;
}
