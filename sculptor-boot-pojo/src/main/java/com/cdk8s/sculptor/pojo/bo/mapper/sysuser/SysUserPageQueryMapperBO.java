/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserPageQueryMapperBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.mapper.sysuser;

import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.bases.SysUserPageQueryBaseMapperBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserPageQueryMapperBO extends SysUserPageQueryBaseMapperBO {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	private Long deptId;
	private List<Long> deptIdList;

	private Long roleId;

	// ==============非 entity 属性 end==============

}
