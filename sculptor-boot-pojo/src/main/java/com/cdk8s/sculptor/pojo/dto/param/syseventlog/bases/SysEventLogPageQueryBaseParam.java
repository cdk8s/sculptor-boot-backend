/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syseventlog.bases;

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
public class SysEventLogPageQueryBaseParam extends PageParam {

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

	@Range(min = 1, max = 2, message = "是否执行成功数值不正确")
	private Integer boolExecuteSuccessEnum;

	@Range(min = 1, max = 7, message = "事件类型数值不正确")
	private Integer operateTypeEnum;

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

	private Long createUserId;


	private Long requestDateStartDate;
	private Long requestDateEndDate;


}
