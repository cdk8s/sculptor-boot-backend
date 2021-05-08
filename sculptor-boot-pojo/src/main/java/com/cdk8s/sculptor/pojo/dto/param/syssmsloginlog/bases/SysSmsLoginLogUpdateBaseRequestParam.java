/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysSmsLoginLogUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	private Long userId;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 20, message = "用户手机号长度不正确")
	private String userMobilePhone;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 10, message = "短信验证码长度不正确")
	private String verificationCode;

	@Range(min = 1, max = 2, message = "服务商类型数值不正确")
	private Integer smsProviderTypeEnum;

	@Range(min = 1, max = 2, message = "服务商是否已验证数值不正确")
	private Integer boolServiceStateEnum;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "短信完整内容长度不正确")
	private String messageContent;

	@Range(min = 1, max = 2, message = "是否已验证数值不正确")
	private Integer boolUseEnum;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址长度不正确")
	private String ipAddress;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "浏览器 UserAgent长度不正确")
	private String userAgent;


}
