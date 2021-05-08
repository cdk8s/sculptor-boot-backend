/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syseventlog.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;
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
public class SysEventLogCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	private Long userId;

	@Length(max = 50, message = "用户账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String username;

	@Length(max = 250, message = "记录信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String message;

	private Long executeTime;

	private Long requestDate;

	@Length(max = 500, message = "请求 URL长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String requestUrl;

	@Length(max = 50, message = "请求方法名长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String requestMethod;

	@Length(max = 500, message = "请求参数长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String requestParam;

	@NotNull(message = "是否执行成功不能为空")
	@Range(min = 1, max = 2, message = "是否执行成功数值不正确")
	private Integer boolExecuteSuccessEnum;

	@NotNull(message = "事件类型不能为空")
	@Range(min = 1, max = 7, message = "事件类型数值不正确")
	private Integer operateTypeEnum;

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


}
