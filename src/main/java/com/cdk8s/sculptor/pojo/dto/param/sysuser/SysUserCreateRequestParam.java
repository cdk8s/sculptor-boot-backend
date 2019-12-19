package com.cdk8s.sculptor.pojo.dto.param.sysuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysUserCreateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户账号")
	@NotBlank(message = "用户账号不能为空")
	@Length(max = 50, message = "用户账号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String username;

	@ApiModelProperty(value = "真实姓名")
	@Length(max = 50, message = "真实姓名长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String realName;

	@ApiModelProperty(value = "登录密码")
	@NotBlank(message = "登录密码不能为空")
	@Length(max = 50, message = "登录密码长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userPassword;

	@ApiModelProperty(value = "邮箱地址")
	@Length(max = 50, message = "邮箱地址长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String userEmail;

	@ApiModelProperty(value = "固话")
	@Length(max = 20, message = "固话长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String telephone;

	@ApiModelProperty(value = "手机号")
	@Length(max = 20, message = "手机号长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String mobilePhone;

	@ApiModelProperty(value = "性别")
	@NotNull(message = "性别不能为空")
	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderEnum;

	@ApiModelProperty(value = "启用状态")
	@NotNull(message = "启用状态不能为空")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
