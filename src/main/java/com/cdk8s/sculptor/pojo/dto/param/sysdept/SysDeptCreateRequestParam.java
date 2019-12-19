package com.cdk8s.sculptor.pojo.dto.param.sysdept;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysDeptCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDeptCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "部门名称")
	@NotBlank(message = "部门名称不能为空")
	@Length(max = 50, message = "部门名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deptName;

	@ApiModelProperty(value = "部门编码")
	@NotBlank(message = "部门编码不能为空")
	@Length(max = 50, message = "部门编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deptCode;

	@ApiModelProperty(value = "父ID")
	@NotNull(message = "父ID不能为空")
	private Long parentId;

	@ApiModelProperty(value = "部门领导用户ID")
	private Long leaderUserId;

	@ApiModelProperty(value = "固话")
	@Length(max = 50, message = "固话长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String telephone;

	@ApiModelProperty(value = "手机号")
	@Length(max = 50, message = "手机号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String mobilePhone;

	@ApiModelProperty(value = "传真")
	@Length(max = 50, message = "传真长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deptFax;

	@ApiModelProperty(value = "地址")
	@Length(max = 250, message = "地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String deptAddress;

	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	@Length(max = 250, message = "描述长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String description;

	@ApiModelProperty(value = "启用状态")
	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
