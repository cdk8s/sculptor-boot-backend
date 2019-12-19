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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysParamTypeUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysParamTypeUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "类型名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "类型名称长度不正确")
	private String typeName;

	@ApiModelProperty(value = "类型编码")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "类型编码长度不正确")
	private String typeCode;

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
