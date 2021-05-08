/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionUpdateBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.syspermission.bases;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseUpdateServiceBO;


@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysPermissionUpdateBaseServiceBO extends BaseUpdateServiceBO{

	private static final long serialVersionUID = -1L;

	private Long id;
	private String permissionName;
	private String permissionCode;
	private String permissionUrl;
	private Integer belongTypeEnum;
	private Integer permissionTypeEnum;
	private Long parentId;
	private String parentIds;
	private String iconClass;
	private Integer visibleEnum;
	private Integer boolExtLinkEnum;
	private Integer boolNewTabEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long tenantId;
	private Long updateDate;
	private Long updateUserId;

}
