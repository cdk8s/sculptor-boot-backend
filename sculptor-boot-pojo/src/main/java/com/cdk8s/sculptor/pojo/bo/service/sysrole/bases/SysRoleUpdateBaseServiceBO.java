/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysRoleUpdateBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.sysrole.bases;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseUpdateServiceBO;


@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysRoleUpdateBaseServiceBO extends BaseUpdateServiceBO{

	private static final long serialVersionUID = -1L;

	private Long id;
	private String roleName;
	private String roleCode;
	private Integer ranking;
	private String description;
	private Integer boolTemplateEnum;
	private Integer stateEnum;
	private Long tenantId;
	private Long updateDate;
	private Long updateUserId;

}
