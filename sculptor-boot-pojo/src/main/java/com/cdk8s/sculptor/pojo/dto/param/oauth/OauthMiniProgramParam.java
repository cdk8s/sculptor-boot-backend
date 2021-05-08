/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthMiniProgramParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.oauth;

import com.cdk8s.sculptor.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 这是我们自己实现的登录方式，oauth 标准没有这个，所以这里就采用驼峰命名，不使用下划线命名了
 * {
 * "code": "111111111111111111",
 * "userInfo": {
 * "nickName": "Meek",
 * "gender": 1,
 * "language": "zh_CN",
 * "city": "Guangzhou",
 * "province": "Guangdong",
 * "country": "China",
 * "avatarUrl": "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqiatkAS7Gq5koHnkric0eR3mfxOY3NJMvVLicKQyRpU8HWvTqQ7Ofqw3oBo9s4icMLb2iak0YzFdR2l9A/132"
 * }
 * }
 */
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthMiniProgramParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "code 不能为空")
	private String code;

	@Valid
	@NotNull(message = "userInfo 不能为空")
	private UserInfoBean userInfo;

	@NoArgsConstructor
	@Setter
	@Getter
	public static class UserInfoBean {
		@NotBlank(message = "微信昵称不能为空")
		private String nickName;
		private int gender;// 0 为知，1男性，2女性
		private String language;
		private String city;
		private String province;
		private String country;
		@NotBlank(message = "微信头像不能为空")
		private String avatarUrl;


		public int getGender() {
			int result;
			switch (gender) {
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
}

