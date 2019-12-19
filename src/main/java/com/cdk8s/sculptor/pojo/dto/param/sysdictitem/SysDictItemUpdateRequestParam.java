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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysDictItemUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDictItemUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "字典子项名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "字典子项名称长度不正确")
	private String itemName;

	@ApiModelProperty(value = "字典子项编码")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 100, message = "字典子项编码长度不正确")
	private String itemCode;

	@ApiModelProperty(value = "字典子项值")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 250, message = "字典子项值长度不正确")
	private String itemValue;

	@ApiModelProperty(value = "字典值类型")
	@Range(min = 1, max = 5, message = "字典值类型数值不正确")
	private Integer dictValueTypeEnum;

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
