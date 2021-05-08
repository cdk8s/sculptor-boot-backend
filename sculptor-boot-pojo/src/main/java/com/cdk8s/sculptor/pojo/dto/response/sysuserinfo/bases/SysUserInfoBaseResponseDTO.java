/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysuserinfo.bases;

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
public class SysUserInfoBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long userId;

	private String weixinOpenid;

	private String weixinUnionid;

	private String weixinUserinfo;

	private String idCard;


	private Long createDate;

	private Long createUserId;

	private String createUsername;

	private Long updateDate;

	private Long updateUserId;

	private String updateUsername;


}
