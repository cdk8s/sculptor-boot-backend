/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserDeptIdAndUserIdToDeleteRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.reldeptuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseDeleteParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;


@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelDeptUserDeptIdAndUserIdToDeleteRequestParam extends BaseDeleteParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "deptId 不能为空")
	private Long deptId;

	@NotNull(message = "userId 不能为空")
	private Long userId;

}
