package com.cdk8s.sculptor.pojo.dto.param.sysdict;

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

@ApiModel(value = "SysDictCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDictCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "字典名称")
	@NotBlank(message = "字典名称不能为空")
	@Length(max = 50, message = "字典名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String dictName;

	@ApiModelProperty(value = "字典编码")
	@NotBlank(message = "字典编码不能为空")
	@Length(max = 100, message = "字典编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String dictCode;

	@ApiModelProperty(value = "字典值类型")
	@NotNull(message = "字典值类型不能为空")
	@Range(min = 1, max = 5, message = "字典值类型数值不正确")
	private Integer dictValueTypeEnum;

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
