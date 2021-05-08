/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionUpdateBaseRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.syspermission.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionUpdateBaseRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "权限名称长度不正确")
	private String permissionName;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "权限标识码长度不正确")
	private String permissionCode;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "路由地址长度不正确")
	private String permissionUrl;

	@Range(min = 1, max = 3, message = "归属类型数值不正确")
	private Integer belongTypeEnum;

	@Range(min = 1, max = 3, message = "权限类型数值不正确")
	private Integer permissionTypeEnum;

	private Long parentId;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "权限图标长度不正确")
	private String iconClass;

	@Range(min = 1, max = 2, message = "显示状态数值不正确")
	private Integer visibleEnum;

	@Range(min = 1, max = 2, message = "是否外链数值不正确")
	private Integer boolExtLinkEnum;

	@Range(min = 1, max = 2, message = "是否新标签打开数值不正确")
	private Integer boolNewTabEnum;

	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "备注长度不正确")
	private String description;

	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
