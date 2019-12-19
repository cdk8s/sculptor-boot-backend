package com.cdk8s.sculptor.pojo.dto.param.sysparam;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ApiModel(value = "SysParamPageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysParamPageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

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
	@Range(min = 1, max = 100, message = "排序数值不正确")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "参数值类型")
	@Range(min = 1, max = 5, message = "参数值类型数值不正确")
	private Integer paramValueTypeEnum;

	@ApiModelProperty(value = "启用状态")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;

	@ApiModelProperty(value = "创建人")
	private Long createUserId;


}
