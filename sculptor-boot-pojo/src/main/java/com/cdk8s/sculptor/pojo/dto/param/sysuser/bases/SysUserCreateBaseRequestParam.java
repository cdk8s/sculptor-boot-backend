/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuser.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "用户账号不能为空")
	@Length(max = 50, message = "用户账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String username;

	@Length(max = 100, message = "昵称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String nickname;

	@Length(max = 50, message = "真实姓名长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String realName;

	@NotBlank(message = "登录密码不能为空")
	@Length(max = 50, message = "登录密码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userPassword;

	@Length(max = 50, message = "邮箱地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userEmail;

	@Length(max = 20, message = "固话长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String telephone;

	@Length(max = 20, message = "手机号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String mobilePhone;

	@Length(max = 500, message = "头像长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String avatarUrl;

	@NotNull(message = "性别不能为空")
	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderEnum;

	@NotNull(message = "用户类型不能为空")
	@Range(min = 1, max = 4, message = "用户类型数值不正确")
	private Integer userTypeEnum;

	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
