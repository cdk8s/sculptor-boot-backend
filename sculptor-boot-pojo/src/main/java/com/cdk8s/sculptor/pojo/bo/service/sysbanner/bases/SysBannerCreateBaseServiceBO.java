/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerCreateBaseServiceBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.service.sysbanner.bases;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.bo.service.bases.BaseCreateServiceBO;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysBannerCreateBaseServiceBO extends BaseCreateServiceBO {

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
	private Integer deleteEnum;
	private Long tenantId;
	private Long createDate;
	private Long createUserId;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

}
