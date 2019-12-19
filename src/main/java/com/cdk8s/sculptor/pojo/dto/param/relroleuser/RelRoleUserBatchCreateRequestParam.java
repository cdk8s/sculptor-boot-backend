package com.cdk8s.sculptor.pojo.dto.param.relroleuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "RelRoleUserBatchCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelRoleUserBatchCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "角色ID列表")
	@NotEmpty(message = "角色ID不能为空")
	@Size(min = 1, message = "角色ID至少需要一个元素")
	private List<Long> roleIdList;

	@ApiModelProperty(value = "用户ID列表")
	@NotEmpty(message = "用户ID不能为空")
	@Size(min = 1, message = "用户ID至少需要一个元素")
	private List<Long> userIdList;


}
