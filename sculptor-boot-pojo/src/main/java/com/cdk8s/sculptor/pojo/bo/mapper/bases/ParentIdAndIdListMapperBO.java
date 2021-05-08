/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ParentIdAndIdListMapperBO.java
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
public class ParentIdAndIdListMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long parentId;
	private List<Long> parentIdList;
	private List<Long> idList;

	public ParentIdAndIdListMapperBO(Long parentId, List<Long> idList) {
		this.parentId = parentId;
		this.idList = idList;
	}

	public ParentIdAndIdListMapperBO(List<Long> parentIdList, List<Long> idList) {
		this.parentIdList = parentIdList;
		this.idList = idList;
	}
}
