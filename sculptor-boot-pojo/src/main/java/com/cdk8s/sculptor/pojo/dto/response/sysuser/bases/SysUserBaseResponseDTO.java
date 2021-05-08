/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserBaseResponseDTO.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.response.sysuser.bases;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserBaseResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String username;

	private String nickname;

	private String realName;

	private String userEmail;

	private String telephone;

	private String mobilePhone;

	private String avatarUrl;

	private Integer genderEnum;

	private String genderEnumString;

	private Integer userTypeEnum;

	private String userTypeEnumString;

	private Integer registerTypeEnum;

	private String registerTypeEnumString;

	private Integer registerOriginEnum;

	private String registerOriginEnumString;

	private Integer stateEnum;

	private String stateEnumString;


	private Long createDate;

	private Long createUserId;

	private String createUsername;

	private Long updateDate;

	private Long updateUserId;

	private String updateUsername;


}
