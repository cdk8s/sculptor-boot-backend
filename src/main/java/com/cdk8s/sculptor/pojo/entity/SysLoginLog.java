package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysLoginLog extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private String username;
	private String clientId;
	private String token;
	private String message;
	private Long loginDate;
	private Long logoutDate;
	private String requestUrl;
	private Integer boolLoginSuccessEnum;
	private Integer boolNowOnlineEnum;
	private Integer offlineTypeEnum;
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
	private Integer boolNewUserEnum;
	private Integer loginOriginEnum;


}

