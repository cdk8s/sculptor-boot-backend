/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjoblog.bases;

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
public class SysJobLogPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private Long jobId;

	private String jobName;

	private String jobGroup;

	private String invokeTarget;

	private String cronExpression;

	private String jobMessage;

	private String exceptionMsg;

	private Long tenantId;

	private String description;

	@Range(min = 1, max = 2, message = "是否执行成功数值不正确")
	private Integer boolExecuteSuccessEnum;

	private Long jobStartDate;

	private Long jobEndDate;

	private Long executeTime;

	private Long createUserId;


	private Long jobStartDateStartDate;
	private Long jobStartDateEndDate;

	private Long jobEndDateStartDate;
	private Long jobEndDateEndDate;


}
