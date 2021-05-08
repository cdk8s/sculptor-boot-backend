/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.bases;

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
public class SysSmsLoginLogPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private Long userId;

	private String userMobilePhone;

	private String verificationCode;

	@Range(min = 1, max = 2, message = "服务商类型数值不正确")
	private Integer smsProviderTypeEnum;

	@Range(min = 1, max = 2, message = "服务商是否已验证数值不正确")
	private Integer boolServiceStateEnum;

	private String messageContent;

	@Range(min = 1, max = 2, message = "是否已验证数值不正确")
	private Integer boolUseEnum;

	private String ipAddress;

	private String userAgent;

	private Long tenantId;

	private Long createUserId;



}
