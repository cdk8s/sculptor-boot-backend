/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysbanner.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysBannerBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String bannerTitle;

	private String bannerDescription;

	private String bannerCode;

	private String coverPcImageUrl;

	private String coverH5ImageUrl;

	private BigDecimal bannerStandDate;

	private Integer bannerJumpTypeEnum;

	private String bannerJumpTypeEnumString;

	private String jumpH5Url;

	private String jumpPcUrl;

	private String jumpTypeCode;

	private Long jumpObjectId;

	private Integer ranking;


	private Long createDate;

	private Long createUserId;

	private String createUsername;

	private Long updateDate;

	private Long updateUserId;

	private String updateUsername;


}
