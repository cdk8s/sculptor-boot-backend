package com.cdk8s.sculptor.pojo.dto.response.sysuser;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "SysUserResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysUserResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户账号")
	private String username;

	@ApiModelProperty(value = "真实姓名")
	private String realName;

	@ApiModelProperty(value = "邮箱地址")
	private String userEmail;

	@ApiModelProperty(value = "固话")
	private String telephone;

	@ApiModelProperty(value = "手机号")
	private String mobilePhone;

	@ApiModelProperty(value = "性别")
	private Integer genderEnum;

	@ApiModelProperty(value = "性别")
	private String genderEnumString;

	@ApiModelProperty(value = "注册方式")
	private Integer registerTypeEnum;

	@ApiModelProperty(value = "注册方式")
	private String registerTypeEnumString;

	@ApiModelProperty(value = "注册来源")
	private Integer registerOriginEnum;

	@ApiModelProperty(value = "注册来源")
	private String registerOriginEnumString;

	@ApiModelProperty(value = "启用状态")
	private Integer stateEnum;

	@ApiModelProperty(value = "启用状态")
	private String stateEnumString;


	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;

	@ApiModelProperty(value = "更新时间")
	private Long updateDate;

	@ApiModelProperty(value = "更新人ID")
	private Long updateUserId;

	@ApiModelProperty(value = "更新人")
	private String updateUsername;

	// ==============非 entity 属性 start==============

	@ApiModelProperty(value = "所属部门")
	private List<String> deptNameList;

	@ApiModelProperty(value = "所属角色")
	private List<String> roleNameList;

	// ==============非 entity 属性 end==============

}
