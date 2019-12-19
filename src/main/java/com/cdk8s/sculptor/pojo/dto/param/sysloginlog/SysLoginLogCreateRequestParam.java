package com.cdk8s.sculptor.pojo.dto.param.sysloginlog;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysLoginLogCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLoginLogCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户ID")
	@NotNull(message = "用户ID不能为空")
	private Long userId;

	@ApiModelProperty(value = "用户账号")
	@NotBlank(message = "用户账号不能为空")
	@Length(max = 50, message = "用户账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String username;

	@ApiModelProperty(value = "客户端账号")
	@Length(max = 50, message = "客户端账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String clientId;

	@ApiModelProperty(value = "登录成功令牌")
	@Length(max = 50, message = "登录成功令牌长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String token;

	@ApiModelProperty(value = "记录信息")
	@Length(max = 250, message = "记录信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String message;

	@ApiModelProperty(value = "登录时间")
	private Long loginDate;

	@ApiModelProperty(value = "登出时间")
	private Long logoutDate;

	@ApiModelProperty(value = "请求 URL")
	@Length(max = 500, message = "请求 URL长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String requestUrl;

	@ApiModelProperty(value = "是否登录成功")
	@NotNull(message = "是否登录成功不能为空")
	@Range(min = 1, max = 2, message = "是否登录成功数值不正确")
	private Integer boolLoginSuccessEnum;

	@ApiModelProperty(value = "当前是否在线")
	@NotNull(message = "当前是否在线不能为空")
	@Range(min = 1, max = 2, message = "当前是否在线数值不正确")
	private Integer boolNowOnlineEnum;

	@ApiModelProperty(value = "登出方式")
	@Range(min = 1, max = 2, message = "登出方式数值不正确")
	private Integer offlineTypeEnum;

	@ApiModelProperty(value = "失败异常信息")
	@Length(max = 250, message = "失败异常信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String exceptionMsg;

	@ApiModelProperty(value = "IP 地址")
	@Length(max = 50, message = "IP 地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipAddress;

	@ApiModelProperty(value = "IP 信息")
	@Length(max = 100, message = "IP 信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegion;

	@ApiModelProperty(value = "IP 地址对应的国家")
	@Length(max = 50, message = "IP 地址对应的国家长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionCountry;

	@ApiModelProperty(value = "IP 地址对应的省")
	@Length(max = 50, message = "IP 地址对应的省长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionProvince;

	@ApiModelProperty(value = "IP 地址对应的市")
	@Length(max = 50, message = "IP 地址对应的市长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionCity;

	@ApiModelProperty(value = "IP 地址对应的网络提供商")
	@Length(max = 50, message = "IP 地址对应的网络提供商长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String ipRegionIsp;

	@ApiModelProperty(value = "浏览器 UserAgent")
	@Length(max = 250, message = "浏览器 UserAgent长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userAgent;

	@ApiModelProperty(value = "设备名称")
	@Length(max = 50, message = "设备名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deviceName;

	@ApiModelProperty(value = "系统名称")
	@Length(max = 50, message = "系统名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String osName;

	@ApiModelProperty(value = "浏览器")
	@Length(max = 50, message = "浏览器长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String browserName;

	@ApiModelProperty(value = "语言区域")
	@Length(max = 50, message = "语言区域长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String browserLocale;

	@ApiModelProperty(value = "是否是新用户")
	@NotNull(message = "是否是新用户不能为空")
	@Range(min = 1, max = 2, message = "是否是新用户数值不正确")
	private Integer boolNewUserEnum;

	@ApiModelProperty(value = "登录来源")
	@NotNull(message = "登录来源不能为空")
	@Range(min = 1, max = 6, message = "登录来源数值不正确")
	private Integer loginOriginEnum;


}
