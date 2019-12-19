package com.cdk8s.sculptor.pojo.dto.param.syscommonopen;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "SysCommonOpenQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysCommonOpenQueryCacheParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "cacheKey")
	@NotBlank(message = "cacheKey 不能为空")
	private String cacheKey;


}
