/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysuser;

import com.cdk8s.sculptor.pojo.dto.response.sysuser.bases.SysUserBaseResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserResponseDTO extends SysUserBaseResponseDTO {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	private List<String> deptNameList;

	private List<String> roleNameList;

	// ==============非 entity 属性 end==============

}
