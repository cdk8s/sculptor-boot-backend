/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.bases;

import com.cdk8s.sculptor.validator.NotXss;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysSmsLoginLogCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "用户ID不能为空")
	private Long userId;

	@NotBlank(message = "用户手机号不能为空")
	@Length(max = 20, message = "用户手机号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userMobilePhone;

	@NotBlank(message = "短信验证码不能为空")
	@Length(max = 10, message = "短信验证码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String verificationCode;

	@NotNull(message = "服务商类型不能为空")
	@Range(min = 1, max = 2, message = "服务商类型数值不正确")
	private Integer smsProviderTypeEnum;

	@NotNull(message = "服务商是否已验证不能为空")
	@Range(min = 1, max = 2, message = "服务商是否已验证数值不正确")
	private Integer boolServiceStateEnum;

	@NotBlank(message = "短信完整内容不能为空")
	@Length(max = 50, message = "短信完整内容长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String messageContent;

	@NotNull(message = "是否已验证不能为空")
	@Range(min = 1, max = 2, message = "是否已验证数值不正确")
	private Integer boolUseEnum;

	@Length(max = 50, message = "IP 地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipAddress;

	@Length(max = 500, message = "浏览器 UserAgent长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userAgent;


}
