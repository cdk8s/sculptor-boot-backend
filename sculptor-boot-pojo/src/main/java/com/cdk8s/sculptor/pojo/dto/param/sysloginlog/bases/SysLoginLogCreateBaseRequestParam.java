/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysloginlog.bases;

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
public class SysLoginLogCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "用户ID不能为空")
	private Long userId;

	@NotBlank(message = "用户账号不能为空")
	@Length(max = 50, message = "用户账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String username;

	@Length(max = 50, message = "客户端账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String clientId;

	@Length(max = 50, message = "登录成功令牌长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String token;

	@Length(max = 250, message = "记录信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String message;

	private Long loginDate;

	private Long logoutDate;

	@Length(max = 500, message = "请求 URL长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String requestUrl;

	@NotNull(message = "是否登录成功不能为空")
	@Range(min = 1, max = 2, message = "是否登录成功数值不正确")
	private Integer boolLoginSuccessEnum;

	@NotNull(message = "当前是否在线不能为空")
	@Range(min = 1, max = 2, message = "当前是否在线数值不正确")
	private Integer boolNowOnlineEnum;

	@Range(min = 1, max = 2, message = "登出方式数值不正确")
	private Integer offlineTypeEnum;

	@Length(max = 250, message = "失败异常信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String exceptionMsg;

	@Length(max = 50, message = "IP 地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipAddress;

	@Length(max = 100, message = "IP 信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegion;

	@Length(max = 50, message = "IP 地址对应的国家长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionCountry;

	@Length(max = 50, message = "IP 地址对应的省长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionProvince;

	@Length(max = 50, message = "IP 地址对应的市长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionCity;

	@Length(max = 50, message = "IP 地址对应的网络提供商长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionIsp;

	@Length(max = 500, message = "浏览器 UserAgent长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userAgent;

	@Length(max = 50, message = "设备名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deviceName;

	@Length(max = 50, message = "系统名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String osName;

	@Length(max = 50, message = "浏览器长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String browserName;

	@Length(max = 50, message = "语言区域长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String browserLocale;

	@NotNull(message = "是否是新用户不能为空")
	@Range(min = 1, max = 2, message = "是否是新用户数值不正确")
	private Integer boolNewUserEnum;

	@NotNull(message = "登录来源不能为空")
	@Range(min = 1, max = 6, message = "登录来源数值不正确")
	private Integer loginOriginEnum;


}
