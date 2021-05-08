/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.syscityarea;

import com.cdk8s.sculptor.pojo.dto.response.syscityarea.bases.SysCityAreaBaseResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysCityAreaResponseDTO extends SysCityAreaBaseResponseDTO {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	private Integer boolParentEnum;

	private String label;// 节点名称（前端组件需要）
	private Long value;// 节点值（前端组件需要）
	private Boolean isLeaf;//是否是子节点（前端组件需要）

	private List<SysCityAreaResponseDTO> children;

	// ==============非 entity 属性 end==============

}
