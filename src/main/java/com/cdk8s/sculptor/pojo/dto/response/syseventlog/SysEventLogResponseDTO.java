package com.cdk8s.sculptor.pojo.dto.response.syseventlog;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysEventLogResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysEventLogResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "用户账号")
	private String username;

	@ApiModelProperty(value = "记录信息")
	private String message;

	@ApiModelProperty(value = "执行时间")
	private Long executeTime;

	@ApiModelProperty(value = "访问时间")
	private Long requestDate;

	@ApiModelProperty(value = "请求 URL")
	private String requestUrl;

	@ApiModelProperty(value = "请求方法名")
	private String requestMethod;

	@ApiModelProperty(value = "请求参数")
	private String requestParam;

	@ApiModelProperty(value = "是否执行成功")
	private Integer boolExecuteSuccessEnum;

	@ApiModelProperty(value = "是否执行成功")
	private String boolExecuteSuccessEnumString;

	@ApiModelProperty(value = "是否执行成功")
	private Integer operateTypeEnum;

	@ApiModelProperty(value = "是否执行成功")
	private String operateTypeEnumString;

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

	@ApiModelProperty(value = "浏览器 userAgent")
	private String userAgent;

	@ApiModelProperty(value = "设备名称")
	private String deviceName;

	@ApiModelProperty(value = "系统名称")
	private String osName;

	@ApiModelProperty(value = "浏览器")
	private String browserName;

	@ApiModelProperty(value = "语言区域")
	private String browserLocale;


}
