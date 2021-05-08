/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BaseEntity.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	protected Long id;

	protected Long tenantId;

	protected Long createDate;
	protected Long createUserId;
}
