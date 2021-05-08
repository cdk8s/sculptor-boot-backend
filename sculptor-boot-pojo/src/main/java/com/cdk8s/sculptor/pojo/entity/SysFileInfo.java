/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfo.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.SysFileInfoBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Setter
@Getter
@ToString(callSuper = true)
public class SysFileInfo extends SysFileInfoBase {

	private static final long serialVersionUID = -1L;


	// ==============非 entity 属性 start==============

	@Transient
	private String fileFullUrlByThumbnail;

	@Transient
	private String fileFullUrlByWatermark;

	@Transient
	private String fileSizeWithUnit;

	// ==============非 entity 属性 end==============

}

