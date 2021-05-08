/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysbanner.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysBannerPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private String bannerTitle;

	private String bannerDescription;

	private String bannerCode;

	private String coverPcImageUrl;

	private String coverH5ImageUrl;

	private BigDecimal bannerStandDate;

	@Range(min = 1, max = 2, message = "跳转类型数值不正确")
	private Integer bannerJumpTypeEnum;

	private String jumpH5Url;

	private String jumpPcUrl;

	private String jumpTypeCode;

	private Long jumpObjectId;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	private Long tenantId;

	private Long createUserId;


	private BigDecimal bannerStandDateStartDate;
	private BigDecimal bannerStandDateEndDate;


}
