/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysdict.bases;

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
public class SysDictUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "字典名称长度不正确")
	private String dictName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "字典编码长度不正确")
	private String dictCode;

	@Range(min = 1, max = 5, message = "字典值类型数值不正确")
	private Integer dictValueTypeEnum;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "备注长度不正确")
	private String description;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
