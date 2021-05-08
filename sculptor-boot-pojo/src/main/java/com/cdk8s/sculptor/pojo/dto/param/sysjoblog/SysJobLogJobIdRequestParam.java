/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogJobIdRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjoblog;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseQueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysJobLogJobIdRequestParam extends BaseQueryParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "任务ID不能为空")
	private Long jobId;


}
