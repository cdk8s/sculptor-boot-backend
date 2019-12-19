package com.cdk8s.sculptor.pojo.bo.service.sysuser;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysUserUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
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
	private Long updateDate;
	private Long updateUserId;

}
