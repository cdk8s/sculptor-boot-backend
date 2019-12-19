package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String username;
	private String realName;
	private String userPassword;
	private String passwordSalt;
	private String userEmail;
	private String telephone;
	private String mobilePhone;
	private Integer genderEnum;
	private Integer registerTypeEnum;
	private Integer registerOriginEnum;

	private Integer stateEnum;

	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;


	// ==============非 entity 属性 start==============

	@Transient
	private String deptName;

	// ==============非 entity 属性 end==============


}

