/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBigTextCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysbigtext.bases;

import com.cdk8s.sculptor.validator.NotXss;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysBigTextCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "文本标题不能为空")
	@Length(max = 250, message = "文本标题长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String textTitle;

	@NotBlank(message = "文本编码不能为空")
	@Length(max = 50, message = "文本编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String textCode;

	@NotBlank(message = "内容不能为空")
	@NotXss
	private String textContent;

	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
