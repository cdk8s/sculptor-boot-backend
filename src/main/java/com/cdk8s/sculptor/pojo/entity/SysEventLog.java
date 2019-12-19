package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysEventLog extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private String username;
	private String message;
	private Long executeTime;
	private Long requestDate;
	private String requestUrl;
	private String requestMethod;
	private String requestParam;
	private Integer boolExecuteSuccessEnum;
	private Integer operateTypeEnum;
	private String exceptionMsg;
	private String ipAddress;
	private String ipRegion;
	private String ipRegionCountry;
	private String ipRegionProvince;
	private String ipRegionCity;
	private String ipRegionIsp;
	private String userAgent;
	private String deviceName;
	private String osName;
	private String browserName;
	private String browserLocale;


}

