package com.cdk8s.sculptor.pojo.dto.param.reldeptuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "RelDeptUserCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelDeptUserCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "部门ID")
	@NotNull(message = "部门ID不能为空")
	private Long deptId;

	@ApiModelProperty(value = "用户ID")
	@NotNull(message = "用户ID不能为空")
	private Long userId;


}
