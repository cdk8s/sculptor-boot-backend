/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogBase.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.enums.BooleanEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Transient;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysSmsLoginLogBase extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private String userMobilePhone;
	private String verificationCode;
	private Integer smsProviderTypeEnum;
	private Integer boolServiceStateEnum;
	private String messageContent;
	private Integer boolUseEnum;
	private String ipAddress;
	private String userAgent;
	private Long tenantId;



	private Long createDate;
	private Long createUserId;


}

