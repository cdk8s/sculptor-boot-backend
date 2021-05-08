/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogPageQueryParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysloginlog;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import org.hibernate.validator.constraints.Range;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.bases.SysLoginLogPageQueryBaseParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLoginLogPageQueryParam extends SysLoginLogPageQueryBaseParam {

	private static final long serialVersionUID = -1L;



}
