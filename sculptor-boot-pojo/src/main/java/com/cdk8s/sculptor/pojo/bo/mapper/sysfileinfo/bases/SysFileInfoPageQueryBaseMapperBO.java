/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoPageQueryBaseMapperBO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.bo.mapper.sysfileinfo.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.cdk8s.sculptor.enums.DeleteEnum;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysFileInfoPageQueryBaseMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
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
	private Integer stateEnum;
	private Long tenantId;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
