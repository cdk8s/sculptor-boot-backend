/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.bases;

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
public class SysFileInfoBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long folderId;

	private String fileShowName;

	private String fileStorageName;

	private String fileSuffix;

	private String fileStoragePath;

	private String fileFullUrl;

	private Integer fileStorageTypeEnum;

	private String fileStorageTypeEnumString;

	private Long fileSize;

	private Integer ranking;

	private String description;

	private Integer boolTopEnum;

	private String boolTopEnumString;

	private Integer boolOssCompleteEnum;

	private String boolOssCompleteEnumString;

	private Integer boolOssDeleteEnum;

	private String boolOssDeleteEnumString;

	private Integer stateEnum;

	private String stateEnumString;


	private Long createDate;

	private Long createUserId;

	private String createUsername;

	private Long updateDate;

	private Long updateUserId;

	private String updateUsername;


}
