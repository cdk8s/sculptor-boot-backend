package com.cdk8s.sculptor.pojo.dto.param.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "BatchUpdateStateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchUpdateStateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("idList")
	@NotEmpty(message = "idList 不能为空")
	@Size(min = 1, message = "idList 至少需要一个元素")
	private List<Long> idList;

	@ApiModelProperty("状态")
	@NotNull(message = "状态不能为空")
	@Range(min = 1, max = 2, message = "状态值只能是 1：启用 和 2：禁用")
	private Integer stateEnum;

}
