/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoBatchUpdateRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuserinfo;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserInfoBatchUpdateRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@Valid
	@NotEmpty(message = "paramList 不能为空")
	@Size(min = 1, message = "paramList 至少需要一个元素")
	private List<SysUserInfoUpdateRequestParam> paramList;

}
