/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogCreateBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.sysjoblog.bases;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseCreateServiceBO;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysJobLogCreateBaseServiceBO extends BaseCreateServiceBO {

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
	private Integer boolExecuteSuccessEnum;
	private Long jobStartDate;
	private Long jobEndDate;
	private Long executeTime;
	private Long createDate;
	private Long createUserId;

}
