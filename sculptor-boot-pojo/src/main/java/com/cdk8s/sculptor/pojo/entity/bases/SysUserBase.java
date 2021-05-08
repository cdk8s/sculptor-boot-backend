/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserBase.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.entity.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserBase extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String username;
	private String nickname;
	private String realName;
	private String userPassword;
	private String passwordSalt;
	private String userEmail;
	private String telephone;
	private String mobilePhone;
	private String avatarUrl;
	private Integer genderEnum;
	private Integer userTypeEnum;
	private Long tenantId;
	private Integer registerTypeEnum;
	private Integer registerOriginEnum;

	private Integer stateEnum;

	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;

}

