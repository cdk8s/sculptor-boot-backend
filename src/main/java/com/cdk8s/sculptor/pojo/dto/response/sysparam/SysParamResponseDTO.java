package com.cdk8s.sculptor.pojo.dto.response.sysparam;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysParamResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysParamResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "参数类型ID")
	private Long typeId;

	@ApiModelProperty(value = "参数类型编码")
	private String typeCode;

	@ApiModelProperty(value = "参数名称")
	private String paramName;

	@ApiModelProperty(value = "参数编码")
	private String paramCode;

	@ApiModelProperty(value = "参数值")
	private String paramValue;

	@ApiModelProperty(value = "排序")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "参数值类型")
	private Integer paramValueTypeEnum;

	@ApiModelProperty(value = "参数值类型")
	private String paramValueTypeEnumString;

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
