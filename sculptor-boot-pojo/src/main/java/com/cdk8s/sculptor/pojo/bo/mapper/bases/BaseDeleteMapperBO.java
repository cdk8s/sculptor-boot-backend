/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BaseDeleteMapperBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseDeleteMapperBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long tenantId;
	private Long deleteDate;
	private Long deleteUserId;
	private Integer deleteEnum;

	// zchtodo 重新生成代码后要删除掉这个
	private Long updateDate;
	private Long updateUserId;
}
