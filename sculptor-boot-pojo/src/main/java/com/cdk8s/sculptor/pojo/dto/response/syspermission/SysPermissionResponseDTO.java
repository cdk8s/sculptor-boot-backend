/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.syspermission;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.pojo.dto.response.syspermission.bases.SysPermissionBaseResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysPermissionResponseDTO extends SysPermissionBaseResponseDTO {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	private Integer boolParentEnum;

	private String label;// 节点名称（前端组件需要）
	private Long value;// 节点值（前端组件需要）
	private Boolean isLeaf;//是否是子节点（前端组件需要）

	private List<SysPermissionResponseDTO> children;

	// ==============非 entity 属性 end==============

}
