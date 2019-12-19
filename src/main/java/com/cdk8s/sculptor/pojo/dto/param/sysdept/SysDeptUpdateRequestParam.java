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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysDeptUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDeptUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "部门名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "部门名称长度不正确")
	private String deptName;

	@ApiModelProperty(value = "部门编码")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "部门编码长度不正确")
	private String deptCode;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "部门领导用户ID")
	private Long leaderUserId;

	@ApiModelProperty(value = "固话")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "固话长度不正确")
	private String telephone;

	@ApiModelProperty(value = "手机号")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "手机号长度不正确")
	private String mobilePhone;

	@ApiModelProperty(value = "传真")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "传真长度不正确")
	private String deptFax;

	@ApiModelProperty(value = "地址")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "地址长度不正确")
	private String deptAddress;

	@ApiModelProperty(value = "排序")
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "描述长度不正确")
	private String description;

	@ApiModelProperty(value = "启用状态")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
