/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjob.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysJobCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "任务名称不能为空")
	@Length(max = 50, message = "任务名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jobName;

	@NotBlank(message = "任务组名不能为空")
	@Length(max = 50, message = "任务组名长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jobGroup;

	@NotBlank(message = "调用目标字符串不能为空")
	@Length(max = 500, message = "调用目标字符串长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String invokeTarget;

	@NotBlank(message = "cron 执行表达式不能为空")
	@Length(max = 50, message = "cron 执行表达式长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String cronExpression;

	@NotNull(message = "计划执行错误策略不能为空")
	@Range(min = 1, max = 4, message = "计划执行错误策略数值不正确")
	private Integer misfirePolicyEnum;

	@NotNull(message = "是否并发执行不能为空")
	@Range(min = 1, max = 2, message = "是否并发执行数值不正确")
	private Integer boolSupportConcurrentEnum;

	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@Length(max = 500, message = "备注长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String description;

	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
