package com.cdk8s.sculptor.pojo.dto.param.syspermission;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ApiModel(value = "SysPermissionPageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionPageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "权限名称")
	private String permissionName;

	@ApiModelProperty(value = "权限标识码")
	private String permissionCode;

	@ApiModelProperty(value = "路由地址")
	private String permissionUrl;

	@ApiModelProperty(value = "权限类型")
	@Range(min = 1, max = 3, message = "权限类型数值不正确")
	private Integer permissionTypeEnum;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "父ID集")
	private String parentIds;

	@ApiModelProperty(value = "权限图标")
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
	private String description;

	@ApiModelProperty(value = "启用状态")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;

	@ApiModelProperty(value = "创建人")
	private Long createUserId;


}
