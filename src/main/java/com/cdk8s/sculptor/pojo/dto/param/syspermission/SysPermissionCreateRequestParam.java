package com.cdk8s.sculptor.pojo.dto.param.syspermission;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysPermissionCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "权限名称")
	@NotBlank(message = "权限名称不能为空")
	@Length(max = 50, message = "权限名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionName;

	@ApiModelProperty(value = "权限标识码")
	@NotBlank(message = "权限标识码不能为空")
	@Length(max = 50, message = "权限标识码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionCode;

	@ApiModelProperty(value = "路由地址")
	@Length(max = 500, message = "路由地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String permissionUrl;

	@ApiModelProperty(value = "权限类型")
	@NotNull(message = "权限类型不能为空")
	@Range(min = 1, max = 3, message = "权限类型数值不正确")
	private Integer permissionTypeEnum;

	@ApiModelProperty(value = "父ID")
	@NotNull(message = "父ID不能为空")
	private Long parentId;

	@ApiModelProperty(value = "权限图标")
	@Length(max = 250, message = "权限图标长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String iconClass;

	@ApiModelProperty(value = "显示状态")
	@NotNull(message = "显示状态不能为空")
	@Range(min = 1, max = 2, message = "显示状态数值不正确")
	private Integer visibleEnum;

	@ApiModelProperty(value = "是否外链")
	@NotNull(message = "是否外链不能为空")
	@Range(min = 1, max = 2, message = "是否外链数值不正确")
	private Integer boolExtLinkEnum;

	@ApiModelProperty(value = "是否新标签打开")
	@NotNull(message = "是否新标签打开不能为空")
	@Range(min = 1, max = 2, message = "是否新标签打开数值不正确")
	private Integer boolNewTabEnum;

	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	@Length(max = 250, message = "描述长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String description;

	@ApiModelProperty(value = "启用状态")
	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
