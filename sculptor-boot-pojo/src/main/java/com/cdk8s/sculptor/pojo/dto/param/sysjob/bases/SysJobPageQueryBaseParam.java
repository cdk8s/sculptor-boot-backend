/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjob.bases;

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
public class SysJobPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private String jobName;

	private String jobGroup;

	private String invokeTarget;

	private String cronExpression;

	@Range(min = 1, max = 4, message = "计划执行错误策略数值不正确")
	private Integer misfirePolicyEnum;

	@Range(min = 1, max = 2, message = "是否并发执行数值不正确")
	private Integer boolSupportConcurrentEnum;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	private String description;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;

	private Long tenantId;

	private Long createUserId;



}
