/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysjob.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysJobBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String jobName;

	private String jobGroup;

	private String invokeTarget;

	private String cronExpression;

	private Integer misfirePolicyEnum;

	private String misfirePolicyEnumString;

	private Integer boolSupportConcurrentEnum;

	private String boolSupportConcurrentEnumString;

	private Integer ranking;

	private String description;

	private Integer stateEnum;

	private String stateEnumString;


	private Long createDate;

	private Long createUserId;

	private String createUsername;

	private Long updateDate;

	private Long updateUserId;

	private String updateUsername;


}
