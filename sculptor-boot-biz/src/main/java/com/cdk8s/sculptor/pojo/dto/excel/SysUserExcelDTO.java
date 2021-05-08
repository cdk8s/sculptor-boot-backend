/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserExcelDTO.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserExcelDTO extends BaseRowModel implements Serializable {

	private static final long serialVersionUID = -1L;

	@ExcelProperty(value = "登录名", index = 0)
	private String username;

	@ExcelProperty(value = "真实姓名", index = 1)
	private String realName;

	@ExcelProperty(value = "邮箱", index = 2)
	private String userEmail;

	@ExcelProperty(value = "固话", index = 3)
	private String telephone;

	@ExcelProperty(value = "手机号", index = 4)
	private String mobilePhone;

}
