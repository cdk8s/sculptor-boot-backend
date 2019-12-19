package com.cdk8s.sculptor.pojo.dto.param.reldeptuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "RelDeptUserPageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelDeptUserPageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "部门ID")
	private Long deptId;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "创建人")
	private Long createUserId;


}
