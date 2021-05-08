/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysjoblog.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysJobLogBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long jobId;

	private String jobName;

	private String jobGroup;

	private String invokeTarget;

	private String cronExpression;

	private String jobMessage;

	private String exceptionMsg;

	private String description;

	private Integer boolExecuteSuccessEnum;

	private String boolExecuteSuccessEnumString;

	private Long jobStartDate;

	private Long jobEndDate;

	private Long executeTime;


	private Long createDate;

	private Long createUserId;

	private String createUsername;



}
