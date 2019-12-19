package com.cdk8s.sculptor.pojo.dto.response.sysloginlog;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysLoginLogResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysLoginLogResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "用户账号")
	private String username;

	@ApiModelProperty(value = "客户端账号")
	private String clientId;

	@ApiModelProperty(value = "登录成功令牌")
	private String token;

	@ApiModelProperty(value = "记录信息")
	private String message;

	@ApiModelProperty(value = "登录时间")
	private Long loginDate;

	@ApiModelProperty(value = "登出时间")
	private Long logoutDate;

	@ApiModelProperty(value = "请求 URL")
	private String requestUrl;

	@ApiModelProperty(value = "是否登录成功")
	private Integer boolLoginSuccessEnum;

	@ApiModelProperty(value = "是否登录成功")
	private String boolLoginSuccessEnumString;

	@ApiModelProperty(value = "当前是否在线")
	private Integer boolNowOnlineEnum;

	@ApiModelProperty(value = "当前是否在线")
	private String boolNowOnlineEnumString;

	@ApiModelProperty(value = "登出方式")
	private Integer offlineTypeEnum;

	@ApiModelProperty(value = "登出方式")
	private String offlineTypeEnumString;

	@ApiModelProperty(value = "失败异常信息")
	private String exceptionMsg;

	@ApiModelProperty(value = "IP 地址")
	private String ipAddress;

	@ApiModelProperty(value = "IP 信息")
	private String ipRegion;

	@ApiModelProperty(value = "IP 地址对应的国家")
	private String ipRegionCountry;

	@ApiModelProperty(value = "IP 地址对应的省")
	private String ipRegionProvince;

	@ApiModelProperty(value = "IP 地址对应的市")
	private String ipRegionCity;

	@ApiModelProperty(value = "IP 地址对应的网络提供商")
	private String ipRegionIsp;

	@ApiModelProperty(value = "浏览器 UserAgent")
	private String userAgent;

	@ApiModelProperty(value = "设备名称")
	private String deviceName;

	@ApiModelProperty(value = "系统名称")
	private String osName;

	@ApiModelProperty(value = "浏览器")
	private String browserName;

	@ApiModelProperty(value = "语言区域")
	private String browserLocale;

	@ApiModelProperty(value = "是否是新用户")
	private Integer boolNewUserEnum;

	@ApiModelProperty(value = "是否是新用户")
	private String boolNewUserEnumString;

	@ApiModelProperty(value = "登录来源")
	private Integer loginOriginEnum;

	@ApiModelProperty(value = "登录来源")
	private String loginOriginEnumString;


}
