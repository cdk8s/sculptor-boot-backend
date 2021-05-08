/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserBatchCreateServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.reldeptuser.bases;

import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseCreateServiceBO;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class RelDeptUserBatchCreateServiceBO extends BaseCreateServiceBO {

	private static final long serialVersionUID = -1L;

	private List<Long> deptIdList;

	private List<Long> userIdList;


}
