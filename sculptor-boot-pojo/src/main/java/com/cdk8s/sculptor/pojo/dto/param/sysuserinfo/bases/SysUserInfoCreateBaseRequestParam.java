/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuserinfo.bases;

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
public class SysUserInfoCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "用户ID不能为空")
	private Long userId;

	@Length(max = 40, message = "微信openid长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String weixinOpenid;

	@Length(max = 40, message = "微信unionid长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String weixinUnionid;

	@Length(max = 800, message = "微信用户信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String weixinUserinfo;

	@Length(max = 30, message = "身份证号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String idCard;


}
