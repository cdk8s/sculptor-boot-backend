/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerCreateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysbanner.bases;

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
public class SysBannerCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "标题不能为空")
	@Length(max = 50, message = "标题长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String bannerTitle;

	@Length(max = 250, message = "介绍长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String bannerDescription;

	@Length(max = 100, message = "编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String bannerCode;

	@Length(max = 500, message = "PC封面图片长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String coverPcImageUrl;

	@Length(max = 500, message = "移动端封面图片长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String coverH5ImageUrl;

	@NotNull(message = "停留时间不能为空")
	private BigDecimal bannerStandDate;

	@NotNull(message = "跳转类型不能为空")
	@Range(min = 1, max = 2, message = "跳转类型数值不正确")
	private Integer bannerJumpTypeEnum;

	@Length(max = 500, message = "移动端跳转url地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jumpH5Url;

	@Length(max = 500, message = "PC端跳转url地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jumpPcUrl;

	@Length(max = 50, message = "跳转业务类型编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jumpTypeCode;

	private Long jumpObjectId;

	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;


}
