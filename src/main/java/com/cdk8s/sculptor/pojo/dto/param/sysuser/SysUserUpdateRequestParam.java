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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "SysUserUpdateRequestParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserUpdateRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("ID")
	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@ApiModelProperty(value = "用户账号")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "用户账号长度不正确")
	private String username;

	@ApiModelProperty(value = "真实姓名")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "真实姓名长度不正确")
	private String realName;

	@ApiModelProperty(value = "登录密码")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "登录密码长度不正确")
	private String userPassword;

	@ApiModelProperty(value = "密码盐")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 10, message = "密码盐长度不正确")
	private String passwordSalt;

	@ApiModelProperty(value = "邮箱地址")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 50, message = "邮箱地址长度不正确")
	private String userEmail;

	@ApiModelProperty(value = "固话")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 20, message = "固话长度不正确")
	private String telephone;

	@ApiModelProperty(value = "手机号")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Length(max = 20, message = "手机号长度不正确")
	private String mobilePhone;

	@ApiModelProperty(value = "性别")
	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderEnum;

	@ApiModelProperty(value = "注册方式")
	@Range(min = 1, max = 4, message = "注册方式数值不正确")
	private Integer registerTypeEnum;

	@ApiModelProperty(value = "注册来源")
	@Range(min = 1, max = 6, message = "注册来源数值不正确")
	private Integer registerOriginEnum;

	@ApiModelProperty(value = "启用状态")
	@Range(min = 1, max = 2, message = "启用状态数值不正确")
	private Integer stateEnum;


}
