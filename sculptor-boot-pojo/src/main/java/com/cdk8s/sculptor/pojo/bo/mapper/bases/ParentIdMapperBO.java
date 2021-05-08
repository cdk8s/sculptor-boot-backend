/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ParentIdMapperBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ParentIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long parentId;
	private List<Long> parentIdList;

	public ParentIdMapperBO(Long parentId) {
		this.parentId = parentId;
	}

	public ParentIdMapperBO(List<Long> parentIdList) {
		this.parentIdList = parentIdList;
	}
}
