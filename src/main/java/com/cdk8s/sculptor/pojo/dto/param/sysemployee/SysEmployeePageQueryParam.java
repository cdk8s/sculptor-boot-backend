package com.cdk8s.sculptor.pojo.dto.param.sysemployee;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysEmployeePageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysEmployeePageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "工号")
	private String workCardId;

	@ApiModelProperty(value = "职位")
	private String jobPosition;

	@ApiModelProperty(value = "创建人")
	private Long createUserId;

	// ==============非 entity 属性 start==============

	private String username;
	private String realName;

	// ==============非 entity 属性 end==============

}
