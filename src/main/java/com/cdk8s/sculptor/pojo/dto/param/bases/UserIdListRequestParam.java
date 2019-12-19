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

@ApiModel(value = "IdListRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIdListRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("userIdList")
	@NotEmpty(message = "userIdList 不能为空")
	@Size(min = 1, message = "userIdList 至少需要一个元素")
	private List<Long> userIdList;

}
