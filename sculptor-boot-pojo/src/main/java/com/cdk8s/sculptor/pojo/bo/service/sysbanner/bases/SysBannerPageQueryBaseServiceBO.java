/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerPageQueryBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.sysbanner.bases;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysBannerPageQueryBaseServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String bannerTitle;
	private String bannerDescription;
	private String bannerCode;
	private String coverPcImageUrl;
	private String coverH5ImageUrl;
	private BigDecimal bannerStandDate;
	private Integer bannerJumpTypeEnum;
	private String jumpH5Url;
	private String jumpPcUrl;
	private String jumpTypeCode;
	private Long jumpObjectId;
	private Integer ranking;
	private Long tenantId;
	private Long createUserId;

	private BigDecimal bannerStandDateStartDate;
	private BigDecimal bannerStandDateEndDate;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
