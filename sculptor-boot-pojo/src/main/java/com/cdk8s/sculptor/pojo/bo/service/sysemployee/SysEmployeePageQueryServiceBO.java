/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEmployeePageQueryServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.sysemployee;

import com.cdk8s.sculptor.pojo.bo.service.sysemployee.bases.SysEmployeePageQueryBaseServiceBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysEmployeePageQueryServiceBO extends SysEmployeePageQueryBaseServiceBO {

	private static final long serialVersionUID = -1L;

	// ==============非 entity 属性 start==============

	private String username;
	private String realName;

	// ==============非 entity 属性 end==============

}
