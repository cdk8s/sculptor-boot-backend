/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuser.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "用户账号长度不正确")
	private String username;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "昵称长度不正确")
	private String nickname;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "真实姓名长度不正确")
	private String realName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "登录密码长度不正确")
	private String userPassword;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "邮箱地址长度不正确")
	private String userEmail;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 20, message = "固话长度不正确")
	private String telephone;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 20, message = "手机号长度不正确")
	private String mobilePhone;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "头像长度不正确")
	private String avatarUrl;

	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderEnum;

	@Range(min = 1, max = 4, message = "用户类型数值不正确")
	private Integer userTypeEnum;

	@Range(min = 1, max = 4, message = "注册方式数值不正确")
	private Integer registerTypeEnum;

	@Range(min = 1, max = 6, message = "注册来源数值不正确")
	private Integer registerOriginEnum;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
