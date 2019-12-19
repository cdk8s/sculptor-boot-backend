package com.cdk8s.sculptor.pojo.dto.param.sysparamtype;

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

@ApiModel(value = "SysParamTypeCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysParamTypeCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "类型名称")
	@NotBlank(message = "类型名称不能为空")
	@Length(max = 50, message = "类型名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String typeName;

	@ApiModelProperty(value = "类型编码")
	@NotBlank(message = "类型编码不能为空")
	@Length(max = 100, message = "类型编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String typeCode;

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
