/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinQrLoginAccessTokenToUserInfoResponse.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.pojo.response;

import com.cdk8s.sculptor.enums.GenderEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class WeixinQrLoginAccessTokenToUserInfoResponse implements Serializable {

	private static final long serialVersionUID = -1L;

	private String openid;
	private String nickname;
	private int sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private String unionid;
	private List<String> privilege;

	public int getSex() {
		int result;
		switch (sex) {
			case 1:
				result = GenderEnum.MALE.getCode();
				break;
			case 2:
				result = GenderEnum.FEMALE.getCode();
				break;
			default:
				result = GenderEnum.PRIVACY.getCode();
				break;
		}
		return result;
	}

}
