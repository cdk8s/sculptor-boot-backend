package com.cdk8s.sculptor.pojo.dto.param.sysdictitem;

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

@ApiModel(value = "SysDictItemCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDictItemCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "字典ID")
	@NotNull(message = "字典ID不能为空")
	private Long dictId;

	@ApiModelProperty(value = "字典子项名称")
	@NotBlank(message = "字典子项名称不能为空")
	@Length(max = 50, message = "字典子项名称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String itemName;

	@ApiModelProperty(value = "字典子项编码")
	@NotBlank(message = "字典子项编码不能为空")
	@Length(max = 100, message = "字典子项编码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String itemCode;

	@ApiModelProperty(value = "字典子项值")
	@Length(max = 250, message = "字典子项值长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String itemValue;

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
