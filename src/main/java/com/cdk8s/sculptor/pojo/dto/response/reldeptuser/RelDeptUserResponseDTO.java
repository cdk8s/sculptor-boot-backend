package com.cdk8s.sculptor.pojo.dto.response.reldeptuser;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "RelDeptUserResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class RelDeptUserResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "部门ID")
	private Long deptId;

	@ApiModelProperty(value = "用户ID")
	private Long userId;


	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;


}
