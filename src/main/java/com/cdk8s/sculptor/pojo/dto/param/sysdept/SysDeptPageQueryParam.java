package com.cdk8s.sculptor.pojo.dto.param.sysdept;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ApiModel(value = "SysDeptPageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDeptPageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "父ID集")
	private String parentIds;

	@ApiModelProperty(value = "部门领导用户ID")
	private Long leaderUserId;

	@ApiModelProperty(value = "固话")
	private String telephone;

	@ApiModelProperty(value = "手机号")
	private String mobilePhone;

	@ApiModelProperty(value = "传真")
	private String deptFax;

	@ApiModelProperty(value = "地址")
	private String deptAddress;

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
