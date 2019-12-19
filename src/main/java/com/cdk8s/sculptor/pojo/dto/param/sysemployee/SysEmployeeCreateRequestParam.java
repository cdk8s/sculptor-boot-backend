package com.cdk8s.sculptor.pojo.dto.param.sysemployee;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysEmployeeCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysEmployeeCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户ID")
	@NotNull(message = "用户ID不能为空")
	private Long userId;

	@ApiModelProperty(value = "工号")
	@Length(max = 50, message = "工号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String workCardId;

	@ApiModelProperty(value = "职位")
	@Length(max = 50, message = "职位长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String jobPosition;


}
