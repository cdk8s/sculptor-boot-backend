/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserUpdateBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.reldeptuser.bases;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseUpdateServiceBO;


@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class RelDeptUserUpdateBaseServiceBO extends BaseUpdateServiceBO{

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long deptId;
	private Long userId;
	private Long tenantId;

}
