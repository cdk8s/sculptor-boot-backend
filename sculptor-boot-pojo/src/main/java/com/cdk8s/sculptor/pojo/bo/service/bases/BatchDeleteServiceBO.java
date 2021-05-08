/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BatchDeleteServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.bases;

import com.cdk8s.sculptor.enums.DeleteEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BatchDeleteServiceBO extends BaseDeleteServiceBO {

	private static final long serialVersionUID = -1L;

	// 必须有的字段
	private List<Long> idList;

	// 非必须字段
	private Integer deleteEnum = DeleteEnum.DELETED.getCode();
	private Long deleteDate;
	private Long deleteUserId;

	private Long updateDate;
	private Long updateUserId;

}
