package com.cdk8s.sculptor.pojo.dto.param.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "BatchDeleteRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchDeleteRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("idList")
	@NotEmpty(message = "idList 不能为空")
	@Size(min = 1, message = "idList 至少需要一个元素")
	private List<Long> idList;

}
