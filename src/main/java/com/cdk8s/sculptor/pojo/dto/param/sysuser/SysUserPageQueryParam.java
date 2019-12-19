package com.cdk8s.sculptor.pojo.dto.param.sysuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ApiModel(value = "SysUserPageQueryParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserPageQueryParam extends PageParam {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "用户账号")
	private String username;

	@ApiModelProperty(value = "真实姓名")
	private String realName;

	@ApiModelProperty(value = "登录密码")
	private String userPassword;

	@ApiModelProperty(value = "密码盐")
	private String passwordSalt;

	@ApiModelProperty(value = "邮箱地址")
	private String userEmail;

	@ApiModelProperty(value = "固话")
	private String telephone;

	@ApiModelProperty(value = "手机号")
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

	@ApiModelProperty(value = "创建人")
	private Long createUserId;


	// ==============非 entity 属性 start==============

	@ApiModelProperty(value = "部门ID")
	private Long deptId;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	// ==============非 entity 属性 end==============

}
