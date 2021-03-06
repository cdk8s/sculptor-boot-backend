/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syscityarea.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysCityAreaUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	private Long parentId;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "地区名称长度不正确")
	private String areaName;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;


}
