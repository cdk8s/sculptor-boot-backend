/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermission.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.pojo.entity.bases.SysPermissionBase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Transient;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysPermission extends SysPermissionBase {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	@Transient
	private Integer boolParentEnum = BooleanEnum.NO.getCode();

	@Transient
	private String label;// 节点名称（前端组件需要）

	@Transient
	private Long value;// 节点值（前端组件需要）

	@Transient
	private Boolean isLeaf;//是否是子节点（前端组件需要）

	@Transient
	private List<SysPermission> children;

	// ==============非 entity 属性 end==============

}

