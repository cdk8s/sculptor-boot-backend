/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionResponseDTOToVue3.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.syspermission;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@Setter
@Getter
@ToString(callSuper = true)
public class SysPermissionResponseDTOToVue3 implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long menuId;
	private Long parentId;
	private String title;
	private String icon;
	private String path;
	private String component;
	private String target;
	private String listPath;
	private int hide;
	private List<SysPermissionResponseDTOToVue3> children;
}
