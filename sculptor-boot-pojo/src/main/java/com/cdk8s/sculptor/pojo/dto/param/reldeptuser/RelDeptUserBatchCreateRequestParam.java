/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserBatchCreateRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.reldeptuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelDeptUserBatchCreateRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotEmpty(message = "部门ID不能为空")
	@Size(min = 1, message = "部门ID至少需要一个元素")
	private List<Long> deptIdList;

	@NotEmpty(message = "用户ID不能为空")
	@Size(min = 1, message = "用户ID至少需要一个元素")
	private List<Long> userIdList;


}
