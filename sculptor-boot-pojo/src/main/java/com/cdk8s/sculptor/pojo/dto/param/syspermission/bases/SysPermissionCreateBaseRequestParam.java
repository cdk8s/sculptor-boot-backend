/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionCreateBaseRequestParam.java
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionCreateBaseRequestParam extends BaseCreateParam {

	private static final long serialVersionUID = -1L;

	@NotBlank(message = "权限名称不能为空")
	@Length(max = 50, message = "权限名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionName;

	@NotBlank(message = "权限标识码不能为空")
	@Length(max = 50, message = "权限标识码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionCode;

	@Length(max = 500, message = "路由地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionUrl;

	@NotNull(message = "归属类型不能为空")
	@Range(min = 1, max = 3, message = "归属类型数值不正确")
	private Integer belongTypeEnum;

	@NotNull(message = "权限类型不能为空")
	@Range(min = 1, max = 3, message = "权限类型数值不正确")
	private Integer permissionTypeEnum;

	@NotNull(message = "父ID不能为空")
	private Long parentId;

	@Length(max = 250, message = "权限图标长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String iconClass;

	@NotNull(message = "显示状态不能为空")
	@Range(min = 1, max = 2, message = "显示状态数值不正确")
	private Integer visibleEnum;

	@NotNull(message = "是否外链不能为空")
	@Range(min = 1, max = 2, message = "是否外链数值不正确")
	private Integer boolExtLinkEnum;

	@NotNull(message = "是否新标签打开不能为空")
	@Range(min = 1, max = 2, message = "是否新标签打开数值不正确")
	private Integer boolNewTabEnum;

	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@Length(max = 500, message = "备注长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String description;

	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
