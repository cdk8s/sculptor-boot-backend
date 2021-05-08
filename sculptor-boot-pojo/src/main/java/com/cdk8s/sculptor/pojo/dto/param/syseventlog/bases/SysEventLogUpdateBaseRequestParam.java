/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syseventlog.bases;

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
public class SysEventLogUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	private Long userId;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "用户账号长度不正确")
	private String username;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "记录信息长度不正确")
	private String message;

	private Long executeTime;

	private Long requestDate;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "请求 URL长度不正确")
	private String requestUrl;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "请求方法名长度不正确")
	private String requestMethod;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "请求参数长度不正确")
	private String requestParam;

	@Range(min = 1, max = 2, message = "是否执行成功数值不正确")
	private Integer boolExecuteSuccessEnum;

	@Range(min = 1, max = 7, message = "事件类型数值不正确")
	private Integer operateTypeEnum;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "失败异常信息长度不正确")
	private String exceptionMsg;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址长度不正确")
	private String ipAddress;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "IP 信息长度不正确")
	private String ipRegion;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址对应的国家长度不正确")
	private String ipRegionCountry;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址对应的省长度不正确")
	private String ipRegionProvince;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址对应的市长度不正确")
	private String ipRegionCity;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "IP 地址对应的网络提供商长度不正确")
	private String ipRegionIsp;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "浏览器 UserAgent长度不正确")
	private String userAgent;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "设备名称长度不正确")
	private String deviceName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "系统名称长度不正确")
	private String osName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "浏览器长度不正确")
	private String browserName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "语言区域长度不正确")
	private String browserLocale;


}
