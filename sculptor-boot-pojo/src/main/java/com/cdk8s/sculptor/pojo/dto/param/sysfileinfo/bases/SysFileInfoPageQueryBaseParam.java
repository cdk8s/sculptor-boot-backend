/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.bases;

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
public class SysFileInfoPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private Long folderId;

	private String fileShowName;

	private String fileStorageName;

	private String fileSuffix;

	private String fileStoragePath;

	private String fileFullUrl;

	@Range(min = 1, max = 2, message = "存储渠道数值不正确")
	private Integer fileStorageTypeEnum;

	private Long fileSize;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	private String description;

	@Range(min = 1, max = 2, message = "是否置顶数值不正确")
	private Integer boolTopEnum;

	@Range(min = 1, max = 2, message = "是否完成上传数值不正确")
	private Integer boolOssCompleteEnum;

	@Range(min = 1, max = 2, message = "OSS真实删除状态数值不正确")
	private Integer boolOssDeleteEnum;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;

	private Long tenantId;

	private Long createUserId;



}
