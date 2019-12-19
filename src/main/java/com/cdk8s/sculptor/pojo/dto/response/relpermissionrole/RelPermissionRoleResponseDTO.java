package com.cdk8s.sculptor.pojo.dto.response.relpermissionrole;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "RelPermissionRoleResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class RelPermissionRoleResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "权限ID")
	private Long permissionId;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;


	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;


}
