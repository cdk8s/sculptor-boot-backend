package com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysLoginLogPageQueryMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
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

	private Long loginDateStartDate;
	private Long loginDateEndDate;

	private Long logoutDateStartDate;
	private Long logoutDateEndDate;
}
