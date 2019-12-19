package com.cdk8s.sculptor.pojo.dto.response.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(value = "EnumInfoItemDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class EnumInfoItemDTO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer value;
	private String label;

}
