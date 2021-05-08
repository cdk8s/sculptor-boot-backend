/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysloginlog.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLoginLogPageQueryBaseParam extends PageParam {

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

	@Range(min = 1, max = 2, message = "是否登录成功数值不正确")
	private Integer boolLoginSuccessEnum;

	@Range(min = 1, max = 2, message = "当前是否在线数值不正确")
	private Integer boolNowOnlineEnum;

	@Range(min = 1, max = 2, message = "登出方式数值不正确")
	private Integer offlineTypeEnum;

	private String exceptionMsg;

	private Long tenantId;

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

	@Range(min = 1, max = 2, message = "是否是新用户数值不正确")
	private Integer boolNewUserEnum;

	@Range(min = 1, max = 6, message = "登录来源数值不正确")
	private Integer loginOriginEnum;

	private Long createUserId;


	private Long loginDateStartDate;
	private Long loginDateEndDate;

	private Long logoutDateStartDate;
	private Long logoutDateEndDate;


}
