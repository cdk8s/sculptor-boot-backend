/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDeptBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysdept.bases;

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
public class SysDeptBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String deptName;

	private String deptCode;

	private Long parentId;

	private String parentIds;

	private Long leaderUserId;

	private String telephone;

	private String mobilePhone;

	private String deptFax;

	private String deptAddress;

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
