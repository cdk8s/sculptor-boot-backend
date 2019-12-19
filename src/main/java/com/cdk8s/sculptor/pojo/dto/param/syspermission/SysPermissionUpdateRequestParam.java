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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysPermissionUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "权限名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "权限名称长度不正确")
	private String permissionName;

	@ApiModelProperty(value = "权限标识码")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "权限标识码长度不正确")
	private String permissionCode;

	@ApiModelProperty(value = "路由地址")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 500, message = "路由地址长度不正确")
	private String permissionUrl;

	@ApiModelProperty(value = "权限类型")
	@Range(min = 1, max = 3, message = "权限类型数值不正确")
	private Integer permissionTypeEnum;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "权限图标")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "权限图标长度不正确")
	private String iconClass;

	@ApiModelProperty(value = "显示状态")
	@Range(min = 1, max = 2, message = "显示状态数值不正确")
	private Integer visibleEnum;

	@ApiModelProperty(value = "是否外链")
	@Range(min = 1, max = 2, message = "是否外链数值不正确")
	private Integer boolExtLinkEnum;

	@ApiModelProperty(value = "是否新标签打开")
	@Range(min = 1, max = 2, message = "是否新标签打开数值不正确")
	private Integer boolNewTabEnum;

	@ApiModelProperty(value = "排序")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "描述长度不正确")
	private String description;

	@ApiModelProperty(value = "启用状态")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
