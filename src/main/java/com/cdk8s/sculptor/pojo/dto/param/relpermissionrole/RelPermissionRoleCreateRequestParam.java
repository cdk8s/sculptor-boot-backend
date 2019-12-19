package com.cdk8s.sculptor.pojo.dto.param.relpermissionrole;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "RelPermissionRoleCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelPermissionRoleCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "权限ID")
	@NotNull(message = "权限ID不能为空")
	private Long permissionId;

	@ApiModelProperty(value = "角色ID")
	@NotNull(message = "角色ID不能为空")
	private Long roleId;


}
