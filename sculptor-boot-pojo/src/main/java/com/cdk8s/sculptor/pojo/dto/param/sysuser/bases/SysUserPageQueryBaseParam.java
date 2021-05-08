/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserPageQueryBaseParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuser.bases;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserPageQueryBaseParam extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;

	private String username;

	private String nickname;

	private String realName;

	private String userPassword;

	private String passwordSalt;

	private String userEmail;

	private String telephone;

	private String mobilePhone;

	private String avatarUrl;

	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderEnum;

	@Range(min = 1, max = 4, message = "用户类型数值不正确")
	private Integer userTypeEnum;

	private Long tenantId;

	@Range(min = 1, max = 4, message = "注册方式数值不正确")
	private Integer registerTypeEnum;

	@Range(min = 1, max = 6, message = "注册来源数值不正确")
	private Integer registerOriginEnum;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;

	private Long createUserId;


}
