package com.cdk8s.sculptor.pojo.bo.service.syseventlog;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysEventLogUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
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
