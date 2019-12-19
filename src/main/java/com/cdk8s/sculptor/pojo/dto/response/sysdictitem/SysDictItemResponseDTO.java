package com.cdk8s.sculptor.pojo.dto.response.sysdictitem;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysDictItemResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysDictItemResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "字典ID")
	private Long dictId;

	@ApiModelProperty(value = "字典编码")
	private String dictCode;

	@ApiModelProperty(value = "字典子项名称")
	private String itemName;

	@ApiModelProperty(value = "字典子项编码")
	private String itemCode;

	@ApiModelProperty(value = "字典子项值")
	private String itemValue;

	@ApiModelProperty(value = "字典值类型")
	private Integer dictValueTypeEnum;

	@ApiModelProperty(value = "字典值类型")
	private String dictValueTypeEnumString;

	@ApiModelProperty(value = "排序")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "启用状态")
	private Integer stateEnum;

	@ApiModelProperty(value = "启用状态")
	private String stateEnumString;


	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;

	@ApiModelProperty(value = "更新时间")
	private Long updateDate;

	@ApiModelProperty(value = "更新人ID")
	private Long updateUserId;

	@ApiModelProperty(value = "更新人")
	private String updateUsername;

}
