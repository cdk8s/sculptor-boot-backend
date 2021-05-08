/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerBase.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.enums.BooleanEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Transient;
import java.util.List;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class SysBannerBase extends BaseEntity {

	private static final long serialVersionUID = -1L;

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


	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;

}

