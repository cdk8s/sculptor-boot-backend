/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogPageQueryBaseMapperBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.cdk8s.sculptor.enums.DeleteEnum;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysEventLogPageQueryBaseMapperBO extends PageParam {

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
