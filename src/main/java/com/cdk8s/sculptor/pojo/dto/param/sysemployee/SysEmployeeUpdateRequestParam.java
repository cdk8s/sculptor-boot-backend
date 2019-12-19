package com.cdk8s.sculptor.pojo.dto.param.sysemployee;

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

@ApiModel(value = "SysEmployeeUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysEmployeeUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "工号")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "工号长度不正确")
	private String workCardId;

	@ApiModelProperty(value = "职位")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "职位长度不正确")
	private String jobPosition;


}
