/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoBase.java
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
public class SysFileInfoBase extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long folderId;
	private String fileShowName;
	private String fileStorageName;
	private String fileSuffix;
	private String fileStoragePath;
	private String fileFullUrl;
	private Integer fileStorageTypeEnum;
	private Long fileSize;
	private Integer ranking;
	private String description;
	private Integer boolTopEnum;
	private Integer boolOssCompleteEnum;
	private Integer boolOssDeleteEnum;
	private Long tenantId;

	private Integer stateEnum;

	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;

}

