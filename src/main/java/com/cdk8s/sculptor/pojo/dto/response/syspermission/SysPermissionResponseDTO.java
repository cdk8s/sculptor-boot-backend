package com.cdk8s.sculptor.pojo.dto.response.syspermission;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "SysPermissionResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysPermissionResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "权限名称")
	private String permissionName;

	@ApiModelProperty(value = "权限标识码")
	private String permissionCode;

	@ApiModelProperty(value = "路由地址")
	private String permissionUrl;

	@ApiModelProperty(value = "权限类型")
	private Integer permissionTypeEnum;

	@ApiModelProperty(value = "权限类型")
	private String permissionTypeEnumString;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "父ID集")
	private String parentIds;

	@ApiModelProperty(value = "权限图标")
	private String iconClass;

	@ApiModelProperty(value = "显示状态")
	private Integer visibleEnum;

	@ApiModelProperty(value = "显示状态")
	private String visibleEnumString;

	@ApiModelProperty(value = "是否外链")
	private Integer boolExtLinkEnum;

	@ApiModelProperty(value = "是否外链")
	private String boolExtLinkEnumString;

	@ApiModelProperty(value = "是否新标签打开")
	private Integer boolNewTabEnum;

	@ApiModelProperty(value = "是否新标签打开")
	private String boolNewTabEnumString;

	@ApiModelProperty(value = "排序")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "启用状态")
	private Integer stateEnum;

	@ApiModelProperty(value = "启用状态")
	private String stateEnumString;


	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;

	@ApiModelProperty(value = "更新时间")
	private Long updateDate;

	@ApiModelProperty(value = "更新人ID")
	private Long updateUserId;

	@ApiModelProperty(value = "更新人")
	private String updateUsername;

	@ApiModelProperty(value = "是否是父节点")
	private Integer boolParentEnum;

	@ApiModelProperty(value = "子节点列表")
	private List<SysPermissionResponseDTO> children;

}
