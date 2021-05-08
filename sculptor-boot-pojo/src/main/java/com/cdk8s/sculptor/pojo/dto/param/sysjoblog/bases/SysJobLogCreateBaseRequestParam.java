/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjoblog.bases;

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
public class SysJobLogCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "任务ID不能为空")
	private Long jobId;

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

	@Length(max = 250, message = "日志信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jobMessage;

	@Length(max = 250, message = "异常信息长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String exceptionMsg;

	@Length(max = 500, message = "备注长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String description;

	@NotNull(message = "是否执行成功不能为空")
	@Range(min = 1, max = 2, message = "是否执行成功数值不正确")
	private Integer boolExecuteSuccessEnum;

	private Long jobStartDate;

	private Long jobEndDate;

	private Long executeTime;


}
