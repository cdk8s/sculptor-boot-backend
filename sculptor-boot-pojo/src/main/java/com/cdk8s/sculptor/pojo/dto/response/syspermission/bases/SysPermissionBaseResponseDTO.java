/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.syspermission.bases;

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
public class SysPermissionBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String permissionName;

	private String permissionCode;

	private String permissionUrl;

	private Integer belongTypeEnum;

	private String belongTypeEnumString;

	private Integer permissionTypeEnum;

	private String permissionTypeEnumString;

	private Long parentId;

	private String parentIds;

	private String iconClass;

	private Integer visibleEnum;

	private String visibleEnumString;

	private Integer boolExtLinkEnum;

	private String boolExtLinkEnumString;

	private Integer boolNewTabEnum;

	private String boolNewTabEnumString;

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
