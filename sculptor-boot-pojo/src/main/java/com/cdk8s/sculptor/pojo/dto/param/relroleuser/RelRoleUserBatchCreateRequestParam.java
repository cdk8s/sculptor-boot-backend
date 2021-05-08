/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserBatchCreateRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.relroleuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelRoleUserBatchCreateRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotEmpty(message = "角色ID不能为空")
	@Size(min = 1, message = "角色ID至少需要一个元素")
	private List<Long> roleIdList;

	@NotEmpty(message = "用户ID不能为空")
	@Size(min = 1, message = "用户ID至少需要一个元素")
	private List<Long> userIdList;


}
