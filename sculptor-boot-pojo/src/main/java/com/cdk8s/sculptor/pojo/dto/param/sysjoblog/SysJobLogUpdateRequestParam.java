/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogUpdateRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysjoblog;

import com.cdk8s.sculptor.pojo.dto.param.sysjoblog.bases.SysJobLogUpdateBaseRequestParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysJobLogUpdateRequestParam extends SysJobLogUpdateBaseRequestParam {

	private static final long serialVersionUID = -1L;


}
